package step_2;

import java.util.Scanner;

/**
 * Grader
 * ------
 * Description
 * Let's add some interactivity to our program. The program should create a 4-digit secret code, and the player should
 * try to guess it on the first try. The program should give a grade to evaluate how accurate the player was.
 * As you remember, a correctly guessed digit is a cow, and if its position is also correct, then it is a bull. After
 * the player tries to guess the secret code, the program should grade the attempt and finish the execution.
 * For example, if the secret code is 9305, then:
 * 1. The number 9305 contains 4 bulls and 0 cows since all 4 digits are correct and their positions are correct as well.
 *    It's the only number that can contain 4 bulls, so it's also the secret code itself.
 * 2. The numbers 3059, 3590, 5930, 5039 contain 0 bulls and 4 cows since all 4 digits are correct but their positions
 *    don't match the positions in the secret code.
 * 3. The numbers 9306, 9385, 9805, 1305 contain 3 bulls.
 * 4. The numbers 9350, 9035, 5309, 3905 contain 2 bulls and 2 cows.
 * 5. The numbers 1293, 5012, 3512, 5129 contain 0 bulls and 2 cows.
 * 6. The numbers 1246, 7184, 4862, 2718 contain 0 bulls and 0 cows.
 * Note that guesses can contain repetitive digits, so:
 * 1. The number 1111 contains 0 bulls and 0 cows.
 * 2. The number 9999 contains 1 bull and 3 cows.
 * 3. The number 9955 contains 2 bulls and 2 cows.
 * ------
 * Objectives
 * In this stage, your goal is to write the core part of the game: the grader.
 * 1. Your program should take a 4-digit number as an input.
 * 2. Use a predefined 4-digit code and grade the answer that was input. You can do it digit by digit.
 * The grade is considered correct if it contains number-and-word pairs (like X bulls and Y cows) that give the correct
 * information. If the answer doesn't contain any bulls and cows, you should output None.
 */

public class Main {
    static char[] secretCode = {'9', '3', '0', '5'};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String guess = scanner.nextLine();
        char[] guessToCharArray = guess.toCharArray();
        int cows = 0;
        int bulls = 0;
        for (int i = 0; i < guessToCharArray.length; i++) {
            for (int j = 0; j < secretCode.length; j++) {
                if (guessToCharArray[i] == secretCode[j] && i == j) {
                    bulls++;
                    continue;
                }
                if (guessToCharArray[i] == secretCode[j]) {
                    cows++;
                }
            }
        }
        if (bulls == 0 && cows == 0) {
            System.out.println("Grade: None. The secret code is 9305.");
        } else if (bulls == 0) {
            System.out.println("Grade: " + cows + " cow(s). The secret code is 9305.");
        } else if (cows == 0) {
            System.out.println("Grade: " + bulls + " bull(s). The secret code is 9305.");
        } else {
            System.out.println("Grade: " + bulls + " bull(s) and " + cows + " cow(s). The secret code is 9305.");
        }
        scanner.close();
    }
}
