package ie.cct.departmentstore.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Keeps loaded employees and newly added records in program memory. */
public final class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();
    private final List<Employee> addedRecords = new ArrayList<>();

    public int load(Path file) throws IOException {
        List<Employee> parsed = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String header = reader.readLine();
            if (header == null || !header.trim().equalsIgnoreCase("Name,Manager Type,Department")) {
                throw new IOException("Invalid file header. Expected: Name,Manager Type,Department");
            }
            String line;
            int number = 1;
            while ((line = reader.readLine()) != null) {
                number++;
                if (line.trim().isEmpty()) continue;
                String[] columns = line.split(",", -1);
                if (columns.length != 3) {
                    throw new IOException("Invalid data at line " + number + ": expected 3 columns.");
                }
                try {
                    parsed.add(new Employee(columns[0],
                            ManagerType.fromLabel(columns[1]),
                            Department.fromLabel(columns[2])));
                } catch (IllegalArgumentException ex) {
                    throw new IOException("Invalid data at line " + number + ": " + ex.getMessage(), ex);
                }
            }
        }
        employees.clear();
        employees.addAll(parsed);
        addedRecords.clear();
        return employees.size();
    }

    public void add(Employee employee) {
        employees.add(employee);
        addedRecords.add(employee);
    }

    public List<Employee> getAll() {
        return Collections.unmodifiableList(employees);
    }

    public List<Employee> getAddedRecords() {
        return Collections.unmodifiableList(addedRecords);
    }
}
