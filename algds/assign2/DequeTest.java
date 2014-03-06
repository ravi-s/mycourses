import junit.framework.TestCase;
import static org.junit.Assert.*;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class DequeTest extends TestCase {
    
    
    /**
     * test case 1
     
     */
    public void testAddFirst() {
        Deque<Integer> dq = new Deque<Integer>();
        for (int i = 1; i <= 10; i++) {
            dq.addFirst(i);
            
        }
        assertTrue(10 == dq.size());
    }
    
    /**
     * test case 2
     */
    public void testAddLast() {
        Deque<Integer> dq = new Deque<Integer>();
        for (int i = 1; i <= 10; i++) {
            dq.addLast(i);
            
        }
        assertTrue(10 == dq.size());
    }
    
   /** 
    * Test case 3
    * 
    */
    public void testEmpty() {
        Deque<Integer> dq = new Deque<Integer>();
        assertTrue(dq.isEmpty());
        
        // Try to remove from an empty deque
        try {
            int item = dq.removeFirst();
            fail ("This is incorrect. Cannot remove from an empty deque");
             
            
        } 
        catch (java.util.NoSuchElementException e) {
            
        }
        try {
            int item = dq.removeLast();
            fail ("This is incorrect. Cannot remove from an empty deque");
             
            
        } 
        catch (java.util.NoSuchElementException e) {
            
        }
        
        
    }
    
    /**
     * Test case 4 - Test 1 element 
     * add 1 element from front, remove from back
     * add 1 element from back, remove from front
     */
    public void testOneElement() {
        Deque<Long> dq = new Deque<Long>();
        dq.addFirst(1L);
        long val = dq.removeLast();
        assertEquals(1L, val);
        assertTrue(dq.size() == 0);
        
        // the other way
        dq.addLast(1L);
        val = dq.removeFirst();
        assertEquals(1L, val);
        assertTrue(dq.size() == 0);
        
        
    }
    
    /**
     *  Test case 5 - check if you can create more than one iterator at the same time
     * 
     */

    public void testIterator() {
        Deque<Integer> dq = new Deque<Integer>();
        for (int i = 1; i <= 10; i++) {
            dq.addFirst(i);
        }
        // Test iterator
        for (Number i: dq) {
            StdOut.print(i + ":");
            for (Number j: dq) 
                StdOut.print(j + ",");
            StdOut.println();
        }   
            
    }
    
    /**
     *  Test case 6 - check for nullpointer exception for 
     * items that are null added to deque
     */
    
    public void testAddItemNull() {
        Deque<Integer> dq = new Deque<Integer>();
        
        
        // Try to add null item to an empty deque
            dq.addFirst(null);
            fail ("This is incorrect. Cannot add null item to  deque");
            dq.addLast(null);
            fail ("This is incorrect. Cannot remove from an empty deque");
    }
}
