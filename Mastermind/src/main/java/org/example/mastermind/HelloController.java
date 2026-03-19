package org.example.mastermind;

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

    public void initialize() {
        view.setInfoText("Erlaubte Farben: R, G, B, Y, O, P");
        view.setRemainingTries(model.getRemainingTries());
        view.clearHistory();
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
        view.clearHistory();
        view.setRemainingTries(model.getRemainingTries());
        view.setMessage("Neues Spiel gestartet.");
    }

    private void updateHistory() {
        view.clearHistory();
        for (int i = 0; i < model.getGuessHistory().size(); i++) {
            view.addHistoryRow(
                    i + 1,
                    model.getGuessHistory().get(i),
                    model.getResultHistory().get(i)
            );
        }
    }

    private void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}