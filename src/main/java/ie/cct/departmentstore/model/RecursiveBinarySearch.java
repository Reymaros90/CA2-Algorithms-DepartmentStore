package ie.cct.departmentstore.model;

import java.util.List;

/** Recursive binary search; input list must already be sorted by employee name. */
public final class RecursiveBinarySearch {
    public Employee find(List<Employee> sorted, String requestedName) {
        if (requestedName == null || requestedName.trim().isEmpty()) return null;
        return search(sorted, requestedName.trim().replaceAll("\\s+", " "), 0, sorted.size() - 1);
    }

    private Employee search(List<Employee> records, String name, int low, int high) {
        if (low > high) return null;
        int mid = low + (high - low) / 2;
        int comparison = records.get(mid).getName().compareToIgnoreCase(name);
        if (comparison == 0) return records.get(mid);
        if (comparison < 0) return search(records, name, mid + 1, high);
        return search(records, name, low, mid - 1);
    }
}
