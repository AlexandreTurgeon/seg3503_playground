import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Exercise 2 - Parameterized (data-driven) JUnit 4 test suite for the nextDate
 * test cases that are expected to raise an IllegalArgumentException.
 *
 * Each row is an invalid (year, month, day) triple. Building such a date (which
 * nextDate exercises) must raise IllegalArgumentException. Because every row in
 * this suite expects the same exception, it is declared once via
 * {@code @Test(expected = ...)}.
 */
@RunWith(Parameterized.class)
public class DateNextDateExceptionTest {

  private final int y, m, d;

  public DateNextDateExceptionTest(int y, int m, int d) {
    this.y = y;
    this.m = m;
    this.d = d;
  }

  @Parameters(name = "{index}: nextDate({0},{1},{2}) throws")
  public static List<Integer[]> data() {
    return Arrays.asList(new Integer[][] {
        { 1500,  2, 31 }, // TC16 February never has 31 days
        { 1500,  2, 29 }, // TC17 1500 is not a leap year (century, not /400)
        {   -1, 10, 20 }, // TC18 year must be >= 0
        { 1458, 15, 12 }, // TC19 month must be between 1 and 12
        { 1975,  6, -50 }, // TC20 day must be >= 1
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNextDateThrows() {
    new Date(y, m, d).nextDate();
  }
}
