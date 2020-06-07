import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPrimeFactorsCalculator {
    @Test
    public void primeFactorsCalculatorLowNumber() {
        checkMethod(0, Arrays.asList());
        checkMethod(1, Arrays.asList());
        checkMethod(2, Arrays.asList(2));
        checkMethod(3, Arrays.asList(3));
        checkMethod(4, Arrays.asList(2, 2));
        checkMethod(5, Arrays.asList(5));
        checkMethod(6, Arrays.asList(2, 3));
        checkMethod(7, Arrays.asList(7));
        checkMethod(8, Arrays.asList(2, 2, 2));
        checkMethod(9, Arrays.asList(3, 3));
        checkMethod(10, Arrays.asList(2, 5));
        checkMethod(11, Arrays.asList(11));
        checkMethod(12, Arrays.asList(2, 2, 3));
    }
    @Test
    public void primeFactorsCalculatorHighNumber() {
        checkMethodForBigNumber(Arrays.asList(2, 2, 2, 2, 3, 5, 7, 11));
        checkMethodForBigNumber(Arrays.asList(2, 2, 2, 2, 3, 5, 7, 11, 13));
    }


    private void checkMethod(int expected, List<Integer> actual) {
        PrimeFactorsCalculator call = new PrimeFactorsCalculator();
        assertEquals(call.calculatePrimeFactors(expected), actual);
    }

    private void checkMethodForBigNumber(List<Integer> listOfIntegers) {
        int result = listOfIntegers.stream().reduce(1, (a, b) -> a * b);
        checkMethod(result, listOfIntegers);
    }
}
