import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestsStringCalculator {

    @Test
    void testEmptyString() {
        checkMethod(0, "");
    }

    @Test
    void testOneNumber() {
        checkMethod(6, "6");
        checkMethod(8, "8");
    }

    @Test
    void testTwoNumbers() {
        checkMethod(3, "1,2");
        checkMethod(7, "3,4");
    }

    @Test
    void testMultipleNumbers() {
        checkMethod(6, "1,2,3");
        checkMethod(22, "3,7,5,7");
    }

    @Test
    void testNewLineSeparator() {
        checkMethod(6, "1\n2,3");
        checkMethod(16, "5,2\n3\n6");
    }

    @Test
    void testMultipleNewLineAndComma() {
        checkMethod(6, "1,2\n3");
        checkMethod(20, "1,2\n3,11\n3");
    }

    @Test
    void testCustomDelimiter() {
        checkMethod(6, "//[:]\n1:2:3");
        checkMethod(15, "//[;]\n1;;2;3;9");
    }

    @Test
    void testNegatives() {
        calculateNegativesException("-1", "negatives not allowed -1");
        calculateNegativesException("-1,-5,-3", "negatives not allowed -1,-5,-3");
    }

    @Test
    void numberBiggerThan1000Ignored() {
        checkMethod(2, "2,1001");
        checkMethod(7, "7,5001");
    }

    @Test
    void delimiterCanBeAnyLength() {
        checkMethod(6, "//[***]\n1***2***3");
        checkMethod(11, "//[##]\n1##2##3##5");
    }

    @Test
    void manyDelimiterCanBeAnyLength() {

        checkMethod(6, "//[***][%%]\n1***2%%3");
        checkMethod(9, "//[***][%%][###]\n1***2%%3###3");
    }

    private void checkMethod(int expected, String actual) {
        StringCalculator call = new StringCalculator();
        assertEquals(expected, call.add(actual));
    }

    private void calculateNegativesException(String negativeNumbers, String exceptionMessage) {
        StringCalculator call = new StringCalculator();
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            call.add(negativeNumbers);
        });
        assertEquals(thrown.getMessage(), exceptionMessage);
    }
}
