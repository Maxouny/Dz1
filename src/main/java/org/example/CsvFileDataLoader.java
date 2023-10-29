package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


public class CsvFileDataLoader implements DataLoader {
    private final String filePath;

    public CsvFileDataLoader(String filePath) {
        this.filePath = filePath;
    }

    public List<Person> load() {
        List<Person> persons = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] fields = line.split(";");
                if (fields.length != 10) {
                    continue;
                }
                Person person = new Person(
                        fields[0],
                        fields[1],
                        Integer.parseInt(fields[2]),
                        Integer.parseInt(fields[3]),
                        Integer.parseInt(fields[4]),
                        Integer.parseInt(fields[5]),
                        Integer.parseInt(fields[6]),
                        Integer.parseInt(fields[7]),
                        Integer.parseInt(fields[8]),
                        Integer.parseInt(fields[9])
                );
                persons.add(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return persons;
    }

}


