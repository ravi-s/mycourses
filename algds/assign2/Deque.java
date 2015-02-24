/**
 * ***********************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque < input.txt
 *
 *  A generic Deque, implemented using a double linked list. Each deque
 *  element is of type Item.
 *  @author Ravi S
 *  Date: 5-February-2015
 *
 *
 */

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 *  The <tt>Deque</tt> A double-ended queue or deque (pronounced "deck") 
 * is a generalization of a stack and a queue that 
 * supports inserting and removing items from 
 * either the front or the back of the data structure
 *  
 */
public class Deque<Item> implements Iterable<Item> {
    private int N;          // size of the deque
    private Node front;     // front of deque
    private Node back;      // back of deque
    
    // helper linked list class
    // Doubly-linked list to move back or forward
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
        
    }
    
    /**
     * Initializes an empty deque.
     */
    public Deque() {
        front = null;
        back = null; 
        N = 0;
        assert check();
    }
    
    /**
     * Is this deque empty?
     * @return true if this deque is empty; false otherwise
     */
    public boolean isEmpty() {
        return (front == null && back == null);
    }
    
    /**
     * Returns the number of items in the stack.
     * @return the number of items in the stack
     */
    public int size() {
        return N;
    }
    
    /**
     * Adds the item to this deque at the front.
     * @param item the item to add
     */
    public void addFirst(Item item) {
        if (item == null) { throw new NullPointerException("item is null"); }
        N++;
        Node oldfront = front;
        front = new Node();
        front.item = item;
        front.next = oldfront;
        front.previous = null;
        if (oldfront != null) { oldfront.previous = front; }
        
        if (N == 1 /*&& back == null*/) { back = front; }
        assert check();
    }
    
    /**
     * Adds the item to this deque at the back or last.
     * @param item the item to add
     */
    public void addLast(Item item) {
        if (item == null) { throw new NullPointerException("item is null"); }
        Node oldLast = back;
        back = new Node();
        back.item = item;
        if (oldLast != null) { 
            oldLast.next = back; 
            back.previous = oldLast;
        }
        back.next = null;
        N++;
        if (N == 1 /*&& front == null*/) { front = back; }
        
        assert check();
    }
    
    /**
     * Removes and returns the item most recently added deque from front
     * @return the item most recently added
     * @throws java.util.NoSuchElementException if this deque is empty
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("deque empty");
        Item item = front.item;        // save item to return
        front = front.next;            // delete first node
        
        if (front != null) {
            front.previous = null;
        }
        
        N--;
        if (N == 0) { 
            front = null; 
            back = null; 
        }
        assert check();
        return item;                   // return the saved item
    }
    
    /**
     * Removes and returns the item most recently added deque from back
     * @return the item most recently added
     * @throws java.util.NoSuchElementException if this deque is empty
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("deque empty");
        Item item = back.item;        // save item to return
        back = back.previous;        // Previous node becomes backmost node
        
        if (back != null) {
            back.next = null;
        }
        N--;
        if (N == 0) { 
            front = null;  
            back = null; 
        }
        assert check();
        return item;                   // return the saved item
    }
    
    
    /**
     * Returns an iterator to this deque that iterates through the items 
     * in front to last
     * @return an iterator to this stack that iterates through the items 
     * from front to back
     */
    public Iterator<Item> iterator()  { return new ListIterator();  }
    
    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = front;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { 
            throw new UnsupportedOperationException("remove not supported");  
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    
    
    // check internal invariants
    private boolean check() {
        if (N == 0) {
            //StdOut.println("when N is 0"+ N);
            if (front != null && back != null) return false;
        }
        else if (N == 1) {
            //StdOut.println("When N is 1:"+ N);
            if (front == null || back == null)      { 
                //StdOut.println("front or back is null");
                return false; 
            }
            if (front != back /*front.next != null || back.next != null*/) {
                //StdOut.println("front or back is not null");
                return false;
            }
        }
        else {
            if (front.next == null  || back.previous == null) return false;
        }
        
        // check internal consistency of instance variable N
//        int numberOfNodes = 0;
//        for (Node x = front; x != null; x = x.next) {
//            numberOfNodes++;
//        }
//        if (numberOfNodes != N) {  return false; }
        
        return true;
    }
    
    /**
     * Unit tests the <tt>LinkedStack</tt> data type.
     */
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
        
        int N = Integer.parseInt(args[0]);
        
        // Test case 1
        StdOut.println("Executing Test case 1");
        
        for (int i = 1; i <= N; i++) {
            dq.addFirst(i);
        }
        StdOut.println("Test case 1: addFirst() method passed");
        
        // Test iterator for testcase 1
        for (Number item: dq) 
            StdOut.print(item + " ");
        StdOut.println();
        StdOut.println("Test case 1: Iterator test passed");
        
        while (!dq.isEmpty()) {
            StdOut.print(dq.removeLast() + " ");
            
        }
        StdOut.println();
        StdOut.println("Test case 1: removeLast() method passed");
        
        // Test case 2
        StdOut.println("Executing Test case 2");
        
        for (int i = 1; i <= N; i++) {
            dq.addLast(i);
            
        }
        StdOut.println("Test case 1: addLast() method passed");
        // Test iterator for testcase 2
        
        for (Number item: dq) 
            StdOut.print(item + " ");
        StdOut.println();
        StdOut.println("Test case 2: Iterator test passed");
        
        while (!dq.isEmpty()) {
            StdOut.print(dq.removeFirst() + " ");
            
        }
        StdOut.println();
        StdOut.println("Test case 2: removeFirst() test passed");
        
        // Test case 3: Test non-Empty to Empty to Non-Empty
        assert dq.isEmpty();
        dq.addFirst(10);
        dq.removeFirst();
        assert dq.isEmpty();
        dq.addFirst(10);
        assert !dq.isEmpty();
        for (Number item: dq) StdOut.println(item);
        StdOut.println("Test case 3: Test non-Empty to Empty to Non-Empty success");
        
        // Test case 4: Test nested iterators
        dq.removeFirst();
        
        // Add N items
        for (int i = 1; i <= N; i++) {
            dq.addFirst(i);
        }
        
        for (Number item: dq) {
            for (Number val: dq) {
                StdOut.print(val + " ");
            }
            StdOut.println();
        }
        StdOut.println("Test case 4: nested iterators success");
    }
}

