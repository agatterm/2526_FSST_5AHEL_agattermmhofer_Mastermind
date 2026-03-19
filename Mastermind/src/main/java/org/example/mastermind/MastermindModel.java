package org.example.mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MastermindModel {
    private static final char[] ALLOWED_COLORS = {'R', 'G', 'B', 'Y', 'O', 'P'};
    private static final int CODE_LENGTH = 4;
    private static final int MAX_TRIES = 10;

    private final char[] secretCode = new char[CODE_LENGTH];
    private int remainingTries;
    private final List<String> guessHistory;
    private final List<String> resultHistory;

    public MastermindModel() {
        this.guessHistory = new ArrayList<>();
        this.resultHistory = new ArrayList<>();
        resetGame();
    }

    public void resetGame() {
        generateSecretCode();
        remainingTries = MAX_TRIES;
        guessHistory.clear();
        resultHistory.clear();
    }

    private void generateSecretCode() {
        Random random = new Random();

        for (int i = 0; i < CODE_LENGTH; i++) {
            secretCode[i] = ALLOWED_COLORS[random.nextInt(ALLOWED_COLORS.length)];
        }
    }

    public boolean isValidGuess(String guess) {
        if (guess == null) {
            return false;
        }

        String cleanedGuess = normalizeGuess(guess);

        if (cleanedGuess.length() != CODE_LENGTH) {
            return false;
        }

        for (int i = 0; i < cleanedGuess.length(); i++) {
            if (!isAllowedColor(cleanedGuess.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private boolean isAllowedColor(char c) {
        for (char allowed : ALLOWED_COLORS) {
            if (allowed == c) {
                return true;
            }
        }
        return false;
    }

    public String evaluateGuess(String guess) {
        String cleanedGuess = normalizeGuess(guess);
        char[] guessChars = cleanedGuess.toCharArray();

        boolean[] secretUsed = new boolean[CODE_LENGTH];
        boolean[] guessUsed = new boolean[CODE_LENGTH];

        int correctPosition = 0;
        int correctColorWrongPosition = 0;

        // 1. Richtige Farbe an richtiger Position
        for (int i = 0; i < CODE_LENGTH; i++) {
            if (guessChars[i] == secretCode[i]) {
                correctPosition++;
                secretUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        // 2. Richtige Farbe an falscher Position
        for (int i = 0; i < CODE_LENGTH; i++) {
            if (guessUsed[i]) {
                continue;
            }

            for (int j = 0; j < CODE_LENGTH; j++) {
                if (!secretUsed[j] && guessChars[i] == secretCode[j]) {
                    correctColorWrongPosition++;
                    secretUsed[j] = true;
                    guessUsed[i] = true;
                    break;
                }
            }
        }

        String result = buildResultString(correctPosition, correctColorWrongPosition);

        guessHistory.add(formatGuess(cleanedGuess));
        resultHistory.add(result);
        remainingTries--;

        return result;
    }

    private String buildResultString(int correctPosition, int correctColorWrongPosition) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < correctPosition; i++) {
            sb.append("●");
        }

        for (int i = 0; i < correctColorWrongPosition; i++) {
            sb.append("○");
        }

        if (sb.length() == 0) {
            sb.append("-");
        }

        return sb.toString();
    }

    private String formatGuess(String guess) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < guess.length(); i++) {
            sb.append(guess.charAt(i));
            if (i < guess.length() - 1) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    private String normalizeGuess(String guess) {
        return guess.replaceAll("\\s+", "").toUpperCase();
    }

    public boolean hasWon(String result) {
        return "●●●●".equals(result);
    }

    public boolean isGameOver() {
        return remainingTries <= 0;
    }

    public int getRemainingTries() {
        return remainingTries;
    }

    public List<String> getGuessHistory() {
        return guessHistory;
    }

    public List<String> getResultHistory() {
        return resultHistory;
    }

    public String getSecretCodeAsString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < secretCode.length; i++) {
            sb.append(secretCode[i]);
            if (i < secretCode.length - 1) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    public String getAllowedColorsAsString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < ALLOWED_COLORS.length; i++) {
            sb.append(ALLOWED_COLORS[i]);
            if (i < ALLOWED_COLORS.length - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // Konsolenmodus
    // -------------------------------------------------------------------------

    public void playInConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== MASTERMIND ===");
        System.out.println("Erlaubte Farben: " + getAllowedColorsAsString());
        System.out.println("Errate den " + CODE_LENGTH + "-stelligen Code. Du hast " + MAX_TRIES + " Versuche.");
        System.out.println("● = richtige Farbe, richtige Position");
        System.out.println("○ = richtige Farbe, falsche Position");
        System.out.println("------------------");

        while (true) {
            System.out.printf("%nVersuch %d/%d – Dein Tipp: ", (MAX_TRIES - remainingTries + 1), MAX_TRIES);
            String input = scanner.nextLine().trim();

            if (!isValidGuess(input)) {
                System.out.println("Ungültige Eingabe! Bitte genau " + CODE_LENGTH
                        + " Farben aus [" + getAllowedColorsAsString() + "] eingeben.");
                continue;
            }

            String result = evaluateGuess(input);
            System.out.println("Ergebnis: " + result);

            if (hasWon(result)) {
                System.out.println("\n🎉 Gewonnen! Du hast den Code in "
                        + (MAX_TRIES - remainingTries) + " Versuch(en) geknackt.");
                break;
            }

            if (isGameOver()) {
                System.out.println("\n❌ Verloren! Der geheime Code war: " + getSecretCodeAsString());
                break;
            }

            System.out.println("Noch " + remainingTries + " Versuch(e) übrig.");
            printHistory();
        }

        System.out.print("%nNochmal spielen? (j/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("j")) {
            resetGame();
            playInConsole();
        } else {
            System.out.println("Tschüss!");
        }
    }

    private void printHistory() {
        System.out.println("-- Verlauf --");
        for (int i = 0; i < guessHistory.size(); i++) {
            System.out.printf("  %2d. %-10s %s%n", i + 1, guessHistory.get(i), resultHistory.get(i));
        }
    }

    public static void main(String[] args) {
        new MastermindModel().playInConsole();
    }
}