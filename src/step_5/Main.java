package step_5;

import java.util.Random;
import java.util.Scanner;

/**
 * Improve the code generator
 * --------------------------
 * Description
 * The algorithm suggested for generating the secret code in the previous stage was really a “reinvention of the wheel”.
 * Java already has the tools for generating random numbers! Research some common pseudo-random generation methods, such
 * as Math.random() and other methods from the Random class. Choose the method you like and use it to rewrite the secret
 * code generation. To learn more about the Random class, jump to Random topic. If you want to get to know all the
 * intricacies of the Math library, don't forget to check out the Math library topic as well.
 * Nothing else is supposed to change at this stage: the program asks for the length, generates a secret code, and then
 * receives and grades the attempts until the code is guessed. Your task here is to rewrite the code generator without
 * breaking the existing code.
 * --------------------------
 * Objective
 * In this stage, rewrite the secret code generator using a suitable Java method.
 */

public class Main {
    public static String secretCode;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Please, enter the secret code's length:");
        int length = scanner.nextInt();
        scanner.nextLine();
        if (length > 10) {
            System.out.printf(
                    "Error: can't generate a secret number with a length of %d because there aren't enough unique digits.",
                    length
            );
            scanner.close();
            return;
        }
        Main.secretCode = Main.generateRandomCode(length, random);

        System.out.println("Okay, let's start a game!");
        Main.playGame(Main.secretCode, scanner);

        scanner.close();
    }

    static String generateRandomCode(int length, Random random) {
        char[] secretCode = new char[length];
        int counter = 0;

        while (counter < length) {
            int randNum  = random.nextInt(10);
            if (counter == 0 && randNum == 0) {
                continue;
            }
            char rand = (char) (randNum + '0');
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
}
