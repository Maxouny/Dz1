package org.example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class PersonNameDataGroups {
    private Map<String, List<Person>> nameToPersons = new HashMap<>();

    public void addPerson(Person person) {
        String firstLetter = String.valueOf(person.getFamilyName().charAt(0));
        List<Person> group = nameToPersons.getOrDefault(firstLetter, new ArrayList<>());
        group.add(person);
        nameToPersons.put(firstLetter, group);
    }

    public List<Person> getPersons(String firstChar) {
        return nameToPersons.getOrDefault(firstChar, new ArrayList<>());
    }
}