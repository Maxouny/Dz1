package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataGroup {
    private final Map<String, List<Person>> data = new HashMap<>();
    private final GroupCriterion criterion;

    public DataGroup(GroupCriterion criterion) {
        this.criterion = criterion;
    }

    public void addPerson(Person person) {
        String groupKey = criterion.getGroupKey(person);
        data.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(person);
    }

    public List<Person> getPersons(String groupKey) {
        return data.getOrDefault(groupKey, new ArrayList<>());
    }
    @FunctionalInterface
    public interface GroupCriterion {
        String getGroupKey(Person person);
    }
}

