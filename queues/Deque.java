/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item value;
        Node next, prev;

        public Node(Item val) {
            value = val;
            next = null;
            prev = null;
        }

        public Node() {
            value = null;
            next = null;
            prev = null;
        }
    }

    private Node head, tail;
    private int size;

    public Deque() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item");
        }
        if (isEmpty()) {
            head = new Node(item);
            tail = head;
            size++;
            return;
        }
        Node newNode = new Node(item);
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item");
        }
        if (isEmpty()) {
            head = new Node(item);
            tail = head;
            size++;
            return;
        }
        Node newNode = new Node(item);
        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("deque empty");
        }
        if (size == 1) {
            size = 0;
            Item ret = head.value;
            head = null;
            tail = null;
            return ret;
        }
        Item ret = head.value;
        head = head.next;
        head.prev = null;
        size--;
        return ret;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("deque empty");
        }
        if (size == 1) {
            size = 0;
            Item ret = head.value;
            head = null;
            tail = null;
            return ret;
        }
        Item ret = tail.value;
        tail = tail.prev;
        tail.next = null;
        size--;
        return ret;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node cur;

        public DequeIterator() {
            cur = head;
        }

        public boolean hasNext() {
            return cur != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item ret = cur.value;
            cur = cur.next;
            return ret;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "this deque implementation doesn't support remove in iterator");
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        for (int i : deque) {
            StdOut.println(i);
        }
    }
}
