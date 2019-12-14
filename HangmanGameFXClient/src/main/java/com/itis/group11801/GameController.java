package com.itis.group11801;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private final static int ALF_LENGTH = 33;
    private ClientHelper clientHelper;
    private Button[] alphabetButtons = new Button[ALF_LENGTH];

    @FXML
    private BorderPane pane;
    @FXML
    private Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5,
            btn_6, btn_7, btn_8, btn_9, btn_10, btn_11, btn_12,
            btn_13, btn_14, btn_15, btn_16, btn_17, btn_18,
            btn_19, btn_20, btn_21, btn_22, btn_23, btn_24,
            btn_25, btn_26, btn_27, btn_28, btn_29, btn_30,
            btn_31, btn_32;
    @FXML
    private Label label_state;
    @FXML
    private Label label_guessed;
    @FXML
    private Label label_answer;
    @FXML
    private ImageView image_game;

    public GameController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientHelper = new ClientHelper();
        startGame();
    }

    private void startGame() {
        clientHelper.connect();
        setupAlfButtons();
        label_guessed.setText(clientHelper.receive());
        if (clientHelper.state) {
            label_state.setText("Ход противника");
            disAlphaButtons();
        } else {
            label_state.setText("Ваш ход. Выберете букву");
        }
        receiver.start();
    }

    private Thread receiver = new Thread(() -> {
        Boolean flag = true;
        while (flag) {
            int isOver = Integer.parseInt(clientHelper.receive());
            switch (isOver) {
                case 0:
                    Button btn = new Button();
                    String letter = clientHelper.receive();
                    for (Button alphabetButton : alphabetButtons) {
                        if (alphabetButton.getText().equals(letter)) {
                            btn = alphabetButton;
                        }
                    }
                    if (Integer.parseInt(clientHelper.receive()) == 0) {
                        btn.setStyle("-fx-background-color: #e62c2c;");
                        Platform.runLater(() ->
                                label_answer.setText("Неверно"));
                        clientHelper.state = !clientHelper.state;
                    } else {
                        btn.setStyle("-fx-background-color: #2f905c;");
                        Platform.runLater(() ->
                                label_answer.setText("Правильно"));
                    }
                    String line = clientHelper.receive();
                    Platform.runLater(() -> {
                                label_guessed.setText(line);
                                label_state.setText("Ход противника");
                            }
                    );
                    break;
                case 1:
                    Platform.runLater(() ->
                            label_state.setText("Конец игры"));
                    disableAlphaButtons();
                    if (Integer.parseInt(clientHelper.receive()) == 0) {
                        String text = "Вы проиграли! Загаданное слово: \"" + clientHelper.receive() + "\"";
                        Platform.runLater(() -> {
                            label_answer.setText(text);
                            Image im = new Image("img/10.png");
                            image_game.setImage(im);
                        });
                    } else {
                        String msg = clientHelper.receive();
                        Platform.runLater(() ->
                                label_guessed.setText(msg));
                        disableAlphaButtons();
                        String text = "Поздравляем, Вы выиграли!";
                        Platform.runLater(() -> {
                            Image img = new Image("img/11.png");
                            image_game.setImage(img);
                            label_answer.setText(text);
                        });
                    }
                    flag = false;
                    break;
            }
            String missCount = clientHelper.receive();
            Platform.runLater(() -> {
                Image im = new Image("img/" + missCount + ".png");
                image_game.setImage(im);
            });
            if (clientHelper.state) {
                disAlphaButtons();
            } else if (flag) {
                enableAlphaButtons();
                Platform.runLater(() ->
                        label_state.setText("Ваш ход. Выберете букву"));

            }
        }
    });

    private void setupAlfButtons() {
        alphabetButtons[0] = btn_0;
        alphabetButtons[1] = btn_1;
        alphabetButtons[2] = btn_2;
        alphabetButtons[3] = btn_3;
        alphabetButtons[4] = btn_4;
        alphabetButtons[5] = btn_5;
        alphabetButtons[6] = btn_6;
        alphabetButtons[7] = btn_7;
        alphabetButtons[8] = btn_8;
        alphabetButtons[9] = btn_9;
        alphabetButtons[10] = btn_10;
        alphabetButtons[11] = btn_11;
        alphabetButtons[12] = btn_12;
        alphabetButtons[13] = btn_13;
        alphabetButtons[14] = btn_14;
        alphabetButtons[15] = btn_15;
        alphabetButtons[16] = btn_16;
        alphabetButtons[17] = btn_17;
        alphabetButtons[18] = btn_18;
        alphabetButtons[19] = btn_19;
        alphabetButtons[20] = btn_20;
        alphabetButtons[21] = btn_21;
        alphabetButtons[22] = btn_22;
        alphabetButtons[23] = btn_23;
        alphabetButtons[24] = btn_24;
        alphabetButtons[25] = btn_25;
        alphabetButtons[26] = btn_26;
        alphabetButtons[27] = btn_27;
        alphabetButtons[28] = btn_28;
        alphabetButtons[29] = btn_29;
        alphabetButtons[30] = btn_30;
        alphabetButtons[31] = btn_31;
        alphabetButtons[32] = btn_32;
        for (int i = 0; i < ALF_LENGTH; i++) {
            alphabetButtons[i].setOnAction(new AlphaButtonHandler());
        }
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
        for (int i = 0; i < ALF_LENGTH; i++) {
            alphabetButtons[i].setDisable(true);
        }
    }

    private void disAlphaButtons() {
        for (int i = 0; i < ALF_LENGTH; i++) {
            alphabetButtons[i].setDisable(true);
        }
    }

    private void enableAlphaButtons() {
        for (int i = 0; i < ALF_LENGTH; i++) {
            alphabetButtons[i].setDisable(false);
        }
    }

    @FXML
    public void onMouseEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setEffect(new DropShadow());
        ScaleTransition st = new ScaleTransition(
                Duration.seconds(0.2), button
        );
        st.setToX(1.2);
        st.setToY(1.2);
        st.play();
    }

    @FXML
    public void onMouseExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setEffect(null);
        ScaleTransition st = new ScaleTransition(
                Duration.seconds(0.2), button
        );
        st.setFromX(1.2);
        st.setFromY(1.2);
        st.setToX(1);
        st.setToY(1);
        st.play();
    }

    @FXML
    public void onEntered(MouseEvent event) {
        Label label = (Label) event.getSource();
        ScaleTransition st = new ScaleTransition(
                Duration.seconds(0.2), label
        );
        st.setToX(1.2);
        st.setToY(1.2);
        st.play();
    }

    @FXML
    public void onExited(MouseEvent event) {
        Label label = (Label) event.getSource();
        ScaleTransition st = new ScaleTransition(
                Duration.seconds(0.2), label
        );
        st.setFromX(1.2);
        st.setFromY(1.2);
        st.setToX(1);
        st.setToY(1);
        st.play();
    }
}
