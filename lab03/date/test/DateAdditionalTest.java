import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DateAdditionalTest {

  @Test
  void constructor_allowsYearZeroAndFormatsDate() {
    Date date = new Date(0, 1, 1);

    assertEquals(0, date.getYear());
    assertEquals(1, date.getMonth());
    assertEquals(1, date.getDay());
    assertEquals("0/January/1", date.toString());
  }

  @Test
  void constructor_rejectsMonthBelowRange() {
    assertThrows(IllegalArgumentException.class, () -> new Date(2024, 0, 1));
  }

  @Test
  void constructor_rejectsDayAboveGlobalRange() {
    assertThrows(IllegalArgumentException.class, () -> new Date(2024, 1, 32));
  }

  @Test
  void constructor_rejectsThirtyDayMonthWithThirtyOneDays() {
    assertThrows(IllegalArgumentException.class, () -> new Date(2024, 4, 31));
  }

  @Test
  void constructor_rejectsLeapFebruaryWithThirtyDays() {
    assertThrows(IllegalArgumentException.class, () -> new Date(2024, 2, 30));
  }

  @Test
  void nextDate_handlesCenturyLeapYear() {
    Date today = new Date(2000, 2, 29);
    Date expectedTomorrow = new Date(2000, 3, 1);

    assertTrue(today.isLeapYear());
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_handlesNonLeapFebruaryEnd() {
    Date today = new Date(1900, 2, 28);
    Date expectedTomorrow = new Date(1900, 3, 1);

    assertFalse(today.isLeapYear());
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_handlesJuneEnd() {
    Date today = new Date(2024, 6, 30);
    Date expectedTomorrow = new Date(2024, 7, 1);

    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_handlesSeptemberEnd() {
    Date today = new Date(2024, 9, 30);
    Date expectedTomorrow = new Date(2024, 10, 1);

    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void equals_rejectsOtherTypesAndDifferentDates() {
    Date date = new Date(2024, 6, 9);

    assertNotEquals(date, "2024/June/9");
    assertNotEquals(date, new Date(2025, 6, 9));
    assertNotEquals(date, new Date(2024, 7, 9));
    assertNotEquals(date, new Date(2024, 6, 10));
  }
}
