package org.example.Commands;

import org.example.Command;
import org.example.DataGroup;
import org.example.Person;
import org.example.StudentService;

import java.util.List;
import java.util.Scanner;

public class SearchLastName implements Command {
    private final StudentService studentService;

    public SearchLastName(StudentService studentService) {
        this.studentService = studentService;
    }

    public void execute() {
        Scanner sc = new Scanner(System.in);
        String searchLastName = sc.nextLine();
        DataGroup lastNameStudent = studentService.getNameGroup();
        Character firstChar = searchLastName.charAt(0);
        List<Person> listLastNameStudent = lastNameStudent.getPersons(String.valueOf(firstChar));
        listLastNameStudent.stream()
                .filter(person -> person.getFamilyName().equalsIgnoreCase(searchLastName))
                .forEach(System.out::println);
    }
}
