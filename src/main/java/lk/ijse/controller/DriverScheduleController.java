package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.ScheduleDTO;
import lk.ijse.dto.tm.ScheduleTm;
import lk.ijse.dao.custom.impl.ScheduleDAOImpl;

import java.io.IOException;
import java.util.List;

public class DriverScheduleController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableColumn<ScheduleTm, String> colAddress;

    @FXML
    private TableColumn<ScheduleTm, String> colBrand;

    @FXML
    private TableColumn<ScheduleTm, String> colContact;

    @FXML
    private TableColumn<ScheduleTm, String> colCusName;

    @FXML
    private TableColumn<ScheduleTm, Integer> colDays;

    @FXML
    private TableColumn<ScheduleTm, String> colPickUpDate;

    @FXML
    private TableColumn<ScheduleTm, String> colBId;

    @FXML
    private TableView<ScheduleTm> tableView;

    @FXML
    private TextField txtDriverName;

    private final ObservableList<ScheduleTm> obList = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        //setScheduleData();
    }

    private void setCellValueFactory() {
        colBId.setCellValueFactory(new PropertyValueFactory<>("bId"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colPickUpDate.setCellValueFactory(new PropertyValueFactory<>("pickUpDate"));
        colDays.setCellValueFactory(new PropertyValueFactory<>("days"));
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Login Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void setScheduleData(List<ScheduleDTO> dtoList, String userName) {
        obList.clear();
        var model = new ScheduleDAOImpl();
        try {
            String name = model.getDrName(userName);

            txtDriverName.setText(name);

            for (ScheduleDTO dto : dtoList){
                obList.add(
                        new ScheduleTm(
                                dto.getBId(),
                                dto.getName(),
                                dto.getBrand(),
                                dto.getAddress(),
                                dto.getContact(),
                                dto.getPickUpDate(),
                                dto.getDays()
                        )
                );
            }
            tableView.setItems(obList);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
