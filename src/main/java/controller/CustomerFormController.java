package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import dto.CustomerDto;
import dto.tm.CustomerTm;
import model.CustomerModel;
import model.impl.CustomerModeImpl;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class CustomerFormController {

    public TableView tblCustomer;
    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private TableColumn colID;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TableColumn colOption;
    private CustomerModel customerModel = new CustomerModeImpl();

    public void initialize() throws ClassNotFoundException, SQLException {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadCustomerTable();
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData((CustomerTm) newValue);
        });
    }

    private void loadCustomerTable() throws SQLException, ClassNotFoundException {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();
        List<CustomerDto> dtoList = customerModel.allCustomer();
        for (CustomerDto dto : dtoList) {
            Button btn = new Button("Delete");
            CustomerTm c = new CustomerTm(
                    dto.getId(),
                    dto.getName(),
                    dto.getAddress(),
                    dto.getSalary(),
                    btn
            );
            btn.setOnAction(actionEvent -> deleteCustomer(c.getId()));
            tmList.add(c);
        }
        tblCustomer.setItems(tmList);
    }


    private void deleteCustomer(String id) {
        try {
            boolean isDeleted = customerModel.deleteCustomer(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Delete Customer").show();
                loadCustomerTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void reloadButtonOnAction(javafx.event.ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        loadCustomerTable();
        tblCustomer.refresh();
        clearFields();
    }


    public void saveButtonOnAction(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            boolean isSaved = customerModel.saveCustomer(
                    new CustomerDto(txtID.getText(),
                            txtName.getText(),
                            txtAddress.getText(),
                            Double.parseDouble(txtSalary.getText())
                    ));
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                loadCustomerTable();
                clearFields();
            }
        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateButtonOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            boolean isUpdated = customerModel.updateCustomer(new CustomerDto(txtID.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())));

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved").show();
                loadCustomerTable();
                clearFields();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void clearFields() {
        txtID.clear();
        txtName.clear();
        txtSalary.clear();
        txtAddress.clear();
        txtID.setEditable(true);
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
       Stage stage = (Stage) tblCustomer.getScene().getWindow();
       stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }
    private void setData(CustomerTm newValue) {
        if (newValue != null) {
            txtID.setEditable(false);
            txtID.setText(newValue.getId());
            txtName.setText(newValue.getName());
            txtAddress.setText(newValue.getAddress());
            txtSalary.setText(String.valueOf(newValue.getSalary()));
        }
    }

}
