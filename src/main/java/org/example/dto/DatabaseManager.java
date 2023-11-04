package org.example.dto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseManager implements AutoCloseable {
    private Connection connection;

    public DatabaseManager(String jdbcURL, String username, String password) {
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public double getAverageGradesForSeniorClasses() throws SQLException {
        double average = 0.0;
        try (Statement statement = connection.createStatement()) {
            String sql = "SELECT AVG(physics), AVG(mathematics), AVG(rus), AVG(literature), AVG(geometry), AVG(informatics) FROM grades WHERE class IN (10, 11)";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                average = (resultSet.getDouble(1) + resultSet.getDouble(2) + resultSet.getDouble(3) +
                        resultSet.getDouble(4) + resultSet.getDouble(5) + resultSet.getDouble(6)) / 6;
            }
            System.out.println(average);
        }
        return average;
    }

    public List<StudentDTO> getAllExcellentStudents() throws SQLException {
        List<StudentDTO> excellentStudents = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM students " +
                    "WHERE age > 14 " +
                    "AND student_id IN (SELECT student_id FROM grades WHERE physics = 5 " +
                    "AND mathematics = 5 AND rus = 5 AND literature = 5 " +
                    "AND geometry = 5 AND informatics = 5)";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                StudentDTO student = new StudentDTO();
                student.setName(resultSet.getString("name"));
                student.setFamily(resultSet.getString("family"));
                student.setAge(resultSet.getInt("age"));
                student.setClassId(resultSet.getInt("class"));
                excellentStudents.add(student);
            }
            for (StudentDTO student : excellentStudents) {
                System.out.println(student.getName() + " " + student.getFamily() + " " + student.getAge() + " " + student.getClassId());
            }
        }
        return excellentStudents;
    }

    public List<GradesDTO> getAverageGradeByFamilyName() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите фамилию ученика");
        String familyName = sc.nextLine();
        List<GradesDTO> studentsWithAverageGrade = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT student_id, family, name, average " +
                "FROM grades " +
                "WHERE family = ? " +
                "GROUP BY student_id, family, name, average")) {
            preparedStatement.setString(1, familyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                GradesDTO student = new GradesDTO();
                student.setStudentId(resultSet.getInt("student_id"));
                student.setFamily(resultSet.getString("family"));
                student.setName(resultSet.getString("name"));
                student.setAverage(resultSet.getDouble("average"));
                studentsWithAverageGrade.add(student);
            }
            for (GradesDTO gradesDTO : studentsWithAverageGrade) {
                System.out.println(gradesDTO.getName() + " " + gradesDTO.getFamily() + " " + gradesDTO.getAverage());
            }
        }
        return studentsWithAverageGrade;
    }

}
