package org.example.postgresql_project.FXControllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.postgresql_project.DataBaseConnection;
import org.example.postgresql_project.Entities.*;
import org.example.postgresql_project.EntitiesControl.ForAllEntities;

import java.sql.Date;
import java.util.List;

public class MainPageController {
    Boolean user;
    public void setInfo(Boolean info) {
        this.user = info;
    }
    @FXML
    private Tab AdminTab;
    @FXML
    private Tab ReportTypeTab;
    @FXML
    private Tab ReportTab;
    @FXML
    private Tab RecyclingRuleTab;
    @FXML
    private Tab RecyclingTab;
    @FXML
    private Tab RecyclingStatusTab;
    @FXML
    private Tab TrashInfoTab;
    @FXML
    private Tab TrashTypeTab;
    @FXML
    private Tab UserTab;


    @FXML
    private TableView<Admin> AdminTableView;
        @FXML
        private TableColumn<Admin,String> Admin1,Admin2,Admin3,Admin4,Admin5,Admin6;
        @FXML
        private TableColumn<Admin,Integer> Admin7,Admin8;
    @FXML
    private TableView<ReportType> ReportTypeTableView;
        @FXML
        private TableColumn<ReportType,String> ReportType1,ReportType2;
    @FXML
    private TableView<Report> ReportTableView;
        @FXML
        private TableColumn<Report,String> Report1,Report2,Report3,Report4,Report6;
        @FXML
        private TableColumn<Report, Date> Report5;
    @FXML
    private TableView<RecyclingRule> RecyclingRuleTableView;
        @FXML
        private TableColumn<RecyclingRule,String> RecyclingRule1,RecyclingRule2;
    @FXML
    private TableView<Recycling> RecyclingTableView;
        @FXML
        private TableColumn<Recycling,String> Recycling1,Recycling2,Recycling3,Recycling4;
        @FXML
        private TableColumn<Recycling,Date> Recycling5;
    @FXML
    private TableView<RecyclingStatus> RecyclingStatusTableView;
        @FXML
        private TableColumn<RecyclingStatus,String> RecyclingStatus1,RecyclingStatus2,RecyclingStatus3;
    @FXML
    private TableView<TrashInfo> TrashInfoTableView;
        @FXML
        private TableColumn<TrashInfo,String> TrashInfo1,TrashInfo2,TrashInfo3;
        @FXML
        private TableColumn<TrashInfo,Integer> TrashInfo4;
    @FXML
    private TableView<TrashType> TrashTypeTableView;
        @FXML
        private TableColumn<TrashType,String> TrashType1,TrashType2;
    @FXML
    private TableView<User> UserTableView;
        @FXML
        private TableColumn<User,String> User1,User2,User3,User4,User5,User6;

    private final ForAllEntities forAllEntities = new ForAllEntities(DataBaseConnection.getConnection());
    @FXML
    public void initialize() {
        setupTab1();
        setupTab2();
        setupTab3();
        setupTab4();
        setupTab5();
        setupTab6();
        setupTab7();
        setupTab8();
        setupTab9();
    }

    private void setupTab1() {
        @SuppressWarnings("unchecked")
        List<Admin> admins = (List<Admin>) forAllEntities.getAllRows("admin");
        ObservableList<Admin> adminObservableList = FXCollections.observableArrayList(admins);
        Admin1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdminId()));
        Admin2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));
        Admin3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        Admin4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        Admin5.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        Admin6.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        Admin7.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAge()));
        Admin8.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBirthYear()));
        AdminTableView.setItems(adminObservableList);
    }

    private void setupTab2() {
        @SuppressWarnings("unchecked")
        List<ReportType> rows = (List<ReportType>) forAllEntities.getAllRows("report_type");
        ObservableList<ReportType> observableList = FXCollections.observableArrayList(rows);
        ReportType1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReportTypeId()));
        ReportType2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReportTypeName()));
        ReportTypeTableView.setItems(observableList);
    }
    private void setupTab3() {
        @SuppressWarnings("unchecked")
        List<Report> rows = (List<Report>) forAllEntities.getAllRows("report");
        ObservableList<Report> observableList = FXCollections.observableArrayList(rows);
        Report1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReportId()));
        Report2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReportTypeId()));
        Report3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdminId()));
        Report4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContent()));
        Report5.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getReportDate()));
        Report6.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingId()));

        ReportTableView.setItems(observableList);
    }
    private void setupTab4() {
        @SuppressWarnings("unchecked")
        List<RecyclingRule> rows = (List<RecyclingRule>) forAllEntities.getAllRows("recycling_rule");
        ObservableList<RecyclingRule> observableList = FXCollections.observableArrayList(rows);
        RecyclingRule1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRuleId()));
        RecyclingRule2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContent()));
        RecyclingRuleTableView.setItems(observableList);
    }
    private void setupTab5() {
        @SuppressWarnings("unchecked")
        List<Recycling> rows = (List<Recycling>) forAllEntities.getAllRows("recycling");
        ObservableList<Recycling> observableList = FXCollections.observableArrayList(rows);
        Recycling1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingId()));
        Recycling2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingStatusId()));
        Recycling3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRuleId()));
        Recycling4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashInfoId()));
        Recycling5.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRecyclingDate()));

        RecyclingTableView.setItems(observableList);
    }
    private void setupTab6() {
        @SuppressWarnings("unchecked")
        List<RecyclingStatus> rows = (List<RecyclingStatus>) forAllEntities.getAllRows("recycling_status");
        ObservableList<RecyclingStatus> observableList = FXCollections.observableArrayList(rows);
        RecyclingStatus1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingStatusId()));
        RecyclingStatus2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingStatusName()));
        RecyclingStatus3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurrentProcessDescription()));

        RecyclingStatusTableView.setItems(observableList);
    }
    private void setupTab7() {
        @SuppressWarnings("unchecked")
        List<TrashInfo> rows = (List<TrashInfo>) forAllEntities.getAllRows("trash_info");
        ObservableList<TrashInfo> observableList = FXCollections.observableArrayList(rows);
        TrashInfo1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashInfoId()));
        TrashInfo2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserId()));
        TrashInfo3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashTypeId()));
        TrashInfo4.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTrashQuantity()));

        TrashInfoTableView.setItems(observableList);
    }
    private void setupTab8() {
        @SuppressWarnings("unchecked")
        List<TrashType> rows = (List<TrashType>) forAllEntities.getAllRows("trash_type");
        ObservableList<TrashType> observableList = FXCollections.observableArrayList(rows);
        TrashType1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashTypeId()));
        TrashType2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashTypeName()));

        TrashTypeTableView.setItems(observableList);
    }
    private void setupTab9() {
        @SuppressWarnings("unchecked")
        List<User> admins = (List<User>) forAllEntities.getAllRows("users");
        ObservableList<User> adminObservableList = FXCollections.observableArrayList(admins);
        User1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserId()));
        User2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));
        User3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        User4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        User5.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        User6.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        UserTableView.setItems(adminObservableList);
    }
}
