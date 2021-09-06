package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here
        if ((statement == null) || (statement.equals(""))) return null;
        Stack<Double> numsStack = new Stack<>();
        Stack<String> symbolsStack = new Stack<>();
        Map<String, Integer> symbolsMap = new HashMap<>();
        // Filling the symbolsMap default symbols with its priority values
        symbolsMap.put("(", 0);
        symbolsMap.put(")", 1);
        symbolsMap.put("+", 1);
        symbolsMap.put("-", 1);
        symbolsMap.put("*", 2);
        symbolsMap.put("/", 2);

        /* Regular expression:
        It can match pair: double number and maths symbol
        or "" and symbol or double number and ""
         */
        Pattern pattern = Pattern.compile("((\\d+[.]?\\d*)?)(([-+*/()])?)");
        Matcher matcher = pattern.matcher(statement);
        String number = "";
        String symbol = "";
        matcher.find();
        while (true){
            number = matcher.group(1);
            symbol = matcher.group(3);
            if ((number.equals("")) && (symbol.equals("")) && (!matcher.hitEnd())) return null;
            if (!number.equals("")) {
                numsStack.push(Double.parseDouble(number));
            } else if ((symbol.equals("-")) && ((symbolsStack.isEmpty()) || symbolsStack.lastElement().equals("("))){
                numsStack.push(0.0);
            }
            if (!symbol.equals("")){
                if (symbol.equals("(")){
                    symbolsStack.push(symbol);
                }
                else if (symbolsStack.isEmpty()){
                    symbolsStack.push(symbol);
                }
                else if (symbolsMap.get(symbol) > symbolsMap.get(symbolsStack.lastElement())){
                    symbolsStack.push(symbol);
                }
                else if (symbol.equals(")")){
                    while ((!symbolsStack.isEmpty()) && (!symbolsStack.lastElement().equals("("))){
                        Double result = calculate(numsStack.pop(), numsStack.pop(), symbolsStack.pop());
                        if (result != null) numsStack.push(result);
                        else return null;
                    }
                    if (symbolsStack.lastElement().equals("(")) symbolsStack.pop();
                }
                else if (symbolsMap.get(symbol) <= symbolsMap.get(symbolsStack.lastElement())){
                    while ((!symbolsStack.isEmpty()) && (!symbolsStack.lastElement().equals("(")) && (numsStack.size() >= 2)) {
                        Double result = calculate(numsStack.pop(), numsStack.pop(), symbolsStack.pop());
                        if (result != null) numsStack.push(result);
                        else return null;
                    }
                    symbolsStack.push(symbol);
                }
                else return null;
            }
            if (!matcher.find()) break;
        }
        while ((numsStack.size() > 1) && (!symbolsStack.isEmpty())){
            Double result = calculate(numsStack.pop(), numsStack.pop(), symbolsStack.pop());
            if (result != null){
                numsStack.push(result);
            }
            else return null;
        }
        if (!symbolsStack.isEmpty()) return null;
        DecimalFormat df = new DecimalFormat("##.####");
        String result = df.format(numsStack.pop()).replace(",", ".");
        if (result.endsWith(".0000")) return result.replace(".0000", "");
        return result;
    }

    private Double calculate(Double second, Double first, String operator){
        switch (operator) {
            case "+": return first + second;
            case "-": return first - second;
            case "*": return first * second;
            case "/": if (second.compareTo(Double.valueOf(0)) == 0) return null;
                      return first / second;
            default: return null;
        }
    }
}