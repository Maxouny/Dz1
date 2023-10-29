package org.example;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        DataLoader dataLoader = new CsvFileDataLoader("src/main/resources/students.csv");

        StudentService studentService = new StudentService(dataLoader);

        studentService.loadData();

        CommandBuilder commandBuilder = new CommandBuilder(studentService);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Введите команду: \n" +
                    "highSchoolScore чтобы получить среднюю оценку в старших классах 10 и 11 \n" +
                    "honorStudent чтобы получить всех отличников старше 14 \n" +
                    "searchLastName чтобы получить всех с фамилией которую ищите \n" +
                    "Введите exit чтобы завершить программу \n");

            String commandName = scanner.nextLine();

            if ("exit".equalsIgnoreCase(commandName)) {
                break;
            }
            Command command = commandBuilder.buildCommand(commandName);

            if (command != null) {
                command.execute();
            } else {
                System.out.println("Неизвестная команда.");
            }
        }
        scanner.close();
    }
}

