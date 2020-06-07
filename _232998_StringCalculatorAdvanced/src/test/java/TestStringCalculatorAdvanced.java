import org.junit.jupiter.api.Test;

import static java.lang.Math.pow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestStringCalculatorAdvanced {


    @Test
    void testTwoNumbersSum() {
        checkMethod(3.5, "1+2.5");
        checkMethod(5.5, "4+1.5");
        checkMethod(10.5, "7+3.5");
    }

    @Test
    void testTwoNumbersMin() {
        checkMethod(4.5, "7-2.5");
        checkMethod(2.5, "4-1.5");
        checkMethod(11.5, "15-3.5");
    }

    @Test
    void testTwoNumbersMultiple() {
        checkMethod(14, "7*2.0");
        checkMethod(6, "4*1.5");
        checkMethod(45, "15*3.0");
    }

    @Test
    void testTwoNumbersDivision() {
        checkMethod(10, "20/2.0");
        checkMethod(3, "33/11.0");
        checkMethod(5, "15/3.0");
    }

    @Test
    void testTwoNumbersPower() {
        checkMethod(400, "20^2");
        checkMethod(1024, "4^5");
        checkMethod(125, "5^3");
    }

    @Test
    void testWhiteSignsCombination() {
        checkMethod(4.4, "2.2*       2");
        checkMethod(0.8, "4/          5");
        checkMethod(125, "5^     3");
    }

    @Test
    void testDifferentOperation() {
        checkMethod(19.5, "1.5+2*3^2");
        checkMethod(-10.5, "1.5-2*3*2");
        checkMethod(6, "3^2+4-7");
        checkMethod(8, "3*2-9/3^2+3");
    }

    @Test
    void testDifferentOperationWithParenthesis() {
        checkMethod(5, "(3 + 2)");
        checkMethod(20, "(3 + 2)*4");
        checkMethod(31.5, "(1.5 + 2) * 3^2");
    }

    @Test
    void testDifferentOperationWithParenthesisForBigNumber() {
        checkMethod(pow(((2.4 - 0.4) * 3), 4), "((2.4 - 0.4) * 3)^4");
        checkMethod(pow(9, 3) - (4 * 7 - (3 + 23.4) * 7) / 2, "9^3-(4*7-(3+23.4)*7)/2");
        checkMethod(((123 - 44) * 3) / 6.0 + pow(7, 3), "((123-44)*3)/6+7^3");
        checkMethod(((1873 + 43) * 8) / 2.0 + pow(7, 9), "((1873+43)*8)/2+7^9");
    }

    @Test
    void shouldReturnExceptionWhenBetweenOperatorsDoNotFindNumber() {
        computeNegativesException("Missing number between - and * operators", "20-*2");
        computeNegativesException("Missing number between + and / operators", "20.0+/2+3-4");
        computeNegativesException("Missing number between + and * operators", "20-2+55/44+*3");
        computeNegativesException("Missing number between - and + operators", "20-+2+/44-33");
    }

    @Test
    void shouldReturnExceptionWhenIsInvalidNumber() {
        computeNegativesException("Invalid number detected: 2a", "2a*3");
        computeNegativesException("Invalid number detected: 66a", "2*3+66a-44/33");
        computeNegativesException("Invalid number detected: 10b", "(2*3)+10b-(44/33a)");
        computeNegativesException("Invalid number detected: 33c", "((2*3)*4)+33c+56h");
    }

    @Test
    void shouldReturnExceptionWhenIsExtraRightParenthesis() {
        computeNegativesException("An extra right parenthesis detected", "24*3)");
        computeNegativesException("An extra right parenthesis detected", "((56*3)+4-5))");
        computeNegativesException("An extra right parenthesis detected", "(27/3))*4)))");
        computeNegativesException("An extra right parenthesis detected", "((6-4)*3)/3-5(5+4)))))))");
    }

    @Test
    void shouldReturnExceptionWhenIsDivisionByZero() {
        computeNegativesException("Division by zero", "20/0");
        computeNegativesException("Division by zero", "4+55-1.8/0");
        computeNegativesException("Division by zero", "(4/0)-1.8/0");
        computeNegativesException("Division by zero", "(4+42)/0");
    }

    @Test
    void testPercentOperator() {
        checkMethod(1.5, "2*75%");
        checkMethod(5.0, "3+5*20*2%");
        checkMethod(23.5, "(4*50%)+43/2");
        checkMethod(738.15, "(232+34)*555%/2");
    }

    private void checkMethod(double expected, String actual) {
        StringCalculatorAdvanced call = new StringCalculatorAdvanced();
        assertEquals(expected, call.compute(actual));
    }

    private void computeNegativesException(String exceptionMessage, String negativeStringOperation) {
        StringCalculatorAdvanced call = new StringCalculatorAdvanced();
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            call.compute(negativeStringOperation);
        });
        assertEquals(thrown.getMessage(), exceptionMessage);
    }

}
