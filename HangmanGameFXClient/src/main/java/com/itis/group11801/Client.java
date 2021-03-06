package com.itis.group11801;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Виселица");
        primaryStage.setScene(new Scene(pane, 1000.0, 550.0));
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
