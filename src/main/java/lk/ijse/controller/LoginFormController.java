package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.ScheduleDTO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LoginFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private TextField txtUserName;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnSignInOnAction(ActionEvent event){
        String userName = txtUserName.getText();
        String password = fieldPassword.getText();
        try {

            boolean isIn = userBO.searchUser(userName, password);
            if (!isIn) {
                new Alert(Alert.AlertType.WARNING, "Invalid UserName or Password").show();
            } else {
                isAdmin(userName, password);
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void isAdmin(String userName, String password){
        try {

            boolean isAdmin = userBO.checkAdmin(userName,password);

            String date = String.valueOf(LocalDate.now());
            LocalTime currentTime = LocalTime.now();
            String time = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            String logId = generateNextLogId();

            userBO.saveLogin(logId, userName, date, time);

            if(isAdmin){
                Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));

                Scene scene = new Scene(rootNode);
                Stage stage = (Stage) this.rootNode.getScene().getWindow();
                stage.setTitle("Dashboard Form");
                stage.setScene(scene);
                stage.centerOnScreen();
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DriverSchedule.fxml"));

                Parent rootNode = loader.load();

                DriverScheduleController driverScheduleController = loader.getController();

                List<ScheduleDTO> dtoList = userBO.getSchedule(userName);
                System.out.println(dtoList);
                driverScheduleController.setScheduleData(dtoList, userName);

                Scene scene = new Scene(rootNode);
                Stage stage = (Stage) this.rootNode.getScene().getWindow();
                stage.setTitle("Driver Schedule Form");
                stage.setScene(scene);
                stage.centerOnScreen();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String generateNextLogId() {
        try {
            String logId = userBO.generateNextId();
            return logId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void forgotPasswordOnAction(MouseEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/forgetPassword.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
