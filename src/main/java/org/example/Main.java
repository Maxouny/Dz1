package org.example;

import org.example.dto.DatabaseManager;

import java.sql.SQLException;
import java.util.Scanner;



public class Main {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) throws SQLException {

        DatabaseManager dbm = new DatabaseManager(JDBC_URL, USERNAME, PASSWORD);


        DataLoader dataLoader = new CsvFileDataLoader("src/main/resources/students.csv");

        StudentService studentService = new StudentService(dataLoader);

        studentService.loadData();

        CommandBuilder commandBuilder = new CommandBuilder(studentService);


        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.print("Введите команду: \n" +
                    "highSchoolScore/sqlHighSchoolScore чтобы получить среднюю оценку в старших классах 10 и 11 \n" +
                    "honorStudent/sqlHonorStudent чтобы получить всех отличников старше 14 \n" +
                    "searchLastName/sqlSearchLastName чтобы получить всех с фамилией которую ищите \n" +
                    "Введите exit чтобы завершить программу \n");

            String commandName = scanner.nextLine();

            if ("exit".equalsIgnoreCase(commandName)) {
                break;
            } else if ("sqlHighSchoolScore".equalsIgnoreCase(commandName)) {
                dbm.getAverageGradesForSeniorClasses();
            } else if ("sqlHonorStudent".equalsIgnoreCase(commandName)) {
                dbm.getAllExcellentStudents();
            } else if ("sqlSearchLastName".equalsIgnoreCase(commandName)) {
                dbm.getAverageGradeByFamilyName();
            }
            Command command = commandBuilder.buildCommand(commandName);

            if (command != null) {
                command.execute();
            }
        }
        scanner.close();
    }
}

