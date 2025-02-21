package com.anurag.techM.Junit_Testing_Task;

import com.anurag.techM.Junit_Testing_Task.Task4;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class Task4_Test {
	private final int input;
    private final int expected;
    private final Task4 object = new Task4();

    public Task4_Test(int input, int expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
            {2, 4}, {3, 9}, {4, 16}, {-5, 25}, {0, 0}
        });
    }

    @Test
    public void testMultiply() {
        assertEquals(expected, object.multiply(input));
    }
}
