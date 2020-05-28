import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Random Queue implementation with a resizing array.
 * * @author RaviS
 * The <tt>RandomizeQueue</tt> class represents a first-in-first-out (FIFO)
 * queue of generic items.
 * It supports the usual <em>enqueue</em> and <em>dequeue</em>
 * operations, along with methods for peeking at the first item,
 * testing if the queue is empty, and iterating through
 * the items in FIFO order.
 * <p>
 * This implementation uses a resizing array, which double the underlying array
 * when it is full and halves the underlying array when it is one-quarter full.
 * The <em>enqueue</em> and <em>dequeue</em> operations take constant
 * amortized time.
 * The <em>size</em>, <em>peek</em>, and <em>is-empty</em> operations takes
 * constant time in the worst case.
 *
 * @author Ravi Shankar
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;            // queue elements
    private int items = 0;           // number of elements on queue
    private int first = 0;       // index of first element of queue
    private int last = 0;       // index of next available slot


    /**
     * Initializes an empty queue.
     */
    public RandomizedQueue() {
// cast needed since no generic array creation in Java
        queue = (Item[]) new Object[1];

        // Set random number seed
        StdRandom.setSeed(System.currentTimeMillis());
    }

    /**
     * Is this queue empty?
     *
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return 0 == items;
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int size() {
        return items;
    }


    /**
     * Adds the item to this queue.
     *
     * @param item the item to add
     * @throws IllegalArgumentException if item is null
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is a null object");
        }

        // double size of array if necessary and recopy to front of array
        if (items == queue.length) {
            resize(2 * queue.length);
        } // double size of array if necessary
        queue[last++] = item;     // add item
        if (last == queue.length) {
            last = 0;
        }       // wrap-around
        items++;
    }

    /**
     * Removes and returns a random item on this queue.
     *
     * @return the item on this queue randomly.
     * @throws java.util.NoSuchElementException if this queue is empty.
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }

        int idx = randomIndex();
        // Swap random element at idx with first
        Item swap = queue[first];
        queue[first] = queue[idx];
        queue[idx] = swap;

        // Deque the random element, now in index pointed by first
        Item item = queue[first];
        queue[first] = null; // to avoid loitering
        items--;
        first++;


        if (first == queue.length) {
            first = 0;
        }        // wrap-around
        // shrink size of array if necessary
        if (items > 0 && items == queue.length / 4) {
            resize(queue.length / 2);
        }
        return item;
    }

    /**
     * Return a random item without deleting from queue.
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        int idx = randomIndex();
        return queue[idx];
    }


    /**
     * Returns an iterator that iterates over the items
     * in this queue in random order.
     *
     * @return an iterator that iterates over the items
     * in this queue in random order
     */
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private RandomizedQueue<Item> iterq;

        /*
         * The constructor copies the deque into its own copy.
         */
        ArrayIterator() {
            iterq = new RandomizedQueue<>();
            int size = items;
            iterq.resize(size);
            for (int j = 0; j < size; j++) {
                iterq.queue[j] = queue[(first + j) % queue.length];

            }
            iterq.items = size;
        }

        public boolean hasNext() {
            return i < items;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove operation not supported");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            //Item item = q[(i + first) % q.length];
            Item item = iterq.dequeue();
            i++;
            return item;
        }
    }

    // resize the underlying array
    private void resize(int max) {
        assert max >= items;
        Item[] temp = (Item[]) new Object[max];

        int size = items;
        for (int i = 0; i < size; i++) {
            temp[i] = queue[(first + i) % queue.length];
        }
        queue = temp;
        first = 0;
        last = items;
    }

    /**
     * Get a random index for the queue data structure.
     */
    private int randomIndex() {
        // Using the uniform random generator API, get a random index
        if (items == 1) {
            return 0;
        }
        return StdRandom.uniform(first, (first + (items - 1)) % queue.length);
    }

    /**
     * Unit tests the <tt>RandomizeQueue</tt> data type.
     */
    public static void main(String[] args) {

        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        int itemCount = 25;

        /*
         * Test case 1: Check randomized queue basic operations
         */
        StdOut.println("Test case 1: Enqueue N items and check iterator");
        for (int i = 0; i <= itemCount; i++) {
            queue.enqueue(i);
        }
        assert queue.size() == itemCount;
        StdOut.println("25 items enqueued successfully. Test passed");

        /*
         * Test case 2: Check Randomized queue iterator() test
         */

        // Check Iterator
        for (Number item : queue) {
            StdOut.print(item + " ");
        }
        StdOut.println("Randomized Queue iterator test passed!");

        /*
         * Test case 3: Check Randomized queue sample() test
         */
        // Check 5 random sample
        // It should be uniformly random sampled output
        for (int i = 1; i <= 5; i++) {
            StdOut.print(queue.sample() + " ");
        }
        StdOut.println();

        assert queue.size() == 25;

        /*
         * Test case 4: Check Randomized queue dequeue() test
         * dequeue 10 random elements. Ensure size is correct after this operation
         */

        for (int i = 1; i <= 10; i++) {
            StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println();
        assert queue.size() == 15;


        // Check if iterator can be mutually nested
        /*for (Number item1 : queue) {
            StdOut.println(item1);
            StdOut.println("----");
            for (Number item2 : queue) {
                StdOut.print(item2 + " ");
            }
            StdOut.println();
        }


        for (int i = 0; i <= itemCount; i++) {
            StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println();*/


        /*queue.enqueue(null);

        queue.dequeue();*/
    }
}

