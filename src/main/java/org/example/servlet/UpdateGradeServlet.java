package org.example.servlet;



import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

/**
 * Я бы готов написать целую повесь о дебаге этого запроса, но не буду :-)
 *добавил валидацию предмета
 * добавил пересчет средней оценки
 */
@WebServlet("/api/grades/update")
public class UpdateGradeServlet extends HttpServlet {
    private Connection connection;

    @Override
    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "postgres");
            String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres?sslmode=disable";
            connection = DriverManager.getConnection(JDBC_URL, props);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Failed to initialize database connection.", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String subject = request.getParameter("subject");
        String[] validSubjects = {"physics", "mathematics", "rus", "literature", "geometry", "informatics"};

        if (!Arrays.asList(validSubjects).contains(subject)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Invalid input: Invalid subject.");
            return;
        }

        String family = request.getParameter("family");
        String name = request.getParameter("name");
        int classId = 0;
        int newGrade = 0;

        try {
            classId = Integer.parseInt(request.getParameter("class"));
            newGrade = Integer.parseInt(request.getParameter("newGrade"));
        } catch (NumberFormatException e) {
            System.out.println("Error updating grade: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Invalid input: class and newGrade must be valid integers.");
            return;
        }

        boolean success = updateGrade(name, family, classId, subject, newGrade);

        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private boolean updateGrade(String family, String name, int classId, String subject, int newGrade) {
        try {
            String updateQuery = "UPDATE grades SET " + subject + " = ? WHERE family = ? AND name = ? AND class = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, newGrade);
            statement.setString(2, name);
            statement.setString(3, family);
            statement.setInt(4, classId);

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            if (rowsUpdated == 0) {
                return false;
            }

            String recalculateAverageQuery = "UPDATE grades SET average = " +
                    "(physics + mathematics + rus + literature + geometry + informatics) / 6 WHERE family = ? AND name = ? AND class = ?";
            statement = connection.prepareStatement(recalculateAverageQuery);
            statement.setString(1, family);
            statement.setString(2, name);
            statement.setInt(3, classId);

            statement.executeUpdate();
            statement.close();

            return true;
        } catch (SQLException e) {
            System.out.println("Error updating grade: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }
}
