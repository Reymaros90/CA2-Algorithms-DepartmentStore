package ie.cct.departmentstore;

import ie.cct.departmentstore.controller.AppController;
import ie.cct.departmentstore.model.EmployeeRepository;
import ie.cct.departmentstore.view.ConsoleView;

public final class Main {
    private Main() { }

    public static void main(String[] args) {
        new AppController(new ConsoleView(System.in, System.out), new EmployeeRepository()).run();
    }
}
