package step_6;

import java.util.Random;
import java.util.Scanner;

/**
 * New level
 * ---------
 * Description
 * Some players need a challenge, so let's make the secret code in the game harder to guess. You should add support for
 * more than 10 symbols by including letters too. Now, the secret code can contain the 10 numbers 0-9 and the 26
 * lowercase Latin characters a-z. So, the new maximum length for the secret code is 36.
 * You should also implement a system where users can select the number of possible symbols that can be used to create
 * the secret code. So you should request input twice: once for the secret code length and once for the number of
 * possible symbols in it. Note that the length of the secret code may not match the number of possible symbols in it.
 * Therefore, it is important to verify that the secret code length is not greater than the number of possible symbols
 * as the integers in secret code have to be unique.
 * Also, since the secret code is not a number anymore, allow the symbol 0 as the first character in a secret code.
 * ---------
 * Objectives
 * In this step, your program should:
 * 1. Ask for the length of the secret code.
 * 2. Ask for the range of possible symbols in the secret code.
 * 3. Generate a secret code using numbers and characters. This time, you should also print the secret code using *
 *    characters and print which symbols were used to generate the secret code.
 * 4. Function as a fully playable game.
 */

public class Main {
    public static String secretCode;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Input the length of the secret code:");
        int lengthSecretCode = scanner.nextInt();
        System.out.println("Input the number of possible symbols in the code:");
        int lengthAvailableSymbols = scanner.nextInt();
        scanner.nextLine();

        if (lengthSecretCode > 36 || lengthAvailableSymbols > 36) {
            System.out.printf(
                    "Error: can't generate a secret number with a length of %d because there aren't enough unique symbols.",
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
