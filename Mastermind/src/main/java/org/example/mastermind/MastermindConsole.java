package org.example.mastermind;

import java.util.Scanner;

public class MastermindConsole {

    public static void main(String[] args) {
        new MastermindConsole().run();
    }

    public void run() {
        MastermindModel model = new MastermindModel();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== MASTERMIND ===");
        System.out.println("Erlaubte Farben: " + model.getAllowedColorsAsString());
        System.out.println("Errate den 4-stelligen Code. Du hast 10 Versuche.");
        System.out.println("● = richtige Farbe, richtige Position");
        System.out.println("○ = richtige Farbe, falsche Position");
        System.out.println("------------------");

        playGame(model, scanner);

        System.out.print("\nNochmal spielen? (j/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("j")) {
            model.resetGame();
            playGame(model, scanner);
        } else {
            System.out.println("Tschüss!");
        }
    }

    private void playGame(MastermindModel model, Scanner scanner) {
        int maxTries = model.getRemainingTries();

        while (true) {
            System.out.printf("%nVersuch %d/%d – Dein Tipp: ",
                    (maxTries - model.getRemainingTries() + 1), maxTries);
            String input = scanner.nextLine().trim();

            if (!model.isValidGuess(input)) {
                System.out.println("Ungültige Eingabe! Bitte genau 4 Farben aus ["
                        + model.getAllowedColorsAsString() + "] eingeben.");
                continue;
            }

            String result = model.evaluateGuess(input);
            System.out.println("Ergebnis: " + result);

            if (model.hasWon(result)) {
                System.out.println("\n🎉 Gewonnen! Du hast den Code in "
                        + (maxTries - model.getRemainingTries()) + " Versuch(en) geknackt.");
                break;
            }

            if (model.isGameOver()) {
                System.out.println("\n❌ Verloren! Der geheime Code war: " + model.getSecretCodeAsString());
                break;
            }

            System.out.println("Noch " + model.getRemainingTries() + " Versuch(e) übrig.");
            printHistory(model);
        }
    }

    private void printHistory(MastermindModel model) {
        System.out.println("-- Verlauf --");
        var guesses = model.getGuessHistory();
        var results = model.getResultHistory();
        for (int i = 0; i < guesses.size(); i++) {
            System.out.printf("  %2d. %-10s %s%n", i + 1, guesses.get(i), results.get(i));
        }
    }
}