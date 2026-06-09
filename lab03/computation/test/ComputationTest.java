
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ComputationTest {

  Computation computation = new Computation();

  @Test
  // A test that does what it should
  public void add() {
    int addResult = computation.add(1,1);
    assertEquals(2, addResult);
    assertEquals(Integer.MIN_VALUE + 1, computation.add(Integer.MIN_VALUE, 1));
  }

  @Test
  // a test that does what it shouldn't
  public void substract() {
    assertEquals(3, computation.substract(5, 2));
    assertEquals(-7, computation.substract(-5, 2));
  }

  @Test
  // a test that partially does what it should
  public void divide() {
    double divideResult = computation.divide(1, 2);
    assertEquals(0.5d, divideResult, 0.0d);
    assertEquals(Double.POSITIVE_INFINITY, computation.divide(1, 0), 0.0d);
  }

  @Test
  public void catchesException() {
    computation.catchesException();
  }

  @Test
  public void multiply() {
    assertEquals(10, computation.multiply(5,2));
    assertEquals(0, computation.multiply(100, 0));
  }

  @Test
  public void justALoop() {
    assertEquals(2, computation.justALoop());
  }
}
