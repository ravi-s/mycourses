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

}
