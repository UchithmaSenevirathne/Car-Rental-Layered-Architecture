package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.Validation.Validate;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;

public class ResetPasswordController {

    @FXML
    private TextField txtConfirmNewPwd;

    @FXML
    private TextField txtNewPassword;

    String userName;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnResetPwdOnAction(ActionEvent event) {
        String newPwd = txtNewPassword.getText();
        String confirmPwd = txtConfirmNewPwd.getText();

        try{
            if(ValidateAdminPw(newPwd)) {
                if (newPwd.equals(confirmPwd)) {
                    txtConfirmNewPwd.setStyle("-fx-background-color: white");
                    boolean isChanged = userBO.changePwd(confirmPwd, userName);

                    if (isChanged) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                        Parent rootNode = loader.load();

                        ConfirmationController confirmationController = loader.getController();

                        confirmationController.lblConfirm.setText("Password changed successfully");

                        Scene scene = new Scene(rootNode);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.centerOnScreen();
                        stage.show();
                        clearFields();
                    }
                } else {
                    txtConfirmNewPwd.setStyle("-fx-background-color: rgba(255,0,0,0.3)");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean ValidateAdminPw(String newPwd) {
        if(!Validate.validation(newPwd, txtNewPassword,"(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}")){    //#000ADpw
            return false;
        }
        return true;
    }

    private void clearFields() {
        txtConfirmNewPwd.setText("");
        txtNewPassword.setText("");
    }

    public void setUserName(String uName) {
        userName = uName;
    }
}
