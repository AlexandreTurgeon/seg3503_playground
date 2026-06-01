import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Exercise 2 - Explicit JUnit 5 (Jupiter) implementation of the nextDate test
 * suite, WITHOUT the Parameterized runner. Each test case from the lab
 * specification is written out as its own test method.
 *
 * Test cases that are expected to produce a valid date use assertEquals on the
 * Date returned by nextDate(). Test cases built from an invalid (year, month,
 * day) triple are expected to raise IllegalArgumentException (the Date
 * constructor validates its arguments).
 */
class DateTest {

  @Test
  void nextDate_sample() {
    Date d = new Date(2020, 5, 3);
    assertEquals(new Date(2020, 5, 4), d.nextDate());
  }

  // --- Test cases that return a valid next date -----------------------------

  @Test
  void tc01_1700_06_20() {
    assertEquals(new Date(1700, 6, 21), new Date(1700, 6, 20).nextDate());
  }

  @Test
  void tc02_2005_04_15() {
    assertEquals(new Date(2005, 4, 16), new Date(2005, 4, 15).nextDate());
  }

  @Test
  void tc03_1901_07_20() {
    assertEquals(new Date(1901, 7, 21), new Date(1901, 7, 20).nextDate());
  }

  @Test
  void tc04_3456_03_27() {
    assertEquals(new Date(3456, 3, 28), new Date(3456, 3, 27).nextDate());
  }

  @Test
  void tc05_1500_02_17() {
    assertEquals(new Date(1500, 2, 18), new Date(1500, 2, 17).nextDate());
  }

  @Test
  void tc06_1700_06_29() {
    assertEquals(new Date(1700, 6, 30), new Date(1700, 6, 29).nextDate());
  }

  @Test
  void tc07_1800_11_29() {
    assertEquals(new Date(1800, 11, 30), new Date(1800, 11, 29).nextDate());
  }

  @Test
  void tc08_3453_01_29() {
    assertEquals(new Date(3453, 1, 30), new Date(3453, 1, 29).nextDate());
  }

  @Test
  void tc09_444_02_29_leap() {
    // 444 is divisible by 4 and is not a century year, so it is a leap year.
    assertEquals(new Date(444, 3, 1), new Date(444, 2, 29).nextDate());
  }

  @Test
  void tc10_2005_04_30() {
    assertEquals(new Date(2005, 5, 1), new Date(2005, 4, 30).nextDate());
  }

  @Test
  void tc11_3453_01_30() {
    assertEquals(new Date(3453, 1, 31), new Date(3453, 1, 30).nextDate());
  }

  @Test
  void tc12_3456_03_30() {
    assertEquals(new Date(3456, 3, 31), new Date(3456, 3, 30).nextDate());
  }

  @Test
  void tc13_1901_07_31() {
    assertEquals(new Date(1901, 8, 1), new Date(1901, 7, 31).nextDate());
  }

  @Test
  void tc14_3453_01_31() {
    assertEquals(new Date(3453, 2, 1), new Date(3453, 1, 31).nextDate());
  }

  @Test
  void tc15_3456_12_31_endOfYear() {
    assertEquals(new Date(3457, 1, 1), new Date(3456, 12, 31).nextDate());
  }

  // --- Test cases that are expected to raise IllegalArgumentException --------

  @Test
  void tc16_1500_02_31_invalidDay() {
    assertThrows(IllegalArgumentException.class,
        () -> new Date(1500, 2, 31).nextDate());
  }

  @Test
  void tc17_1500_02_29_notLeap() {
    // 1500 is a century year not divisible by 400, so February has 28 days.
    assertThrows(IllegalArgumentException.class,
        () -> new Date(1500, 2, 29).nextDate());
  }

  @Test
  void tc18_negativeYear() {
    assertThrows(IllegalArgumentException.class,
        () -> new Date(-1, 10, 20).nextDate());
  }

  @Test
  void tc19_invalidMonth() {
    assertThrows(IllegalArgumentException.class,
        () -> new Date(1458, 15, 12).nextDate());
  }

  @Test
  void tc20_negativeDay() {
    assertThrows(IllegalArgumentException.class,
        () -> new Date(1975, 6, -50).nextDate());
  }
}
