package org.example.servlet;

import org.example.dto.GradesDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/***из-за того, что я использовал структуру gradesDto для получения нужных параметров
 * в ответе на остальных полях которые мы не селектим получаем 0
 * с одной стороны это плохо т.к. получаем лишнии данные,
 * с другой стороны мы знаем с какой сущностью мы работаем и значения сокрыты
*/
@WebServlet("/api/grades/average")
public class AverageGradesServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres?sslmode=disable";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "postgres");

        String className = request.getParameter("class");

        List<GradesDTO> studentAverages = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, props);

            String sql = "SELECT family, name, age, class, average " +
                    "FROM grades WHERE class = ? GROUP BY family, name, age, class, average";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(className));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String family = resultSet.getString("family");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int classId = resultSet.getInt("class");
                double avg = resultSet.getDouble("average");
                GradesDTO gradesDTO = new GradesDTO();
                gradesDTO.setFamily(family);
                gradesDTO.setName(name);
                gradesDTO.setAge(age);
                gradesDTO.setClassId(classId);
                gradesDTO.setAverage(avg);
                studentAverages.add(gradesDTO);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(studentAverages);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
