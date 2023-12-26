package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.SalaryBO;
import lk.ijse.dto.SalaryDTO;
import lk.ijse.dto.tm.SalaryTm;
import java.util.List;

public class ViewSalaryController {
    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDrId;

    @FXML
    private TableColumn<?, ?> colMonth;

    @FXML
    private TableColumn<?, ?> colSalId;

    @FXML
    private TableView<SalaryTm> tableView;

    public final ObservableList<SalaryTm> obListSal = FXCollections.observableArrayList();

    @FXML
    private TextField txtSearch;
    SalaryBO salaryBO = (SalaryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SALARY);

    public void initialize(){
        setCellValueFactory();
        loadAllSalary();
    }

    private void setCellValueFactory(){
        colSalId.setCellValueFactory(new PropertyValueFactory<>("drSalId"));
        colDrId.setCellValueFactory(new PropertyValueFactory<>("drId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
    }

    private void loadAllSalary() {
        obListSal.clear();

        try {
            List<SalaryDTO> dtoList = salaryBO.getAllSalary();

            for (SalaryDTO dto : dtoList){
                obListSal.add(
                        new SalaryTm(
                                dto.getDrSalId(),
                                dto.getDrId(),
                                dto.getAmount(),
                                dto.getMonth()
                        )
                );
            }
            tableView.setItems(obListSal);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void searchSalDetails(ActionEvent event) {
        FilteredList<SalaryTm> filteredData = new FilteredList<>(obListSal, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(SalaryTm -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                String amountString = String.valueOf(SalaryTm.getAmount());

                if(SalaryTm.getDrSalId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(SalaryTm.getDrId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                }else if(amountString.toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                }else if(SalaryTm.getMonth().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else
                    return false;
            });
        });

        SortedList<SalaryTm> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }

}
