/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int tail, size;
    private Item[] seq;

    public RandomizedQueue() {
        tail = -1;
        size = 0;
        seq = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int newSize) {
        Item[] newSeq = (Item[]) new Object[newSize];
        for (int i = 0; i <= tail; i++) {
            newSeq[i] = seq[i];
        }
        seq = newSeq;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("cannot enqueue null");
        }
        if (tail == seq.length - 1) {
            resize(2 * seq.length);
        }
        seq[++tail] = item;
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("queue empty");
        }
        int pos = tail == 0 ? 0 : StdRandom.uniformInt(tail + 1);
        Item tmp = seq[pos];
        seq[pos] = seq[tail];
        seq[tail] = null;
        tail--;
        size--;
        if (tail == seq.length / 4 && seq.length > 0) {
            resize(seq.length / 2);
        }
        return tmp;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("queue empty");
        }
        return tail == 0 ? seq[0] : seq[StdRandom.uniformInt(tail + 1)];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] perm;
        private int visitedNumber;

        public RandomizedQueueIterator() {
            if (size > 1) {
                perm = StdRandom.permutation(tail + 1);
            }
            else {
                perm = new int[1];
            }
            visitedNumber = 0;
        }

        public boolean hasNext() {
            return visitedNumber <= tail;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("last element");
            }
            Item ret = seq[perm[visitedNumber]];
            visitedNumber++;
            return ret;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "this randomized queue implementation doesn't support remove in iterator");
        }
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int k = StdIn.readInt();
        for (int i = 1; i <= k; i++) {
            rq.enqueue("A" + i);
        }
        for (String s : rq) {
            System.out.println(s);
        }
    }
}
