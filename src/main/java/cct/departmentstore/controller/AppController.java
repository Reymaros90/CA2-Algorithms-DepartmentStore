package ie.cct.departmentstore.controller;

import ie.cct.departmentstore.model.Department;
import ie.cct.departmentstore.model.Employee;
import ie.cct.departmentstore.model.EmployeeRepository;
import ie.cct.departmentstore.model.EmployeeTree;
import ie.cct.departmentstore.model.ManagerType;
import ie.cct.departmentstore.model.MenuOption;
import ie.cct.departmentstore.model.RecursiveBinarySearch;
import ie.cct.departmentstore.model.RecursiveMergeSort;
import ie.cct.departmentstore.view.ConsoleView;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/** Controller: coordinates the model and view in response to each menu choice. */
public final class AppController {
    private final ConsoleView view;
    private final EmployeeRepository repository;
    private final RecursiveMergeSort sorter = new RecursiveMergeSort();
    private final RecursiveBinarySearch searcher = new RecursiveBinarySearch();
    private List<Employee> sortedEmployees = new ArrayList<>();

    public AppController(ConsoleView view, EmployeeRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void run() {
        view.message("DEPARTMENT STORE - Employee Management");
        while (true) {
            String fileName = view.askFileName();
            try {
                int count = repository.load(Paths.get(fileName));
                sortedEmployees = sorter.sort(repository.getAll());
                view.message("File read successfully (" + count + " employees loaded).");
                break;
            } catch (IOException | IllegalArgumentException exception) {
                view.message("Unable to read file: " + exception.getMessage());
            }
        }

        while (true) {
            MenuOption selection = view.chooseMenuOption();
            switch (selection) {
                case SORT:
                    sortedEmployees = sorter.sort(repository.getAll());
                    view.showFirst20Names(sortedEmployees);
                    break;
                case SEARCH:
                    view.showSearchResult(searcher.find(sortedEmployees, view.askEmployeeName()));
                    break;
                case ADD_RECORDS:
                    addRecord();
                    break;
                case CREATE_BINARY_TREE:
                    showHierarchy();
                    break;
                case EXIT:
                    view.message("Goodbye.");
                    return;
                default:
                    throw new IllegalStateException("Unexpected menu value.");
            }
        }
    }

    private void addRecord() {
        String name = view.askEmployeeName();
        ManagerType managerType = view.chooseManagerType();
        Department department = view.chooseDepartment();
        repository.add(new Employee(name, managerType, department));
        sortedEmployees = sorter.sort(repository.getAll());
        view.showNewRecords(repository.getAddedRecords());
    }

    private void showHierarchy() {
        EmployeeTree tree = new EmployeeTree();
        for (Employee employee : sortedEmployees) tree.insertLevelOrder(employee);
        view.showTree(tree);
    }
}
