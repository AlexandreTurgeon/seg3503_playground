import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Exercise 2 - Parameterized (data-driven) JUnit 4 test suite for the nextDate
 * test cases that produce a valid date (no exception expected).
 *
 * Each row provides the input date (y, m, d) and the expected next date
 * (ey, em, ed). The test asserts that new Date(y, m, d).nextDate() equals the
 * expected Date.
 */
@RunWith(Parameterized.class)
public class DateNextDateOkTest {

  private final int y, m, d;
  private final int ey, em, ed;

  public DateNextDateOkTest(int y, int m, int d, int ey, int em, int ed) {
    this.y = y;
    this.m = m;
    this.d = d;
    this.ey = ey;
    this.em = em;
    this.ed = ed;
  }

  @Parameters(name = "{index}: nextDate({0},{1},{2}) = ({3},{4},{5})")
  public static List<Integer[]> data() {
    return Arrays.asList(new Integer[][] {
        { 1700,  6, 20, 1700,  6, 21 }, // TC1
        { 2005,  4, 15, 2005,  4, 16 }, // TC2
        { 1901,  7, 20, 1901,  7, 21 }, // TC3
        { 3456,  3, 27, 3456,  3, 28 }, // TC4
        { 1500,  2, 17, 1500,  2, 18 }, // TC5
        { 1700,  6, 29, 1700,  6, 30 }, // TC6
        { 1800, 11, 29, 1800, 11, 30 }, // TC7
        { 3453,  1, 29, 3453,  1, 30 }, // TC8
        {  444,  2, 29,  444,  3,  1 }, // TC9  (leap year)
        { 2005,  4, 30, 2005,  5,  1 }, // TC10 (end of month)
        { 3453,  1, 30, 3453,  1, 31 }, // TC11
        { 3456,  3, 30, 3456,  3, 31 }, // TC12
        { 1901,  7, 31, 1901,  8,  1 }, // TC13 (end of month)
        { 3453,  1, 31, 3453,  2,  1 }, // TC14 (end of month)
        { 3456, 12, 31, 3457,  1,  1 }, // TC15 (end of year)
    });
  }

  @Test
  public void testNextDate() {
    Date expected = new Date(ey, em, ed);
    Date actual = new Date(y, m, d).nextDate();
    Assert.assertEquals(expected, actual);
  }
}
