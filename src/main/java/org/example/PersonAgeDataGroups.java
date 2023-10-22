package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonAgeDataGroups {
    private Map<Integer, List<Person>> ageToPersons = new HashMap<>();

    public void addPerson(Person person) {
        int age = person.getAge();
        List<Person> group = ageToPersons.getOrDefault(age, new ArrayList<>());
        group.add(person);
        ageToPersons.put(age, group);
    }
    public List<Person> getPersons(int age) {
        return ageToPersons.getOrDefault(age, new ArrayList<>());
    }
}
