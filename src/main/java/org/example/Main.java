package org.example;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        ClassroomDataGroups classroomDataGroups = new ClassroomDataGroups();
        PersonAgeDataGroups personAgeDataGroups = new PersonAgeDataGroups();
        PersonNameDataGroups personNameDataGroups = new PersonNameDataGroups();

        try (BufferedReader br = new BufferedReader(new FileReader("D:\\workDir\\Dz1\\src\\main\\resources\\students.csv"))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                Person person = getPerson(line);

                classroomDataGroups.addPerson(person);
                personAgeDataGroups.addPerson(person);
                personNameDataGroups.addPerson(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * для вычисления средний оценки 10 и 11 классов использовал classroomDataGroups
         * потому что мы можем быстро сократить колличество групп для вычисления средней оценки
         * так мы ускоряем время работы нашего O(n) т.к. оно линейно зависит от этих данных
         */
        List<Person> highSchoolScore = new ArrayList<>();
        highSchoolScore.addAll(classroomDataGroups.getPersons(10));
        highSchoolScore.addAll(classroomDataGroups.getPersons(11));
        double average = highSchoolScore.stream()
                .mapToDouble(person -> (person.getPhysics() + person.getMathematics() + person.getRus() + person.getLiterature() + person.getGeometry() + person.getInformatics())/6)
                .average()
                .orElse(0.0);
        System.out.println(average);


        /**
         * путем нехитрых вычислений я узнал, что максимальный возраст 17 лет
         * использовал для поиска средней оценки personAgeDataGroups т.к.
         * он сокращает пул возрастов и этим ускоряем работу
         * сложность O(n) линейно зависит от этих данных
         */
        List<Person> honorStudent = new ArrayList<>();
        honorStudent.addAll(personAgeDataGroups.getPersons(15));
        honorStudent.addAll(personAgeDataGroups.getPersons(16));
        honorStudent.addAll(personAgeDataGroups.getPersons(17));
        double highScore = honorStudent.stream()
                .mapToDouble(person -> (person.getPhysics() + person.getMathematics() + person.getRus() + person.getLiterature() + person.getGeometry() + person.getInformatics())/6)
                .average()
                .orElse(0.0);
        honorStudent.stream()
                .filter(person -> (person.getPhysics() + person.getMathematics() + person.getRus() + person.getLiterature() + person.getGeometry() + person.getInformatics()) / 6 >= 5.0)
                .forEach(person -> System.out.println(person));

        /**
         * Используем personNameDataGroups для сокращения пула поиска по фамилии (получаем только фамилии начинающиеся с символа искомой фамилии)
         * В среднем сложность O(n)
         */
        Scanner sc = new Scanner(System.in);
        String searchLastName = sc.nextLine();
        Character firstChar = searchLastName.charAt(0);
        List<Person> lastNameStudent = personNameDataGroups.getPersons(String.valueOf(firstChar));
        for (Person person : lastNameStudent) {
            if (person.getFamilyName().equalsIgnoreCase(searchLastName)) {
                System.out.println(person);
            }
        }


    }
    private static Person getPerson(String line){
        String[] fields = line.split(";");
        if (fields.length != 10) {
            throw new RuntimeException("Invalid line " + line);
        }
        return new Person(
                fields[0],
                fields[1],
                Integer.parseInt(fields[2]),
                Integer.parseInt(fields[3]),
                Integer.parseInt(fields[4]),
                Integer.parseInt(fields[5]),
                Integer.parseInt(fields[6]),
                Integer.parseInt(fields[7]),
                Integer.parseInt(fields[8]),
                Integer.parseInt(fields[9]));
    }
}

