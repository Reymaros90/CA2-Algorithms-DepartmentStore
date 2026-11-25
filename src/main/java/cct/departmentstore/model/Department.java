package ie.cct.departmentstore.model;

/** The existing departments in this department store. */
public enum Department {
    SALES("Sales"),
    CUSTOMER_SERVICE("Customer Service"),
    HR("HR"),
    FINANCE("Finance"),
    OPERATIONS("Operations"),
    MARKETING("Marketing"),
    IT_SUPPORT("IT Support");

    private final String label;
    Department(String label) { this.label = label; }
    public String getLabel() { return label; }

    public static Department fromLabel(String text) {
        for (Department option : values()) {
            if (option.label.equalsIgnoreCase(text.trim())) return option;
        }
        throw new IllegalArgumentException("Invalid Department: " + text);
    }
}
