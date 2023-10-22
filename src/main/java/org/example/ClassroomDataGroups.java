package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassroomDataGroups {
    private Map<Integer, List<Person>> groupToPersons = new HashMap<>();

    public void addPerson(Person person) {
        int groupNum = person.getGroup();
        List<Person> group = groupToPersons.getOrDefault(groupNum, new ArrayList<>());
        group.add(person);
        groupToPersons.put(groupNum, group);
    }
    public List<Person> getPersons(int groupNum) {
        return groupToPersons.getOrDefault(groupNum, new ArrayList<>());
    }
}

