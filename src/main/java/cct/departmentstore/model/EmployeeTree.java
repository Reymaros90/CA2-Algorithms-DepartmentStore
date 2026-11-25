package ie.cct.departmentstore.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/** Ordinary binary tree: fill each level left-to-right, not a binary search tree. */
public final class EmployeeTree {
    private static final class Node {
        final Employee employee;
        Node left;
        Node right;
        Node(Employee employee) { this.employee = employee; }
    }

    private Node root;
    private int nodeCount;
    private final Queue<Node> availableParents = new ArrayDeque<>();

    public void insertLevelOrder(Employee employee) {
        Node created = new Node(employee);
        if (root == null) {
            root = created;
            availableParents.add(created);
        } else {
            Node parent = availableParents.peek();
            if (parent.left == null) {
                parent.left = created;
            } else {
                parent.right = created;
                availableParents.remove();
            }
            availableParents.add(created);
        }
        nodeCount++;
    }

    /** Each inner list is one level, arranged left to right. */
    public List<List<Employee>> levels() {
        List<List<Employee>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Employee> level = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                Node current = queue.remove();
                level.add(current.employee);
                if (current.left != null) queue.add(current.left);
                if (current.right != null) queue.add(current.right);
            }
            result.add(level);
        }
        return result;
    }

    /** Height is measured in levels: an empty tree has height 0. */
    public int height() { return levels().size(); }
    public int count() { return nodeCount; }
}
