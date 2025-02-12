package step_4;

/**
 * Game time!
 * ----------
 * Description
 * In this stage, you should combine all the previous parts into a simple playable version of the "Bulls and Cows" game.
 * First, prompt the player to input the length of the secret code. The length will determine the difficulty level for
 * the current game session. The program should generate a secret code of the given length. Remember that it should
 * consist of unique numbers.
 * Then, the game starts and the program prompts the player to guess the code. When the player inputs a number, the
 * program should grade it in bulls and cows. The game goes on until the code is guessed, that is, until the number of
 * bulls is equal to the number of digits in the code. When the game ends, the program should finish its execution.
 * ----------
 * Objectives
 * In this stage, your program should:
 * 1. Ask for the length of the secret code and then generate the code.
 * 2. Wait for the user input.
 * 3. Grade the guessing attempt in bulls and cows.
 * 4. If the secret code has been guessed, the program stops; otherwise, return to the second step.
 */

import java.util.Scanner;

public class Main {
    public static String secretCode;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
        Main.secretCode = Main.generateRandomCode(length);

        System.out.println("Okay, let's start a game!");
        Main.playGame(Main.secretCode, scanner);

        scanner.close();
    }

    static String generateRandomCode(int length) {
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
                    break;
                }
                if (Main.isUnique(secretCode, c, counter)) {
                    secretCode[counter] = c;
                    counter++;
                }
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

