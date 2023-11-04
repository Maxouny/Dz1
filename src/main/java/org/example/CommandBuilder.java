package org.example;

import org.example.Commands.HighSchoolScore;
import org.example.Commands.HonorStudents;
import org.example.Commands.SearchLastName;

public class CommandBuilder {
    private final StudentService studentService;

    public CommandBuilder(StudentService studentService) {
        this.studentService = studentService;
    }

    public Command buildCommand(String commandName) {
        switch (commandName) {
            case "highSchoolScore":
                return new HighSchoolScore(studentService);
            case "honorStudent":
                return new HonorStudents(studentService);
            case "searchLastName":
                return new SearchLastName(studentService);
            default:
                return null;
        }
    }
}
