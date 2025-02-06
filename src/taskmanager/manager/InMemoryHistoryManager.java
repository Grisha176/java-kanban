package taskmanager.manager;

import taskmanager.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {


    public static class Node {
        Task item;
        Node next;
        Node prev;

        public Node(Task item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node head;
    private Node tail;
    private final Map<Integer, Node> map = new HashMap<>();

    private void linkLast(Task task) {
        Node node = new Node(task, null, tail);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        map.put(task.getId(), node);
    }

    public List<Task> getHistory() {
        List<Task> list = new ArrayList<>();
        for (Node current = head; current != null; current = current.next) {
            list.add(current.item);
        }
        return list;
    }

    private boolean removeNode(Node node) {
        if ((head == null && tail == null)) {
            return false;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        node.item = null;
        node.next = null;
        node.prev = null;
        return true;

    }

    @Override
    public void addHistory(Task task) {
        if (task == null) {
            return;
        }
        Node taskInMap = map.get(task.getId());
        if (taskInMap != null) {
            removeNode(taskInMap);
        }
        linkLast(task);
    }

    @Override
    public void removeID(int id) {
        map.remove(id);
    }

}
