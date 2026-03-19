package org.example.mastermind;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * VIEW – verantwortlich für Darstellung und Benutzereingaben.
 */
public class MastermindView {

    @FXML private Label     titleLabel;
    @FXML private Label     infoLabel;
    @FXML private Label     triesLabel;
    @FXML private VBox      historyBox;
    @FXML private TextField guessField;
    @FXML private Button    submitButton;
    @FXML private Button    resetButton;
    @FXML private Label     messageLabel;

    @FXML
    public void initialize() {
        titleLabel.setText("🎯 Mastermind");
        infoLabel.setText("Erlaubte Farben: R, G, B, Y, O, P");
        triesLabel.setText("Verbleibende Versuche: 10");
        guessField.setPromptText("z.B. RGBY oder R G B Y");
        messageLabel.setText("Bitte einen Code eingeben.");
    }

    public String getGuessInput()              { return guessField.getText(); }
    public void   clearGuessInput()            { guessField.clear(); }
    public void   setRemainingTries(int tries) { triesLabel.setText("Verbleibende Versuche: " + tries); }
    public void   setMessage(String message)   { messageLabel.setText(message); }
    public void   setInfoText(String text)     { infoLabel.setText(text); }
    public Button getSubmitButton()            { return submitButton; }
    public Button getResetButton()             { return resetButton; }
    public void   clearHistory()               { historyBox.getChildren().clear(); }

    public void disableGameInput(boolean disable) {
        guessField.setDisable(disable);
        submitButton.setDisable(disable);
    }

    public void addHistoryRow(int nr, String guess, String result) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        // Zeilennummer
        Label nrLabel = new Label(String.format("%2d.", nr));
        nrLabel.setStyle("-fx-font-family: monospace; -fx-font-size: 13px;");
        nrLabel.setPrefWidth(30);

        // Farbige Kreise
        HBox circles = new HBox(6);
        circles.setAlignment(Pos.CENTER_LEFT);
        for (String color : guess.split(" ")) {
            Circle c = new Circle(12);
            c.setFill(toColor(color));
            c.setStroke(Color.GRAY);
            c.setStrokeWidth(1);
            circles.getChildren().add(c);
        }

        // Trennstrich
        Label sep = new Label("|");
        sep.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaa;");
        sep.setPrefWidth(15);

        // Auswertung
        Label res = new Label(result);
        res.setStyle("-fx-font-size: 14px; -fx-font-family: monospace;");

        row.getChildren().addAll(nrLabel, circles, sep, res);
        historyBox.getChildren().add(row);
    }

    private Color toColor(String c) {
        return switch (c) {
            case "R" -> Color.RED;
            case "G" -> Color.LIMEGREEN;
            case "B" -> Color.DODGERBLUE;
            case "Y" -> Color.GOLD;
            case "O" -> Color.ORANGE;
            case "P" -> Color.MEDIUMPURPLE;
            default  -> Color.GRAY;
        };
    }
}