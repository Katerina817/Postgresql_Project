package org.example.postgresql_project.FXControllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.postgresql_project.Entities.ReportAndAdmin;


import java.sql.Date;
import java.util.List;

public class ReportAndAdminController {
    List<ReportAndAdmin> list;
    public void setInfo(List<ReportAndAdmin> info) {
        this.list = info;
        setupTab1();
    }
    @FXML
    private TableView<ReportAndAdmin> ReportAndAdminTableView;
    @FXML
    private TableColumn<ReportAndAdmin,String> ReportIDTab,ContentTab,adminNameTab,adminSurnameTab;
    @FXML
    private TableColumn<ReportAndAdmin, Date> reportDateTab;
    @FXML
    public void setupTab1() {
        ObservableList<ReportAndAdmin> observableList = FXCollections.observableArrayList(list);
        ReportIDTab.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReportId()));
        ContentTab.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContent()));
        adminNameTab.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        adminSurnameTab.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        reportDateTab.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getReportDate()));
        ReportAndAdminTableView.setItems(observableList);
    }
}
