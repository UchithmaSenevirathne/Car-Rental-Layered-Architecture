package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CarBO;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.DriverBO;
import lk.ijse.dto.CarOutDto;
import lk.ijse.dto.DriverInTimeDto;
import lk.ijse.dto.tm.CarOutTM;
import lk.ijse.dto.tm.DriverInTimeTM;
import lk.ijse.dao.custom.impl.BookingDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable{

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblBookings;

    @FXML
    private Label lblEarnings;

    @FXML
    private Label lbltravellers;

    @FXML
    private TableColumn<?, ?> colCarNo;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableView<CarOutTM> tableView;

    @FXML
    private TableColumn<?, ?> colDrName;

    @FXML
    private TableColumn<?, ?> colLoginTime;

    @FXML
    private TableView<DriverInTimeTM> tableViewLogin;

    public final ObservableList<CarOutTM> obListCar = FXCollections.observableArrayList();

    public final ObservableList<DriverInTimeTM> obListDrLog = FXCollections.observableArrayList();

    DriverBO driverBO = (DriverBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DRIVER);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    CarBO carBO = (CarBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CAR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactoryCar();
        setCellValueFactoryLog();
        loadAllCars();
        loadAllDrIn();
        setValuesLables();
    }

    private void loadAllDrIn() {
        obListDrLog.clear();

        try {
            String date = String.valueOf(LocalDate.now());

            List<DriverInTimeDto> dtoList = driverBO.gerDrInTime(date);

            for (DriverInTimeDto dto : dtoList){
                obListDrLog.add(
                        new DriverInTimeTM(
                                dto.getName(),
                                dto.getTime()
                        )
                );
            }
            tableViewLogin.setItems(obListDrLog);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactoryLog() {
        colDrName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLoginTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    private void setValuesLables() {
        var modelBook = new BookingDAOImpl();

        try{
            int countBooking = modelBook.getCountBooking();

            int countCustomers = customerBO.getCountCus();

            lblBookings.setText(String.valueOf(countBooking));
            lbltravellers.setText(String.valueOf(countCustomers));

            lblBookings.setStyle("-fx-text-fill: #1abc9c; -fx-font-weight: bold; -fx-font-size: 19px");
            lbltravellers.setStyle("-fx-text-fill: #fd9644; -fx-font-weight: bold; -fx-font-size: 19px");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadAllCars() {
        obListCar.clear();

        try {
            List<CarOutDto> dtoList = carBO.getCarOut();

            for (CarOutDto dto : dtoList){
                obListCar.add(
                        new CarOutTM(
                                dto.getCarNo(),
                                dto.getBrand(),
                                dto.getPickUpDate()
                        )
                );
            }
            tableView.setItems(obListCar);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactoryCar() {
        colCarNo.setCellValueFactory(new PropertyValueFactory<>("carNo"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("pickUpDate"));
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event){

        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/CustomerManageForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Customer Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnDriverOnAction(ActionEvent event) {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/DriverManageForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Driver Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/PaymentForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Payment Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnReportOnAction(ActionEvent event) {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/ReportForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Report Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnSalaryOnAction(ActionEvent event){
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/SalaryForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Salary Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnBookingOnAction(ActionEvent event){
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/BookingForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Booking Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnCarOnAction(ActionEvent event){
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/CarManageForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Car Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event){
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Login Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnAdminOnAction(ActionEvent event){
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/VerifySuperAdmin.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Admin Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}


