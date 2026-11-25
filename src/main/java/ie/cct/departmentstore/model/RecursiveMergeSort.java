package ie.cct.departmentstore.model;

import java.util.ArrayList;
import java.util.List;

/** Recursive, stable sorting by full employee name (case-insensitive). */
public final class RecursiveMergeSort {
    public List<Employee> sort(List<Employee> original) {
        if (original.size() <= 1) return new ArrayList<>(original);
        int midpoint = original.size() / 2;
        List<Employee> left = sort(original.subList(0, midpoint));
        List<Employee> right = sort(original.subList(midpoint, original.size()));
        return merge(left, right);
    }

    private List<Employee> merge(List<Employee> left, List<Employee> right) {
        List<Employee> result = new ArrayList<>(left.size() + right.size());
        int i = 0;
        int j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getName().compareToIgnoreCase(right.get(j).getName()) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }
        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));
        return result;
    }
}
