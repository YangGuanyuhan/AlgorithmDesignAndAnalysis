import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HelloTest {

    @Test
    void additionOperation() {
        assertEquals(5.0, performCalculation(2.0, '+', 3.0));
    }

    @Test
    void subtractionOperation() {
        assertEquals(1.0, performCalculation(3.0, '-', 2.0));
    }

    @Test
    void multiplicationOperation() {
        assertEquals(6.0, performCalculation(2.0, '*', 3.0));
    }

    @Test
    void divisionOperation() {
        assertEquals(2.0, performCalculation(6.0, '/', 3.0));
    }

    @Test
    void divisionByZero() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            performCalculation(6.0, '/', 0.0);
        });
        assertEquals("除数不能为零！", exception.getMessage());
    }

    @Test
    void invalidOperator() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            performCalculation(6.0, '%', 3.0);
        });
        assertEquals("无效的运算符！", exception.getMessage());
    }

    private double performCalculation(double num1, char operator, double num2) {
        double result;
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    throw new ArithmeticException("除数不能为零！");
                }
                break;
            default:
                throw new IllegalArgumentException("无效的运算符！");
        }
        return result;
    }
}