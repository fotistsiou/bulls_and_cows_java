package step_3;

import java.util.Scanner;

/**
 * Secret code
 * -----------
 * Description
 * Using a predefined secret code doesn't make a fun game. Let's implement the ability to generate a pseudo-random
 * secret number of a given length. If the length is greater than 10, print a warning message and do not generate a
 * number.
 * We suggest you use the following algorithm to generate the numbers:
 * 1. long pseudoRandomNumber = System.nanoTime();
 * This code saves the nanoseconds elapsed since a certain time to the pseudoRandomNumber variable. We can assume that
 * this is a random number. You can generate a secret code by iterating over the pseudoRandomNumber in the reverse order
 * and adding unique digits. If the pseudoRandomNumber lacks the required number of unique digits, call System.nanoTime()
 * again and try to generate the secret code again until you get a satisfactory result.
 * You can use the Character.getNumericValue(char a) method to get an integer representation of a number!
 * -----------
 * Objective
 * In this stage, your program should generate a pseudo-random number of a given length with unique digits and print it.
 * If the length is greater than 10, the program should print a message containing the word Error. The secret code may
 * contain any digits from 0 to 9 but only once. The secret code shouldn't start with a digit 0: for the first digit of
 * the secret code, use digits from 1 to 9.
 * Don't delete your previous work, just move your code to a separate method. You will need it in the future stages.
 */

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        scanner.nextLine();
        Main.generateRandomCode(length);
        scanner.close();
    }

    static void generateRandomCode(int length) {
        if (length > 10) {
            System.out.printf(
                    "Error: can't generate a secret number with a length of %d because there aren't enough unique digits.",
                    length
            );
            return;
        }

        char[] secretCode = new char[length];
        int counter = 0;

        while (counter < length) {
            long pseudoRandomNumber = System.nanoTime();
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(pseudoRandomNumber)).reverse();
            for (char c : stringBuilder.toString().toCharArray()) {
                if (counter == 0) {
                    if (c == '0') {
                        continue;
                    }
                    secretCode[counter] = c;
                    counter++;
                }
                if (counter == secretCode.length) {
                    System.out.print("The random secret number is " + String.valueOf(secretCode));
                    return;
                }
                if (Main.isUnique(secretCode, c, counter)) {
                    secretCode[counter] = c;
                    counter++;
                }
            }
        }
    }

    static boolean isUnique(char[] secretCode, char c, int counter) {
        for (int i = 0; i < counter; i++) {
            if (secretCode[i] == c) {
                return false;
            }
        }
        return true;
    }
}
