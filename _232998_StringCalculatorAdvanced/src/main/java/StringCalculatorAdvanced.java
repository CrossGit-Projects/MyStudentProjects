import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

class StringCalculatorAdvanced {

    public static void main(String[] args) {
        StringCalculatorAdvanced call = new StringCalculatorAdvanced();
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj dane wejściowe do obliczeń: ");
        String infix = scan.nextLine();

        System.out.println("Infix:  " + infix);
        List<String> postFixString = call.compute(infix);
        //System.out.println("Postfix: " + postFixString);
        System.out.print("Postfix: ");
        for (String postfix: postFixString) {
            System.out.print(postfix+ " ");
        }
    }

    public List<String> compute(String s) {
        s = deleteWhiteSpace(s);
        List<String> postFixString = parse(s);
        return postFixString;
    }

    private List<String> parse(String s) {
        Stack<Character> stack = new Stack<>();
        List<String> elements = new ArrayList<>();
        boolean isMultiDigitNumber = false;
        for (int i = 0; i < s.length(); i++) {
            char characterElement = s.charAt(i);
            if (characterElement == '+' || characterElement == '-' || characterElement == '*'
                    || characterElement == '/' || characterElement == '^') {
                isMultiDigitNumber = false;
                if (!stack.isEmpty()) {
                    while (!stack.isEmpty() && getOperatorPriority(stack.peek()) >= getOperatorPriority(characterElement)) {
                        elements.add(stack.pop() + "");
                    }
                }
                stack.push(characterElement);
            } else if (characterElement == '(') {
                stack.push(characterElement);
                isMultiDigitNumber = false;
            } else if (characterElement == ')') {
                isMultiDigitNumber = false;
                while (!stack.isEmpty()) {
                    if (stack.peek() == '(') {
                        stack.pop();
                        break;
                    } else {
                        elements.add(stack.pop() + "");
                    }
                }
            } else {
                if (isMultiDigitNumber) {
                    String lastNumber = elements.get(elements.size() - 1);
                    lastNumber += characterElement;
                    elements.set(elements.size() - 1, lastNumber);
                } else {
                    elements.add(characterElement + "");
                    isMultiDigitNumber = true;
                }
            }
        }
        while (!stack.isEmpty()) {
            elements.add(stack.pop().toString());
        }
        return elements;
    }

    private String deleteWhiteSpace(String s) {
        return s.replaceAll(" ", "");
    }

    private double getOperatorPriority(char c) {
        if (c == '+' || c == '-') {
            return 1;
        } else if (c == '*' || c == '/') {
            return 2;
        } else if (c == '^') {
            return 3;
        } else return -1;
    }
}
