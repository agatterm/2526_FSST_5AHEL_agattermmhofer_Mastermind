package org.example.mastermind;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
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

    @FXML private Label      titleLabel;
    @FXML private Label      infoLabel;
    @FXML private Label      triesLabel;
    @FXML private VBox       historyBox;
    @FXML private TextField  guessField;
    @FXML private Button     submitButton;
    @FXML private Button     resetButton;
    @FXML private Label      messageLabel;

    @FXML private ColorPicker colorPickerR;
    @FXML private ColorPicker colorPickerG;
    @FXML private ColorPicker colorPickerB;
    @FXML private ColorPicker colorPickerY;
    @FXML private ColorPicker colorPickerO;
    @FXML private ColorPicker colorPickerP;

    @FXML
    public void initialize() {
        titleLabel.setText("🎯 Mastermind");
        infoLabel.setText("Erlaubte Farben: R, G, B, Y, O, P");
        triesLabel.setText("Verbleibende Versuche: 10");
        guessField.setPromptText("z.B. RGBY oder R G B Y");
        messageLabel.setText("Bitte einen Code eingeben.");

        // Standardfarben setzen
        colorPickerR.setValue(Color.RED);
        colorPickerG.setValue(Color.LIMEGREEN);
        colorPickerB.setValue(Color.DODGERBLUE);
        colorPickerY.setValue(Color.GOLD);
        colorPickerO.setValue(Color.ORANGE);
        colorPickerP.setValue(Color.MEDIUMPURPLE);
    }

    /** Gibt die aktuell gewählte Farbe für einen Buchstaben zurück. */
    public Color getColor(String c) {
        return switch (c) {
            case "R" -> colorPickerR.getValue();
            case "G" -> colorPickerG.getValue();
            case "B" -> colorPickerB.getValue();
            case "Y" -> colorPickerY.getValue();
            case "O" -> colorPickerO.getValue();
            case "P" -> colorPickerP.getValue();
            default  -> Color.GRAY;
        };
    }

    public void addHistoryRow(int nr, String guess, String result) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        // Zeilennummer
        Label nrLabel = new Label(String.format("%2d.", nr));
        nrLabel.setStyle("-fx-font-family: monospace; -fx-font-size: 13px;");
        nrLabel.setPrefWidth(30);

        // Farbige Kreise – Farbe kommt live vom ColorPicker
        HBox circles = new HBox(6);
        circles.setAlignment(Pos.CENTER_LEFT);
        for (String color : guess.split(" ")) {
            Circle circle = new Circle(12);
            circle.setFill(getColor(color));
            circle.setStroke(Color.GRAY);
            circle.setStrokeWidth(1);
            circles.getChildren().add(circle);
        }

        // Trennstrich
        Label sep = new Label("|");
        sep.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaa;");
        sep.setPrefWidth(15);

        // Auswertung ● ○
        Label res = new Label(result);
        res.setStyle("-fx-font-size: 14px; -fx-font-family: monospace;");

        row.getChildren().addAll(nrLabel, circles, sep, res);
        historyBox.getChildren().add(row);
    }

    public String  getGuessInput()              { return guessField.getText(); }
    public void    clearGuessInput()            { guessField.clear(); }
    public void    setRemainingTries(int tries) { triesLabel.setText("Verbleibende Versuche: " + tries); }
    public void    setMessage(String message)   { messageLabel.setText(message); }
    public void    setInfoText(String text)     { infoLabel.setText(text); }
    public Button  getSubmitButton()            { return submitButton; }
    public Button  getResetButton()             { return resetButton; }
    public void    clearHistory()               { historyBox.getChildren().clear(); }

    public void disableGameInput(boolean disable) {
        guessField.setDisable(disable);
        submitButton.setDisable(disable);
    }
}