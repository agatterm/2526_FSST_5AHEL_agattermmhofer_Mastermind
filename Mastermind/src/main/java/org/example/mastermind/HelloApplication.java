package org.example.mastermind;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MastermindV1"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 550);

        // MastermindView wurde von FXMLLoader als Controller instanziiert
        MastermindView view = fxmlLoader.getController();

        // Model und Controller erzeugen und verbinden
        MastermindModel model = new MastermindModel();
        HelloController controller = new HelloController(model, view);
        controller.initialize();

        stage.setTitle("Mastermind");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}