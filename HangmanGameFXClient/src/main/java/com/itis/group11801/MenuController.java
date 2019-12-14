package com.itis.group11801;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Button btn_exit, btn_play, btn_back, btn_rules;

    @FXML
    private Label title, label_rules;

    @FXML
    private ImageView image_menu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adjustButtons();
    }

    @FXML
    public void onClickPlay() throws IOException {
        btn_play.setDisable(true);
        Stage stage = (Stage) btn_play.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));
        stage.setResizable(false);
        stage.setTitle("Виселица");
        stage.setScene(new Scene(root, 1000, 550));
        stage.sizeToScene();
        stage.show();
    }

    @FXML
    public void onClickExit() {
        Stage stage = (Stage) btn_exit.getScene().getWindow();
        stage.close();
    }

    public void adjustButtons() {
        btn_rules.setOnAction(event -> {
            title.setVisible(false);
            image_menu.setVisible(false);
            btn_rules.setVisible(false);
            btn_rules.setDisable(true);
            btn_play.setVisible(false);
            btn_play.setDisable(true);
            btn_exit.setVisible(false);
            btn_exit.setDisable(true);
            btn_back.setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.seconds(0.3), btn_back);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
            label_rules.setVisible(true);
            FadeTransition ftLabel = new FadeTransition(Duration.seconds(0.3), label_rules);
            ftLabel.setFromValue(0);
            ftLabel.setToValue(1);
            ftLabel.play();
        });

        btn_back.setOnAction(event -> {
            label_rules.setVisible(false);
            btn_back.setVisible(false);
            btn_rules.setVisible(true);
            FadeTransition f1 = new FadeTransition(Duration.seconds(0.3), btn_rules);
            f1.setFromValue(0);
            f1.setToValue(1);
            f1.play();
            btn_rules.setDisable(false);
            btn_play.setVisible(true);
            FadeTransition f2 = new FadeTransition(Duration.seconds(0.3), btn_play);
            f2.setFromValue(0);
            f2.setToValue(1);
            f2.play();
            btn_play.setDisable(false);
            btn_exit.setVisible(true);
            FadeTransition f3 = new FadeTransition(Duration.seconds(0.3), btn_exit);
            f3.setFromValue(0);
            f3.setToValue(1);
            f3.play();
            btn_exit.setDisable(false);
            image_menu.setVisible(true);
            FadeTransition f4 = new FadeTransition(Duration.seconds(0.3), image_menu);
            f4.setFromValue(0);
            f4.setToValue(1);
            f4.play();
            title.setVisible(true);
            FadeTransition f5 = new FadeTransition(Duration.seconds(0.3), title);
            f5.setFromValue(0);
            f5.setToValue(1);
            f5.play();
        });
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
