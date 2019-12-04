package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final static int ALF_LENGHT = 32;
    private final int PORT = 8083;
    private String host = "localhost";
    private Socket socket;
    private GameLogic gameLogic = new GameLogic();

    @FXML
    BorderPane pane;
    private Label guessedWordLabel = new Label();
    private Button[] alphabetButtons = new Button[ALF_LENGHT];
    private GridPane mainGrid = new GridPane();
    private Label statusLabel = new Label(" ");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupScreen();
    }

    public void connect() {
        try {
            socket = new Socket(host, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupScreen() {
        MenuBar menuBar = new MenuBar();
        Menu gameMenu = new Menu("Game");
        MenuItem openPlayMenuItem = new MenuItem("Play");
        openPlayMenuItem.setOnAction(event -> {
            //  connect();
            setupMainPane();
            setupGuessedWordPane();
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
        guessedWordLabel.setText(gameLogic.guessedWord);
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

    private class AlphaButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Button button = (Button) event.getSource();
            String input = button.getText();
            if (gameLogic.nextStep(input)) {
                button.setStyle("-fx-background-color: #2f7e4d;");
                button.setDisable(true);
                guessedWordLabel.setText(gameLogic.guessedWord);
            } else {
                button.setStyle("-fx-background-color: #e62c2c;");
                button.setDisable(true);
            }
            if (gameLogic.missCount >= GameLogic.MAX_MISSES) {
                disableAlphaButtons();
                statusLabel.setText("Вы проиграли! Загаданное слово: \"" + gameLogic.keyWord + "\"");
            }
            if (gameLogic.isGuessed) {
                disableAlphaButtons();
                statusLabel.setText("Вы выиграли!");
            }
        }
    }

    private void disableAlphaButtons() {
        for (int i = 0; i < ALF_LENGHT; i++) {
            alphabetButtons[i].setStyle("-fx-background-color: transparent;");
            alphabetButtons[i].setDisable(true);
        }
    }
}
