package org.example.mastermind;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * VIEW – verantwortlich für Darstellung und Benutzereingaben.
 * Die FXML-Felder werden von FXMLLoader injiziert.
 */
public class MastermindView {

    @FXML private Label    titleLabel;
    @FXML private Label    infoLabel;
    @FXML private Label    triesLabel;
    @FXML private TextArea historyArea;
    @FXML private TextField guessField;
    @FXML private Button   submitButton;
    @FXML private Button   resetButton;
    @FXML private Label    messageLabel;

    @FXML
    public void initialize() {
        titleLabel.setText("🎯 Mastermind");
        infoLabel.setText("Erlaubte Farben: R, G, B, Y, O, P");
        triesLabel.setText("Verbleibende Versuche: 10");
        historyArea.setEditable(false);
        historyArea.setStyle("-fx-font-family: monospace; -fx-font-size: 13px;");
        guessField.setPromptText("z.B. RGBY oder R G B Y");
        messageLabel.setText("Bitte einen Code eingeben.");
    }

    public String getGuessInput()              { return guessField.getText(); }
    public void   clearGuessInput()            { guessField.clear(); }
    public void   setRemainingTries(int tries) { triesLabel.setText("Verbleibende Versuche: " + tries); }
    public void   setHistory(String text)      { historyArea.setText(text); }
    public void   setMessage(String message)   { messageLabel.setText(message); }
    public void   setInfoText(String text)     { infoLabel.setText(text); }
    public Button getSubmitButton()            { return submitButton; }
    public Button getResetButton()             { return resetButton; }

    public void disableGameInput(boolean disable) {
        guessField.setDisable(disable);
        submitButton.setDisable(disable);
    }
}