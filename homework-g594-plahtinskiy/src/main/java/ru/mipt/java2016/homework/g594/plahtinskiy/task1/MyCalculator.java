package ru.mipt.java2016.homework.g594.plahtinskiy.task1;

import ru.mipt.java2016.homework.base.task1.Calculator;
import ru.mipt.java2016.homework.base.task1.ParsingException;
import static java.lang.Math.*;

import java.util.*;

/**
 * Created by VadimPl on 10.10.16.
 */
public class MyCalculator implements Calculator {

    private HashMap<String, Function> functions = new HashMap<>();

    public String PreProcess(String expression) throws  ParsingException {
        String newExpression = "";
        String name = "";
        int balance = 0;
        int tmpBalance = -1;
        boolean flag = false;
        boolean flagParam = false;
        boolean flagRoot = false;
        String tmpStr1 = "";
        String tmpStr2 = "";
        for (int i = 0; i < expression.length(); ++i) {

            if( expression.charAt(i) == '(' && flag) {
                flagRoot = true;
            }

            if (expression.charAt(i) == '(') {
                ++balance;
            }

            if (expression.charAt(i) == ')') {
                --balance;
            }
            if (expression.charAt(i) - 'a' >= 0 && expression.charAt(i) - 'z' <= 0 && !flagRoot) {
                flag = true;
                flagParam = false;
                name += expression.charAt(i);
                tmpBalance = balance;
            }

            if (flag && flagParam && !(expression.charAt(i) - 'a' >= 0 && expression.charAt(i) - 'z' <= 0)) {
                tmpStr2 += expression.charAt(i);
            }

            if (flag && expression.charAt(i) == ',') {
                if (flagParam == true) {
                    throw new ParsingException("Parsing exp");
                }
                flagParam = true;
            }

            if (flag && !flagParam && !(expression.charAt(i) - 'a' >= 0 && expression.charAt(i) - 'z' <= 0)) {
                tmpStr1 += expression.charAt(i);
            }

            if (!flag) {
                newExpression += expression.charAt(i);
            }

            if (tmpBalance == balance && !(expression.charAt(i) - 'a' >= 0 && expression.charAt(i) - 'z' <= 0)) {
                tmpBalance = -1;
                flag = false;
                double res1 = 0;
                double res2 = 0;
                if (name != "rnd" && !flagParam ) {
                    res1 = calculate(tmpStr1);
                }
                if (flagParam == true) {
                    tmpStr1 += ')';
                    tmpStr2 = '(' + tmpStr2;
                    res1 = calculate(tmpStr1);
                    res2 = calculate(tmpStr2);
                }
                tmpStr1 = "";
                tmpStr2 = "";
                String name1 = name;
                name = "";
                newExpression += Double.toString(functions.get(name1).Calculate(res1, res2));
            }
        }
        return newExpression;
    }

    public double calculate(String expression) throws ParsingException {
        functions.put("cos", new Cos());
        functions.put("sin", new Sin());
        functions.put("tg", new Tg());
        functions.put("abc", new Abs());
        functions.put("log", new Log());
        functions.put("log2", new Log2());
        functions.put("max", new Max());
        functions.put("min", new Min());
        functions.put("pow", new Pow());
        functions.put("Rnd", new Rnd());
        functions.put("Sign", new Sign());
        if (expression == null) {
            throw new ParsingException("Expression is null");
        }

        expression = PreProcess(expression);
        expression = "(" + expression.replaceAll("\\s", "") + ")";
        expression = makeUnaryMinus(expression);
        findUnaryPlus(expression);
        StringTokenizer tokenizer = new StringTokenizer(expression, "+-*/~()", true);
        Stack<Number> results = new Stack<>();
        Stack<Operations> operations = new Stack<>();

        while (tokenizer.hasMoreTokens()) {
            String t = tokenizer.nextToken();
            Operations lex = Operations.fromString(t);
            lex.addLexeme(results, operations);
        }
        if (!operations.isEmpty()) {
            throw new ParsingException("No parenthesis balance");
        }
        if (results.isEmpty()) {
            throw new ParsingException("No numbers in equations");
        }
        if (results.size() > 1) {
            throw new ParsingException("Not enough operators for these numbers");
        }
        return results.peek().getValue();
    }

    public String makeUnaryMinus(String expression) throws ParsingException {
        String result = "(";
        Set<Character> correct = new HashSet<>();
        for (char c : new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '+', '-', '*', '/', '(', ')', '.'}) {
            correct.add(c);
        }

        for (int i = 1; i < expression.length(); ++i) {
            char thissymbol = expression.charAt(i);
            char previoussymbol = expression.charAt(i - 1);

            if (!correct.contains(thissymbol)) {
                throw new ParsingException("Incorrect character");
            }

            if  (previoussymbol == '(' && thissymbol == ')') {
                throw new ParsingException("No param");
            }

            if (previoussymbol != ')' && thissymbol == '-' && !Character.isDigit(previoussymbol)) {
                thissymbol = '~';
            }

            result += thissymbol;
        }
        return result;
    }

    public void findUnaryPlus(String expression) throws ParsingException {
        for (int i = 1; i < expression.length(); ++i) {
            if (expression.charAt(i - 1) == '+' && expression.charAt(i) == '+') {
                throw new ParsingException("++");
            }
        }
    }
}
