import junit.framework.TestCase;
import static junit.framework.Assert.*;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class RandomizedQueueTest extends TestCase {
    private RandomizedQueue<Integer> q;
    
    protected void setUp() {
        q = new RandomizedQueue<Integer>();
    }
    
    
    /**
     * Test one element enqueue and dequeue operations
     */
    public void testOneElement() {
        q.enqueue(1);
        assertEquals(1, q.dequeue().intValue());
       
    }
    
    /**
     * Test a series of enque and deque equal number of times 
     */
    
    public void testEnqueDeque() {
        for (int i = 1; i <= 10; i++) {
            q.enqueue(i);
        }
        assertEquals(10,q.size());
        
        while (!q.isEmpty())
            StdOut.print(q.dequeue() + " ");
        StdOut.println();
        assertTrue(q.isEmpty());
    }
    
    /**
     *  Test sample method
     */
    public void testSample() {
    for (int i = 1; i <= 10; i++) {
            q.enqueue(i);
        }
     StdOut.println(q.sample());
     assertEquals(10,q.size());
    }
}
