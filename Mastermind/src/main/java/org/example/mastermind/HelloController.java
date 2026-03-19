package org.example.mastermind;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/**
 * CONTROLLER – steuert den Spielablauf und verbindet Model und View.
 */
public class HelloController {

    private final MastermindModel model;
    private final MastermindView  view;

    public HelloController(MastermindModel model, MastermindView view) {
        this.model = model;
        this.view  = view;
    }

    @FXML
    public void initialize() {
        view.setInfoText("Erlaubte Farben: " + model.getAllowedColorsAsString());
        view.setRemainingTries(model.getRemainingTries());
        view.setHistory("Noch keine Versuche.");
        view.setMessage("Bitte einen gültigen Code eingeben.");

        view.getSubmitButton().setOnAction(e -> handleGuess());
        view.getResetButton().setOnAction(e -> handleReset());
    }

    private void handleGuess() {
        String guess = view.getGuessInput();

        if (!model.isValidGuess(guess)) {
            view.setMessage("Ungültige Eingabe! Genau 4 Zeichen aus R, G, B, Y, O, P eingeben.");
            return;
        }

        String result = model.evaluateGuess(guess);
        updateHistory();
        view.setRemainingTries(model.getRemainingTries());
        view.clearGuessInput();
        view.setMessage("Auswertung: " + result);

        if (model.hasWon(result)) {
            view.disableGameInput(true);
            showAlert("Gewonnen! 🎉", "Glückwunsch! Sie haben den geheimen Code geknackt!");
            return;
        }

        if (model.isGameOver()) {
            view.disableGameInput(true);
            showAlert("Spiel beendet", "Keine Versuche mehr!\nDer geheime Code war: " + model.getSecretCodeAsString());
        }
    }

    private void handleReset() {
        model.resetGame();
        view.disableGameInput(false);
        view.clearGuessInput();
        view.setRemainingTries(model.getRemainingTries());
        view.setHistory("Noch keine Versuche.");
        view.setMessage("Neues Spiel gestartet.");
    }

    private void updateHistory() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < model.getGuessHistory().size(); i++) {
            sb.append(String.format("%2d. %-10s | %s%n",
                    i + 1,
                    model.getGuessHistory().get(i),
                    model.getResultHistory().get(i)));
        }
        view.setHistory(sb.toString());
    }

    private void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}