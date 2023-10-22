package org.example;

public class Person {
    String familyName;
    String name;
    int age;
    int group;
    int physics;
    int mathematics;
    int rus;
    int literature;
    int geometry;
    int informatics;

    public Person(String familyName, String name, int age, int group, int physics, int mathematics, int rus, int literature, int geometry, int informatics) {
        this.familyName = familyName;
        this.name = name;
        this.age = age;
        this.group = group;
        this.physics = physics;
        this.mathematics = mathematics;
        this.rus = rus;
        this.literature = literature;
        this.geometry = geometry;
        this.informatics = informatics;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getGroup() {
        return group;
    }

    public int getPhysics() {
        return physics;
    }

    public int getMathematics() {
        return mathematics;
    }

    public int getRus() {
        return rus;
    }

    public int getLiterature() {
        return literature;
    }

    public int getGeometry() {
        return geometry;
    }

    public int getInformatics() {
        return informatics;
    }

    @Override
    public String toString() {
        return "Student{" +
                "familyName='" + familyName + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", group=" + group +
                ", physics=" + physics +
                ", mathematics=" + mathematics +
                ", rus=" + rus +
                ", literature=" + literature +
                ", geometry=" + geometry +
                ", informatics=" + informatics +
                '}';
    }
}
