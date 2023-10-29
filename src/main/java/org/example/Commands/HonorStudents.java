package org.example.Commands;

import org.example.Command;
import org.example.DataGroup;
import org.example.Person;
import org.example.StudentService;

import java.util.ArrayList;
import java.util.List;

public class HonorStudents implements Command {

    private final StudentService studentService;

    public HonorStudents(StudentService studentService) {
        this.studentService = studentService;
    }

    public void execute() {
        DataGroup honorStudents = studentService.getAgeGroup();
        List<Person> listHonorStudent = new ArrayList<>();
        listHonorStudent.addAll(honorStudents.getPersons(String.valueOf(15)));
        listHonorStudent.addAll(honorStudents.getPersons(String.valueOf(16)));
        listHonorStudent.addAll(honorStudents.getPersons(String.valueOf(17)));
        listHonorStudent.stream()
                .filter(person -> (person.getPhysics() + person.getMathematics() + person.getRus() + person.getLiterature() + person.getGeometry() + person.getInformatics()) / 6 >= 5.0)
                .forEach(person -> System.out.println(person));
    }
}
