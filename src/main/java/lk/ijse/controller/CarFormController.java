package lk.ijse.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.Validation.Validate;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CarBO;
import lk.ijse.dto.CarDto;

public class CarFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtCarBrand;

    @FXML
    private TextField txtCarNo;

    @FXML
    public Button btnCarForm;

    @FXML
    private TextField txtCurrentMileage;

    @FXML
    private TextField txtKmOneDay;

    @FXML
    private TextField txtPriceExtraKm;

    @FXML
    private TextField txtPriceOneDay;

    @FXML
    private ComboBox<String> availability;
    Stage stage;

    CarBO carBO = (CarBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CAR);

    @FXML
    void btnCancelCarOnAction(ActionEvent event) {
        stage = (Stage) rootNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnCarOnAction(ActionEvent event) {
        String carNo = txtCarNo.getText();
        String brand = txtCarBrand.getText();
        String availa = availability.getValue();
        double currentMileage = Double.parseDouble(txtCurrentMileage.getText());
        double kmOneDay = Double.parseDouble(txtKmOneDay.getText());
        double priceOneDay = Double.parseDouble(txtPriceOneDay.getText());
        double priceExtraKm = Double.parseDouble(txtPriceExtraKm.getText());

        var dto = new CarDto(carNo, brand, availa, currentMileage, kmOneDay, priceOneDay, priceExtraKm);

        try{
            if(validateCar(carNo)) {
                if (btnCarForm.getText().equals("UPDATE")) {
                    boolean isUpdated = carBO.updateCar(dto);
                    if (isUpdated) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                        Parent rootNode = loader.load();

                        ConfirmationController confirmationController = loader.getController();

                        confirmationController.lblConfirm.setText("Car updated successfully");

                        Scene scene = new Scene(rootNode);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.centerOnScreen();
                        stage.show();
                        clearFields();

                        btnCancelCarOnAction(event);
                    }
                } else if (btnCarForm.getText().equals("SAVE")) {
                    boolean isSaved = carBO.saveCar(dto);

                    if (isSaved) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                        Parent rootNode = loader.load();

                        ConfirmationController confirmationController = loader.getController();

                        confirmationController.lblConfirm.setText("Car saved successfully");

                        Scene scene = new Scene(rootNode);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.centerOnScreen();
                        stage.show();
                        clearFields();
                    }
                }
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean validateCar(String carNo) {
        if(!Validate.validation(carNo,txtCarNo,"[C][R][0-9]{3,}")){
            return false;
        }
        return true;
    }

    private void clearFields() {
        txtCarNo.setText("");
        txtCarBrand.setText("");
    }

    public void setCarData(String carNo, String brand, double currentMileage, double priceOneDay, double kmOneDay, double priceExtraKm) {
        txtCarNo.setText(carNo);
        txtCarBrand.setText(brand);
        txtCurrentMileage.setText(String.valueOf(currentMileage));
        txtPriceOneDay.setText(String.valueOf(priceOneDay));
        txtKmOneDay.setText(String.valueOf(kmOneDay));
        txtPriceExtraKm.setText(String.valueOf(priceExtraKm));
    }

    public void setData(String carNo) {
        txtCarNo.setText(carNo);
    }

    public void setComboData(ObservableList<String> obList) {
        availability.setItems(obList);
    }
}
