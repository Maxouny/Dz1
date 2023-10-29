package org.example.Commands;

import org.example.Command;
import org.example.DataGroup;
import org.example.Person;
import org.example.StudentService;

import java.util.ArrayList;
import java.util.List;

public class HighSchoolScore implements Command {
    private final StudentService studentService;

    public HighSchoolScore(StudentService studentService) {
        this.studentService = studentService;
    }

    public void execute() {
        DataGroup highSchoolScore = studentService.getClassGroup();
        List<Person> listHighSchoolScore = new ArrayList<>();
        listHighSchoolScore.addAll(highSchoolScore.getPersons(String.valueOf(10)));
        listHighSchoolScore.addAll(highSchoolScore.getPersons(String.valueOf(11)));
        double average = listHighSchoolScore.stream()
                .mapToDouble(person -> (person.getPhysics() + person.getMathematics() + person.getRus() + person.getLiterature() + person.getGeometry() + person.getInformatics()) / 6)
                .average()
                .orElse(0.0);
        System.out.println(average);

    }
}
