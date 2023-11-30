package bullscows;

import java.util.Scanner;

public class Main {
    final static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        //SECRET CODE SIZE
        int size = secretCodeSize();
        if (size != 0) {
            //SYMBOLS SIZE
            int symbols = symbolsSize();
            if (symbols != -1) {
                if (symbols < size) {
                    System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", size, symbols);
                } else if (symbols > 36) {
                    System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                } else {
                    String secretCode = randomCode(size, symbols);
                    String password = generateHiddenSecretCode(size);
                    String characters = characterUsingInSecretCode(symbols);

                    System.out.printf("The secret is prepared: %s %s.%n", password, characters);
                    System.out.println("Okay, let's start a game!");
                    playGame(secretCode);
                }
            }
        } else {
            System.out.println("Error: the minimum number possible is 1");
        }
    }

    public static int secretCodeSize() {
        System.out.println("Input the length of the secret code:");
        String value = scanner.nextLine();
        if (!isValidInteger(value)) {
            System.out.printf("Error: \"%s\" isn't a valid number\n", value);
            return 0;
        } else {
            return Integer.parseInt(value);
        }
    }

    public static int symbolsSize() {
        System.out.println("Input the number of possible symbols in the code:");
        String value = scanner.nextLine();
        if (!isValidInteger(value)) {
            System.out.printf("Error: \"%s\" isn't a valid number", value);
            return 0;
        } else {
            return Integer.parseInt(value);
        }
    }

    /*
    //ONLY FOR STAGE 5/7
    public static String randomCode(int size) {
        String secretCode = "";
        //long pseudoRandomNumber = System.nanoTime();
        double random = Math.random();
        int pseudoRandomNumber = (int) (random * Math.pow(10, size * 2));
        System.out.println(pseudoRandomNumber);
        while (secretCode.length() != size) {
            String remaining = String.valueOf(pseudoRandomNumber % 10);
            if (secretCode.isEmpty() && !remaining.equals("0")) {
                secretCode += remaining;
            } else if (!secretCode.isEmpty()) {
                int index = secretCode.indexOf(remaining);
                if (index == -1) {
                    secretCode += remaining;
                }
            }
            pseudoRandomNumber /= 10;
        }
        return secretCode;
    }
     */

    public static String randomCode(int size, int symbols) {
        String secretCode = "";
        while (secretCode.length() != size) {
            String character = "";

            if (symbols > 10) {
                int option = (int) (Math.round(Math.random()));
                switch (option) {
                    case 0 -> character = String.valueOf(randomInt());
                    case 1 -> character = String.valueOf(randomCharacter(symbols - 10));
                }
            } else {
                character = String.valueOf(randomInt());
            }

            int index = secretCode.indexOf(character);
            if (index == -1) {
                secretCode += character;
            }
        }
        return secretCode;
    }

    public static int randomInt() {
        return (int) (9 * Math.random());
    }

    public static char randomCharacter(int length) {
        int min = 'a';
        int max = min + length;
        return (char) ((int)((max - min) * Math.random()) + min);
    }

    public static String generateHiddenSecretCode(int size) {
        String password = "";
        for (int i = 0; i < size; i++) {
            password += "*";
        }
        return password;
    }

    public static String characterUsingInSecretCode(int length) {
        String text = "(0-";
        text += (length < 10) ? (length - 1) : "9";
        if (length == 10) {
            text += ")";
        } else {
            text += ", ";

            int remaining = length - 10;
            int min = 97;
            int max = min + remaining - 1;

            text += (char) min + "-" + (char) max + ")";
        }
        return text;
    }

    public static void playGame(String secretCode) {
        int i = 1;
        String guess = "";
        while (!guess.equals(secretCode)) {
            System.out.printf("Turn %d. Answer:%n", i);
            guess = scanner.nextLine();
            if (!guess.isEmpty()) {
                char[] grade = grade(secretCode, guess);
                int[] state = countRepeatSymbol(grade);
                showState(state);
                i++;
            }
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    public static char[] grade(String secretCode, String guessCode) {
        char[] array = new char[secretCode.length()];
        for (int i = 0; i < guessCode.length(); i++) {
            char current = guessCode.charAt(i);
            int index = secretCode.indexOf(current);
            if (i == index) {
                array[i] = 'B';
            } else if (index != -1) {
                array[i] = 'C';
            }
        }
        return array;
    }

    public static int[] countRepeatSymbol(char[] grade) {
        int[] array = new int[]{ 0, 0 };
        for (char symbol : grade) {
            if (symbol == 'B') array[0]++;
            else if (symbol == 'C') array[1]++;
        }
        return array;
    }

    public static void showState(int[] state) {
        int bulls = state[0];
        int cows = state[1];

        String message = "";
        if (bulls != 0) {
            message += String.format("%d ", bulls);
            message += (bulls == 1) ? "bull" : "bulls";
        }
        if (bulls != 0 && cows != 0) {
            message += " and ";
        }
        if (cows != 0) {
            message += String.format("%d ", cows);
            message += (cows == 1) ? "cow" : "cows";
        }
        if (!message.isEmpty()) {
            message += ".";
            System.out.println("Grade: " + message);
        } else {
            System.out.println("Grade: None.");
        }
    }

    public static boolean isValidInteger(String str) {
        for (int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);
            if (!Character.isDigit(current)) return false;
        }
        return true;
    }
}
