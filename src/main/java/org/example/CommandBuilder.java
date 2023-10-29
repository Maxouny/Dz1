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
        if ("highSchoolScore".equals(commandName)) {
            return new HighSchoolScore(studentService);
        } else if ("honorStudent".equals(commandName)) {
            return new HonorStudents(studentService);
        } else if ("searchLastName".equals(commandName)) {
            return new SearchLastName(studentService);
        }
        return null;
    }
}
