package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

    @FXML
    private Pane pane_right;

    @FXML
    public void onClickPlay() throws IOException {
        btn_play.setDisable(true);
        Stage stage = (Stage) btn_play.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/game.fxml"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_rules.setOnAction(event -> {
            title.setVisible(false);
            btn_rules.setVisible(false);
            btn_rules.setDisable(true);
            btn_play.setVisible(false);
            btn_play.setDisable(true);
            btn_exit.setVisible(false);
            btn_exit.setDisable(true);
            btn_back.setVisible(true);
            image_menu.setVisible(false);
            label_rules.setVisible(true);
        });

        btn_back.setOnAction(event -> {
            title.setVisible(true);
            btn_rules.setVisible(true);
            btn_rules.setDisable(false);
            btn_play.setVisible(true);
            btn_play.setDisable(false);
            btn_exit.setVisible(true);
            btn_exit.setDisable(false);
            btn_back.setVisible(false);
            image_menu.setVisible(true);
            label_rules.setVisible(false);
        });
    }
}
