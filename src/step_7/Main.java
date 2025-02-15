package step_7;

import java.util.Random;
import java.util.Scanner;

/**
 * Error!
 * ------
 * Description
 * There are many possibilities for errors. What if someone enters an answer of the wrong length? Or if the number of
 * possible symbols is less than the length of the code? What if the answer contains invalid symbols? The game may crash
 * before the secret number is guessed!
 * Let's handle errors like this. At this point, you can implement this without the try-catch construction. Use the
 * following rule of thumb: if you can avoid exception-based logic, do so! If you use exceptions in normal situations,
 * how would you deal with unusual (exceptional) situations? Now it may not seem that important, but when you need to
 * find errors in more complex programs, this makes a difference.
 * ------
 * Objectives
 * In this stage, your program should:
 * 1. Handle incorrect input.
 * 2. Print an error message that contains the word error. After that, don't ask for the numbers again, just finish the
 *    program.
 */

public class Main {
    public static String secretCode;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Input the length of the secret code:");
        int lengthSecretCode;
        if (scanner.hasNextInt()) {
            lengthSecretCode = scanner.nextInt();
            if (lengthSecretCode > 36 || lengthSecretCode <= 0) {
                System.out.println("Error. Invalid input. Enter a number between 1 and 36.");
                scanner.close();
                return;
            }
        } else {
            System.out.println("Error. Invalid input. Enter only numbers.");
            scanner.close();
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        int lengthAvailableSymbols = scanner.nextInt();
        scanner.nextLine();
        if (lengthAvailableSymbols < lengthSecretCode || lengthAvailableSymbols > 36) {
            System.out.printf(
                    "Error. Invalid input. Enter a number between %d and 36.",
                    lengthSecretCode
            );
            scanner.close();
            return;
        }

        char[] availableSymbols = Main.createAvailableSymbols(lengthAvailableSymbols);

        Main.secretCode = Main.generateRandomCode(lengthSecretCode, availableSymbols, random);

        Main.printSecretCodeDescription(lengthSecretCode, availableSymbols);

        System.out.println("Okay, let's start a game!");
        Main.playGame(Main.secretCode, scanner);

        scanner.close();
    }

    static String generateRandomCode(int lengthSecretCode, char[] availableSymbols, Random random) {
        char[] secretCode = new char[lengthSecretCode];
        int counter = 0;

        while (counter < lengthSecretCode) {
            char rand  = availableSymbols[random.nextInt(availableSymbols.length)];
            if (Main.isUnique(secretCode, rand, counter)) {
                secretCode[counter] = rand;
                counter++;
            }
        }

        return String.valueOf(secretCode);
    }

    static boolean isUnique(char[] secretCode, char c, int counter) {
        for (int i = 0; i < counter; i++) {
            if (secretCode[i] == c) {
                return false;
            }
        }
        return true;
    }

    static void playGame(String secretCode, Scanner scanner) {
        char[] secretCodeArray = secretCode.toCharArray();
        int bulls = 0;
        int round = 1;
        while (bulls < secretCodeArray.length) {
            System.out.printf("Turn %d:%n", round);
            String guess = scanner.nextLine();
            char[] guessToCharArray = guess.toCharArray();
            int cows = 0;
            bulls = 0;
            for (int i = 0; i < guessToCharArray.length; i++) {
                for (int j = 0; j < secretCodeArray.length; j++) {
                    if (guessToCharArray[i] == secretCodeArray[j] && i == j) {
                        bulls++;
                        continue;
                    }
                    if (guessToCharArray[i] == secretCodeArray[j]) {
                        cows++;
                    }
                }
            }
            if (bulls == 0 && cows == 0) {
                System.out.println("Grade: None.");
            } else if (bulls == 0) {
                String cowsText = cows == 1 ? "cow" : "cows";
                System.out.println("Grade: " + cows + " " + cowsText);
            } else if (cows == 0) {
                String bullsText = bulls == 1 ? "bull" : "bulls";
                System.out.println("Grade: " + bulls + " " + bullsText);
            } else {
                String cowsText = cows == 1 ? "cow" : "cows";
                String bullsText = bulls == 1 ? "bull" : "bulls";
                System.out.println("Grade: " + bulls + " " + bullsText + " " + cows + " " + cowsText);
            }
            round++;
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    static char[] createAvailableSymbols(int length) {
        char[] availableSymbols = new char[length];
        if (length > 10) {
            for (int i = 0; i < 10; i++) {
                availableSymbols[i] = (char) ('0' + i);
            }
            for (int i = 0; i < length - 10; i++) {
                availableSymbols[10 + i] = (char) ('a' + i);
            }
        } else {
            for (int i = 0; i < length; i++) {
                availableSymbols[i] = (char) ('0' + i);
            }
        }
        return availableSymbols;
    }

    static void printSecretCodeDescription(int lengthSecretCode, char[] availableSymbols) {
        System.out.print("The secret is prepared: ");
        System.out.print(String.valueOf('*').repeat(lengthSecretCode) + " ");
        if (availableSymbols.length > 10) {
            System.out.printf("(%s-%s, %s-%s)%n",
                    availableSymbols[0],
                    availableSymbols[9],
                    availableSymbols[10],
                    availableSymbols[availableSymbols.length - 1]
            );
        } else if (availableSymbols.length == 10) {
            System.out.printf("(%s-%s)%n",
                    availableSymbols[0],
                    availableSymbols[9]
            );
        }

    }
}
