import java.util.ArrayList;
import java.util.List;

public class PrimeFactorsCalculator {

    public List<Integer> calculatePrimeFactors(int number) {
        List<Integer> factors = new ArrayList<>();
        for (int divisior = 2; number > 1; divisior++) {
            for (; number % divisior == 0; number /= divisior) {
                factors.add(divisior);
            }
        }
        return factors;
    }
}
