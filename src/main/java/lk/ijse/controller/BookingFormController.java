package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.BookingBO;
import lk.ijse.dto.*;
import lk.ijse.dto.tm.BookTm;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingFormController {
    BookingBO bookingBO = (BookingBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOKING);

    private final ObservableList<BookTm> obList3 = FXCollections.observableArrayList();

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colCarNo;

    @FXML
    private TableColumn<?, ?> colDays;

    @FXML
    private TableColumn<?, ?> colDrId;

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colPickUpDate;

    @FXML
    private TableColumn<?, ?> colBId;

    @FXML
    private ComboBox<String> comboCarNo;

    @FXML
    private ComboBox<String> comboCusId;

    @FXML
    private ComboBox<String> comboDrId;

    @FXML
    private TextField txtAdvancePayment;

    @FXML
    private DatePicker datepickerDate;

    @FXML
    private Label lblBookingId;

    @FXML
    private TextField txtCarName;

    @FXML
    private TextField txtCusAddress;

    @FXML
    private TextField txtCusContact;

    @FXML
    private TextField txtCusName;

    @FXML
    private TextField txtDays;

    @FXML
    private TextField txtDrName;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<BookTm> tableView;

    @FXML
    private Pane subAnchorPane;

    public void initialize() {
        setCellValueFactory();
        generateNextBookingId();
        loadCustomerIds();
        loadDriverIds();
        loadCarNo();
    }

    private void setCellValueFactory() {
        colBId.setCellValueFactory(new PropertyValueFactory<>("bId"));
        colCarNo.setCellValueFactory(new PropertyValueFactory<>("carNo"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colDrId.setCellValueFactory(new PropertyValueFactory<>("drId"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colPickUpDate.setCellValueFactory(new PropertyValueFactory<>("pickUpDate"));
        colDays.setCellValueFactory(new PropertyValueFactory<>("days"));
    }

    private void generateNextBookingId() {
        try {
            String rentId = bookingBO.generateNextBookingId();
            lblBookingId.setText(rentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCarNo() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CarDto> carList = bookingBO.getAllCars();

            for (CarDto carDto : carList) {
                obList.add(carDto.getCarNo());
            }
            comboCarNo.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> cusList = bookingBO.getAllCustomers();

            for (CustomerDto dto : cusList) {
                obList.add(dto.getId());
            }
            comboCusId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDriverIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<DriverDto> drList = bookingBO.getAllDrivers();

            for (DriverDto dto : drList) {
                obList.add(dto.getId());
            }
            comboDrId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddCarOnAction(ActionEvent event) {
        String bId = lblBookingId.getText();
        String carNo = comboCarNo.getValue();
        String brand = txtCarName.getText();
        String drId = comboDrId.getValue();
        String cusId = comboCusId.getValue();
        String pickUpDate = String.valueOf(datepickerDate.getValue());
        int days = Integer.parseInt(txtDays.getText());

        obList3.add(new BookTm(
                bId,
                carNo,
                brand,
                drId,
                cusId,
                pickUpDate,
                days
        ));
        System.out.println("++++++++++");
        System.out.println(obList3);

        tableView.setItems(obList3);
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
        stage.setTitle("Cars Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnConfirmRentOnAction(ActionEvent event) {
        String bId = lblBookingId.getText();
        String pickUpDate = String.valueOf(datepickerDate.getValue());
        int days = Integer.parseInt(txtDays.getText());
        String status = "Pending";
        double payment = Double.parseDouble(txtAdvancePayment.getText());
        String cusId = comboCusId.getValue();

        List<BookingDetailDTO> bookingDetailList = new ArrayList<>();

        for (BookTm bookTm : obList3) {
            bookingDetailList.add(new BookingDetailDTO(
                    bookTm.getBId(),
                    bookTm.getCarNo(),
                    bookTm.getDrId()
            ));
        }

        System.out.println(bookingDetailList);

        var bookDto = new BookDTO(
                bId,
                pickUpDate,
                days,
                status,
                payment,
                cusId,
                bookingDetailList
        );

        System.out.println(bookDto);

        try {
            boolean isSuccess = bookingBO.makeBooking(bookDto);
            System.out.println(isSuccess);
            if(isSuccess) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                Parent rootNode = loader.load();

                ConfirmationController confirmationController = loader.getController();

                confirmationController.lblConfirm.setText("Booking completed successfully");

                Scene scene = new Scene(rootNode);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.centerOnScreen();
                stage.show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void cmbCarOnAction(ActionEvent event) {
        String carNo = comboCarNo.getValue();

        try {
            CarDto dto = bookingBO.searchCars(carNo);

            txtCarName.setText(dto.getBrand());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbCusOnAction(ActionEvent event){
        String id = comboCusId.getValue();
        CustomerDto dto = null;
        try {
            dto = bookingBO.searchCustomer(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        txtCusName.setText(dto.getName());
        txtCusContact.setText(dto.getContact());
        txtCusAddress.setText(dto.getAddress());
    }

    @FXML
    void cmbDrOnAction(ActionEvent event){
        String drId = comboDrId.getValue();
        DriverDto dto = null;
        try {
            dto = bookingBO.searchDriver(drId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        txtDrName.setText(dto.getUserName());
    }

    @FXML
    void btnShowAllBookingOnAction(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewBooking.fxml"));
        AnchorPane viewBooking = null;
        try {
            viewBooking = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        subAnchorPane.getChildren().setAll(viewBooking);
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
        stage.show();
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event){
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Dashboard Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnDriverOnAction(ActionEvent event){
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
    void btnPaymentOnAction(ActionEvent event){
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
    void btnReportOnAction(ActionEvent event){
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
