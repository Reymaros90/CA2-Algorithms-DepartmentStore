package ie.cct.departmentstore.model;

/** An employee record using only the three fields required by the assessment. */
public final class Employee {
    private final String name;
    private final ManagerType managerType;
    private final Department department;

    public Employee(String name, ManagerType managerType, Department department) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty.");
        }
        if (managerType == null || department == null) {
            throw new IllegalArgumentException("Manager Type and Department are required.");
        }
        this.name = name.trim().replaceAll("\\s+", " ");
        this.managerType = managerType;
        this.department = department;
    }

    public String getName() { return name; }
    public ManagerType getManagerType() { return managerType; }
    public Department getDepartment() { return department; }

    @Override
    public String toString() {
        return name + " | " + managerType.getLabel() + " | " + department.getLabel();
    }
}
