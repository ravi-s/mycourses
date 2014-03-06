/*************************************************************************
 *   
 *  
 *  Queue implementation with a resizing array.
 *
 * 
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The <tt>RandomizeQueue</tt> class represents a first-in-first-out (FIFO)
 *  queue of generic items.
 *  It supports the usual <em>enqueue</em> and <em>dequeue</em>
 *  operations, along with methods for peeking at the first item,
 *  testing if the queue is empty, and iterating through
 *  the items in FIFO order.
 *  <p>
 *  This implementation uses a resizing array, which double the underlying array
 *  when it is full and halves the underlying array when it is one-quarter full.
 *  The <em>enqueue</em> and <em>dequeue</em> operations take constant amortized time.
 *  The <em>size</em>, <em>peek</em>, and <em>is-empty</em> operations takes
 *  constant time in the worst case. 
 *  
 *
 *  @author Ravi Shankar
 */
public class RandomizeQueue<Item> implements Iterable<Item> {
    private Item[] q;            // queue elements
    private int N = 0;           // number of elements on queue
    private int first = 0;       // index of first element of queue
    private int last  = 0;       // index of next available slot


    /**
     * Initializes an empty queue.
     */
    public RandomizeQueue() {
        // cast needed since no generic array creation in Java
        q = (Item[]) new Object[2];
    }

    /**
     * Is this queue empty?
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns the number of items in this queue.
     * @return the number of items in this queue
     */
    public int size() {
        return N;
    }

    

    /**
     * Adds the item to this queue.
     * @param item the item to add
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("item is a null object");
        
        // double size of array if necessary and recopy to front of array
        if (N == q.length) resize(2*q.length);   // double size of array if necessary
        q[last++] = item;                        // add item
        if (last == q.length) last = 0;          // wrap-around
        N++;
    }

    /**
     * Removes and returns a random item on this queue 
     * @return the item on this queue randomly
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        
        int idx = randomIndex();
        
        if (q[idx] != null) { 
            Item item = q[idx]; 
            q[idx] = null; // to avoid loitering
             N--;
             if (idx == first) { first++; } 
        }
        
        
        if (first == q.length) first = 0;           // wrap-around
        // shrink size of array if necessary
        if (N > 0 && N == q.length/4) resize(q.length/2); 
        return item;
    }

    /**
     *  Return a random item without deleting from queue
     */
    public Item sample() {
         if (isEmpty()) throw new NoSuchElementException("Queue underflow");
         int idx = randomIndex();
         while (q[idx] != null) {
             idx = randomIndex();
         }
         return q[idx];
    }
        

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext()  { return i < N;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[(i + first) % q.length];
            i++;
            return item;
        }
    }

    // resize the underlying array
    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        // Like resizing queue, but take care of nulls that are interstitial
        int size = N;
        for (int i = 0; i < size; i++) {
            if (q[first + i] != null) {
               temp[i] = q[(first + i) % q.length];
            }
        }
        q = temp;
        first = 0;
        last  = N;
    }
    
    /**
     *  Get a random index for the queue datastructure
     * 
     */
    private int randomIndex() {
        / Using the uniform random generator API, get a random index
        int idx = StdRandom(q.length + 1); /* Note that N is the number of 
                                            elements and should not be used
                                            for index */
        return idx;
    }
   /**
     * Unit tests the <tt>RandomizeQueue</tt> data type.
     */
    public static void main(String[] args) {
        RandomizeQueue<String> q = new RandomizeQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) q.enqueue(item);
            else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }

}