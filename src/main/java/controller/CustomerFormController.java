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
    private CustomerModel customerModel= new CustomerModeImpl();

    public void initialize() throws ClassNotFoundException {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadCustomerTable();
    }

    private void loadCustomerTable() throws ClassNotFoundException {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet result = stm.executeQuery(sql);

            while (result.next()){
                Button btn = new Button("Delete");
                CustomerTm c = new CustomerTm(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getDouble(4),
                        btn
                );
                btn.setOnAction(actionEvent -> {
                    deleteCustomer(c.getId());
                });
                tmList.add(c);
            }
            tblCustomer.setItems(tmList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void deleteCustomer(String id) {
        String sql= "delete from customer where id='"+id+"'";
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if (result > 0){
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
    public void reloadButtonOnAction(javafx.event.ActionEvent actionEvent) throws ClassNotFoundException {
        loadCustomerTable();
        tblCustomer.refresh();
        clearFields();
    }

    @FXML
    public void saveButtonOnAction(javafx.event.ActionEvent actionEvent) {
        CustomerDto c = new CustomerDto(txtID.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText()));
        String sql= "insert into customer values('"+c.getId()+"','"+c.getName()+"','"+c.getAddress()+"',"+c.getSalary()+")";
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if (result > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved").show();
            }
        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateButtonOnAction(javafx.event.ActionEvent actionEvent) {
        CustomerDto c = new CustomerDto(txtID.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText()));
        String sql = "UPDATE customer SET name='"+c.getName()+"', address='"+c.getAddress()+"', salary="+c.getSalary()+" WHERE id='"+c.getId()+"'";
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if (result > 0) {
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
}
