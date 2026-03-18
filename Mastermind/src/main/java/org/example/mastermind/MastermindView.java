package org.example.mastermind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class MastermindView {
    @FXML
    private Label titleLabel;

    @FXML
    private Label infoLabel;

    @FXML
    private Label triesLabel;

    @FXML
    private TextArea historyArea;

    @FXML
    private TextField guessField;

    @FXML
    private Button submitButton;

    @FXML
    private Button resetButton;

    @FXML
    private Label messageLabel;

    private Parent root;

    public MastermindView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mastermind.fxml"));
        loader.setController(this);

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("FXML konnte nicht geladen werden.", e);
        }
    }

    @FXML
    private void initialize() {
        titleLabel.setText("Mastermind");
        infoLabel.setText("Erlaubte Farben: R, G, B, Y, O, P");
        triesLabel.setText("Verbleibende Versuche: 10");
        historyArea.setEditable(false);
        guessField.setPromptText("z.B. RGBY oder R G B Y");
        messageLabel.setText("Bitte einen Code eingeben.");
    }

    public Parent getRoot() {
        return root;
    }

    public String getGuessInput() {
        return guessField.getText();
    }

    public void clearGuessInput() {
        guessField.clear();
    }

    public void setRemainingTries(int tries) {
        triesLabel.setText("Verbleibende Versuche: " + tries);
    }

    public void setHistory(String historyText) {
        historyArea.setText(historyText);
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void setInfoText(String text) {
        infoLabel.setText(text);
    }

    public void disableGameInput(boolean disable) {
        guessField.setDisable(disable);
        submitButton.setDisable(disable);
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public Button getResetButton() {
        return resetButton;
    }
}