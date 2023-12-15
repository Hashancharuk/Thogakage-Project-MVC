package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class PlaceOrderFormController {

    public AnchorPane pane;
    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtDesc;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXComboBox txtCustId;

    @FXML
    private JFXComboBox itemCode;

    @FXML
    private TableColumn colCode;

    @FXML
    private TableColumn colDesc;

    @FXML
    private TableColumn colPrice;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableColumn colAmont;

    @FXML
    private TableColumn colOption;


    public void addToCartButtonOnAction(javafx.event.ActionEvent actionEvent) {
    }

    public void placeOrderBtnOnAction(javafx.event.ActionEvent actionEvent) {
    }

    public void backButtonOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));

    }
}
