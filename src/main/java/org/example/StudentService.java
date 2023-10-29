package org.example;

import java.util.List;

public class StudentService {
    private final DataLoader dataLoader;
    private final DataGroup classGroup;
    private final DataGroup ageGroup;
    private final DataGroup nameGroup;

    public StudentService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
        classGroup = new DataGroup(person -> String.valueOf(person.getGroup()));
        ageGroup = new DataGroup(person -> String.valueOf(person.getAge()));
        nameGroup = new DataGroup(person -> String.valueOf(person.getFamilyName().charAt(0)));
    }

    public void loadData() {
        List<Person> students = dataLoader.load();
        for (Person student : students) {
            classGroup.addPerson(student);
            ageGroup.addPerson(student);
            nameGroup.addPerson(student);
        }
    }

    public DataGroup getClassGroup() {
        return classGroup;
    }

    public DataGroup getAgeGroup() {
        return ageGroup;
    }

    public DataGroup getNameGroup() {
        return nameGroup;
    }
}
