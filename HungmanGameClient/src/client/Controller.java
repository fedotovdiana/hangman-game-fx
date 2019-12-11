package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final static int ALF_LENGHT = 32;
    private ClientHelper clientHelper;

    @FXML
    BorderPane pane;
    private Label guessedWordLabel = new Label();
    private Button[] alphabetButtons = new Button[ALF_LENGHT];
    private GridPane mainGrid = new GridPane();
    private Label statusLabel = new Label(" ");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientHelper = new ClientHelper();
        setupScreen();
    }

    private void setupScreen() {
        MenuBar menuBar = new MenuBar();
        Menu gameMenu = new Menu("Game");
        MenuItem openPlayMenuItem = new MenuItem("Play");
        openPlayMenuItem.setOnAction(event -> {
            clientHelper.connect();
            setupMainPane();
            setupGuessedWordPane();
            startGame();
        });
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(event ->
                mainGrid.getChildren().clear()
        );
        MenuItem closeGameMenuItem = new MenuItem("Close Game");
        closeGameMenuItem.setOnAction(event -> {
            pane.setBottom(null);
            pane.setCenter(null);
        });
        gameMenu.getItems().addAll(openPlayMenuItem, closeGameMenuItem, exitMenuItem);
        menuBar.getMenus().addAll(gameMenu);
        pane.setTop(menuBar);
    }

    private void setupMainPane() {
        Label message = new Label("Выберите букву");
        mainGrid.add(message, 0, 0);
        setupButtonPane();
        pane.setBottom(mainGrid);
    }

    private void setupGuessedWordPane() {
        StackPane guessedWordPane = new StackPane();
        guessedWordLabel.setText(clientHelper.recieve());
        guessedWordPane.getChildren().addAll(guessedWordLabel);
        pane.setCenter(guessedWordPane);
    }

    private void setupButtonPane() {
        GridPane buttonsGridPane = new GridPane();
        buttonsGridPane.setMaxWidth(600);
        char ch = 'а';
        for (int i = 0; i < ALF_LENGHT; i++) {
            alphabetButtons[i] = new Button(String.valueOf(ch));
            alphabetButtons[i].setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
            alphabetButtons[i].setOnAction(new AlphaButtonHandler());
            ch++;
        }
        for (int i = 0; i < ALF_LENGHT; i++) {
            buttonsGridPane.add(alphabetButtons[i], i, 0);
        }
        mainGrid.add(buttonsGridPane, 0, 1);
        mainGrid.add(statusLabel, 0, 2);
    }

    private void startGame() {
        if (clientHelper.state) disAlphaButtons();
        receiver.start();
    }

    private class AlphaButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Button button = (Button) event.getSource();
            String input = button.getText();
            clientHelper.send(input);
            disAlphaButtons();
        }
    }

    private void disableAlphaButtons() {
        for (int i = 0; i < ALF_LENGHT; i++) {
            alphabetButtons[i].setStyle("-fx-background-color: transparent;");
            alphabetButtons[i].setDisable(true);
        }
    }

    private void disAlphaButtons() {
        for (int i = 0; i < ALF_LENGHT; i++) {
            alphabetButtons[i].setDisable(true);
        }
    }

    private void enableAlphaButtons() {
        for (int i = 0; i < ALF_LENGHT; i++) {
            alphabetButtons[i].setDisable(false);
        }
    }

    private Thread receiver = new Thread(() -> {
        Boolean flag = true;
        while (flag) {
            int isOver = Integer.parseInt(clientHelper.recieve());
            switch (isOver) {
                case 0:
                    Button btn = new Button();
                    String letter = clientHelper.recieve();
                    for (Button alphabetButton : alphabetButtons) {
                        if (alphabetButton.getText().equals(letter)) {
                            btn = alphabetButton;
                        }
                    }
                    if (Integer.parseInt(clientHelper.recieve()) == 0) {
                        btn.setStyle("-fx-background-color: #e62c2c;");
                        clientHelper.state = !clientHelper.state;
                    } else {
                        btn.setStyle("-fx-background-color: #2f7e4d;");
                    }
                    String line = clientHelper.recieve();
                    Platform.runLater(() ->
                            guessedWordLabel.setText(line));
                    break;
                case 1:
                    if (Integer.parseInt(clientHelper.recieve()) == 0) {
                        disableAlphaButtons();
                        String text = "Вы проиграли! Загаданное слово: \"" + clientHelper.recieve() + "\"";
                        Platform.runLater(() ->
                                statusLabel.setText(text));
                    } else {
                        String line1 = clientHelper.recieve();
                        Platform.runLater(() ->
                                guessedWordLabel.setText(line1));
                        disableAlphaButtons();
                        String text = "Вы выиграли";
                        Platform.runLater(() ->
                                statusLabel.setText(text));
                    }
                    flag = false;
                    break;
            }
            if (clientHelper.state) disAlphaButtons();
            else enableAlphaButtons();

        }
    });
}
