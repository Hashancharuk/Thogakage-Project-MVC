package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.IOException;

public class DashBoardFormController {
    public AnchorPane pane;
    public Label lblTime;

    public void initialize(){
        calculateTime();
    }
    public void customerButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"))));
        stage.show();
    }
    private void calculateTime() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
        ), new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void itemButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemForm.fxml"))));
        stage.show();
    }

    public void placeOrderBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceOrderForm.fxml"))));
        stage.show();
    }
}
