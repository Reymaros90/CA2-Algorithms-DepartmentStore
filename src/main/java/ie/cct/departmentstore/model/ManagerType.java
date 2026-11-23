package ie.cct.departmentstore.model;

/** Choices offered to the user; no unlisted Manager Type can be entered. */
public enum ManagerType {
    SENIOR_MANAGER("Senior Manager"),
    MANAGER("Manager"),
    TEAM_LEAD("Team Lead"),
    STAFF("Staff");

    private final String label;

    ManagerType(String label) { this.label = label; }
    public String getLabel() { return label; }

    public static ManagerType fromLabel(String text) {
        for (ManagerType option : values()) {
            if (option.label.equalsIgnoreCase(text.trim())) return option;
        }
        throw new IllegalArgumentException("Invalid Manager Type: " + text);
    }
}
