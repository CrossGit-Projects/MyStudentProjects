import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StringCalculator {

    List<String> negatives = new ArrayList<String>();

    public int add(String s) {
        if (s.equals("")) {
            return 0;
        } else {
            if (s.contains(",")) {
                String numbers[] = s.split(",|\n");
                return sum(numbers);
            }
            else if (s.contains("//")) {
                String newRegex = s.substring(2, s.indexOf("\n"));
                StringBuilder sb = new StringBuilder(newRegex);
                for (int i = 0; i < newRegex.length(); ++i) {
                    char ch = '|';
                    if (sb.charAt(i) == ']') {
                        sb.insert(sb.indexOf("]",i) + 1, ch);
                    }
                }
                String regex1 = sb.toString();
                String regex2 = regex1.replaceAll("\\[", "\\Q").replaceAll("]","\\E");
                //String regex3 = regex1.substring(0, regex1.length()-1);
                System.out.println(regex2);

                String newString = s.substring(s.indexOf("\n") + 1);
                String numbers[] = newString.split(regex1);
                return sum(numbers);
            }
            String[] numbers = s.split(" ");
            return sum(numbers);
        }
    }

    private int sum(String[] numbers) {
        int num = 0;
        int total = 0;
        for (String number : numbers) {
            if (!number.isEmpty()) {
                num = toInt(number);
                if (num < 1000) {
                    if (num < 0) {
                        negatives.add(number);
                    }
                    total += num;
                }
            }
        }
        if (!negatives.isEmpty()) {
            throw new RuntimeException(
                    "negatives not allowed " + String.join(",", negatives));
        }
        return total;
    }

    private int toInt(String number) {
        return Integer.parseInt(number);
    }

}
