// Bilaal Ahmad
// Equivalent Expression

package com.company;

import java.io.*;
import java.util.*;

public class EquivalentExpression {
    static Random rand = new Random();
    static int setSize = 0; /* size of set (I used a while scanner.hasNext() loop) */
    static String target = ""; /* target expression e */
    static List<String> expressions = new ArrayList<>(); /* list containing expressions */
    static Map<String,Double> map = new HashMap<>(); /* maps variable names to random integers */

    /**
     * This main method interprets the input file and writes to the output file.
     * @param args disregard this
     * @throws IOException if there are issues with reading/writing to files (probably a path issue)
     */
    public static void main(String[] args) throws IOException {
        // read file and get important information, put in variables above
        File file = new File ("/Users/bilaalahmad/IdeaProjects/EquivalentExpression/src/com/company/input.txt");
        Scanner sc = new Scanner(file);

        setSize = Integer.parseInt(sc.nextLine());
        target = sc.nextLine();
        while (sc.hasNextLine()) {
            expressions.add(sc.nextLine());
        }
        sc.close();

        // compare output of each expression to original and write to file
        FileWriter fileWriter = new FileWriter("output.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        double targetVal = Math.round(evaluator(target, false));
        for (String exp : expressions) {
            double val = Math.round(evaluator(exp, false));
            // write to file and print to console
            if (val == targetVal) {
                printWriter.println("yes");
                System.out.println("yes");
            } else {
                printWriter.println("no");
                System.out.println("no");
            }
        }
        printWriter.close();
    }

    /**
     *  This is the main evaluation method.
     * @param input string representing the expression
     * @param working this is true if this method was called with recursion
     * @return double representing result of input
     */
    public static double evaluator(String input, boolean working) {
        // deals with parenthesis with simple recursion
        if (input.contains("(") && !working) {
            int open = input.indexOf("(");
            int closed = input.indexOf(")");
            String inside = input.substring(open + 1, closed).trim();
            String after = Double.toString(evaluator(inside, true));
            input = input.replace(input.substring(open, closed + 1), after);
            return evaluator(input, false);
        }

        // split the input into array of strings and list
        String[] parts = input.split(" ");
        List<String> partsList = new ArrayList<>(Arrays.asList(parts));

        // deals with division and multiplication (aka higher priority)
        for (int i = 0; i < partsList.size(); i++) {
            if (partsList.get(i).equals("/") || partsList.get(i).equals("*")) {
                // get variable before and after current operator, assign each a random value and put in map
                String var1 = partsList.get(i - 1);
                String var2 = partsList.get(i + 1);

                if (!map.containsKey(var1)) {
                    map.put(var1, (double) rand.nextInt(100) + 1);
                }
                if (!map.containsKey(var2)) {
                    map.put(var2, (double) rand.nextInt(100) + 1);
                }

                double result = 0;
                if (partsList.get(i).equals("/")) {
                    result = division(map.get(var1), map.get(var2));
                } else if (partsList.get(i).equals("*")) {
                    result = multiplication(map.get(var1), map.get(var2));
                }

                String resultStr = Double.toString(result);
                map.put(resultStr, result);

                // replaces an expression with its output
                partsList.remove(i - 1);
                partsList.remove(i - 1);
                partsList.set(i - 1, resultStr);
                i--;
            }

        }

        // deals with addition and subtraction - almost identical to above
        for (int i = 0; i < partsList.size(); i++) {
            if (partsList.get(i).equals("+") || partsList.get(i).equals("-")) {

                String var1 = partsList.get(i - 1);
                String var2 = partsList.get(i + 1);

                if (!map.containsKey(var1)) {
                    map.put(var1, (double) rand.nextInt(100) + 1);
                }
                if (!map.containsKey(var2)) {
                    map.put(var2, (double) rand.nextInt(100) + 1);
                }

                double result = 0;
                if (partsList.get(i).equals("+")) {
                    result = addition(map.get(var1), map.get(var2));
                } else if (partsList.get(i).equals("-")) {
                    result = subtraction(map.get(var1), map.get(var2));
                }

                String resultStr = Double.toString(result);
                map.put(resultStr, result);

                partsList.remove(i - 1);
                partsList.remove(i - 1);
                partsList.set(i - 1, resultStr);
                i--;
            }
        }

        // return the only value in the arraylist after all operations are done
        try {
            return Double.parseDouble(partsList.get(0));
        } catch (NumberFormatException e) {
            // this will result in "no" (the input was only one variable)
            return 129312;
        }
    }

    // method for addition
    public static double addition(double first, double second) {
        return first + second;
    }

    // method for subtraction
    public static double subtraction(double first, double second) {
        return first - second;
    }

    // method for multiplication
    public static double multiplication (double first, double second) {
        return first * second;
    }

    // method for division
    public static double division (double first, double second) {
        return first / second;
    }
}
