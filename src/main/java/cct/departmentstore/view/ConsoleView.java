package ie.cct.departmentstore.view;

import ie.cct.departmentstore.model.Department;
import ie.cct.departmentstore.model.Employee;
import ie.cct.departmentstore.model.EmployeeTree;
import ie.cct.departmentstore.model.ManagerType;
import ie.cct.departmentstore.model.MenuOption;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/** View: presents the terminal menus and reads user input; no sorting or searching logic. */
public final class ConsoleView {
    private final Scanner input;
    private final PrintStream output;

    public ConsoleView(InputStream input, PrintStream output) {
        this.input = new Scanner(input);
        this.output = output;
    }

    public void message(String text) { output.println(text); }

    public String ask(String prompt) {
        output.print(prompt);
        if (!input.hasNextLine()) throw new IllegalStateException("Input stream closed.");
        return input.nextLine().trim();
    }

    public String askFileName() {
        return ask("Please enter the filename to read: ");
    }

    public MenuOption chooseMenuOption() {
        while (true) {
            output.println("\nDo You wish to SORT or SEARCH:");
            for (MenuOption option : MenuOption.values()) {
                output.println(option.getNumber() + ". " + option.getLabel());
            }
            try {
                return MenuOption.fromNumber(Integer.parseInt(ask("Choose an option: ")));
            } catch (IllegalArgumentException exception) {
                output.println("Invalid option. Enter a number from 1 to 5.");
            }
        }
    }

    public String askEmployeeName() {
        while (true) {
            String name = ask("Enter the employee's full name: ").replaceAll("\\s+", " ");
            if (!name.isEmpty() && !name.contains(",")) return name;
            output.println("Name is required and cannot contain a comma.");
        }
    }

    public ManagerType chooseManagerType() {
        while (true) {
            output.println("Choose an existing Manager Type:");
            ManagerType[] options = ManagerType.values();
            for (int i = 0; i < options.length; i++) {
                output.println((i + 1) + ". " + options[i].getLabel());
            }
            try {
                int selection = Integer.parseInt(ask("Manager Type: "));
                if (selection >= 1 && selection <= options.length) return options[selection - 1];
            } catch (NumberFormatException ignored) {
                // All invalid values must be rejected and re-entered.
            }
            output.println("Invalid Manager Type. Choose one of the listed numbers.");
        }
    }

    public Department chooseDepartment() {
        while (true) {
            output.println("Choose an existing Department:");
            Department[] options = Department.values();
            for (int i = 0; i < options.length; i++) {
                output.println((i + 1) + ". " + options[i].getLabel());
            }
            try {
                int selection = Integer.parseInt(ask("Department: "));
                if (selection >= 1 && selection <= options.length) return options[selection - 1];
            } catch (NumberFormatException ignored) {
                // All invalid values must be rejected and re-entered.
            }
            output.println("Invalid Department. Choose one of the listed numbers.");
        }
    }

    public void showFirst20Names(List<Employee> sorted) {
        output.println("\nSORT selected - first 20 names (alphabetical order):");
        for (int index = 0; index < Math.min(20, sorted.size()); index++) {
            output.printf("%2d. %s%n", index + 1, sorted.get(index).getName());
        }
    }

    public void showSearchResult(Employee person) {
        if (person == null) {
            output.println("No employee found with that name.");
        } else {
            output.println("Name: " + person.getName());
            output.println("Manager Type: " + person.getManagerType().getLabel());
            output.println("Department: " + person.getDepartment().getLabel());
        }
    }

    public void showNewRecords(List<Employee> added) {
        output.println("\nAll newly added records (" + added.size() + "):");
        for (Employee record : added) output.println("- " + record);
    }

    public void showTree(EmployeeTree tree) {
        output.println("\nEmployee hierarchy (level-order traversal):");
        int level = 1;
        for (List<Employee> employees : tree.levels()) {
            output.println("Level " + level++ + ":");
            for (Employee employee : employees) {
                output.println("  - " + employee);
            }
        }
        output.println("Tree height (number of levels): " + tree.height());
        output.println("Total node count: " + tree.count());
    }
}
