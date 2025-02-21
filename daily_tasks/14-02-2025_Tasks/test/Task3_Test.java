package com.anurag.techM.Junit_Testing_Task;

import org.junit.*;
import static org.junit.Assert.*;
import com.anurag.techM.Junit_Testing_Task.Task3;

public class Task3_Test {
	private Task3 object;

    @Before
    public void setUp() {
        object = new Task3();
        System.out.println("Setup: Creating a new Task3 instance");
    }

    @After
    public void tearDown() {
        object = null;
        System.out.println("Teardown: Deallocating the memory and cleaning up");
    }

    @Test
    public void testSubtract() {
        assertEquals(5, object.subtract(10, 5));
    }

    @Test
    public void testIsOdd() {
        assertTrue(object.checkOdd(11));
        assertFalse(object.checkOdd(10));
    }

}
