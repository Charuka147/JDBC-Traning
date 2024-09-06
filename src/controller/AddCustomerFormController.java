package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    public TableView tblCustomer;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewOnAction(ActionEvent event) {
        loadTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        loadTable();
    }

    private void loadTable(){
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM customer";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/thogakade","root","12345");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()){
                Customer customer = new Customer(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getDouble("salary")
                );

                System.out.println(customer);
                customerObservableList.add(customer);
            }

            tblCustomer.setItems(customerObservableList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}