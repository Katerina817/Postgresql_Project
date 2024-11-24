package org.example.postgresql_project.FXControllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.postgresql_project.DataBaseConnection;
import org.example.postgresql_project.Entities.*;
import org.example.postgresql_project.EntitiesControl.AdminControl;
import org.example.postgresql_project.EntitiesControl.ForAllEntities;
import org.example.postgresql_project.EntitiesControl.ReportControl;
import org.example.postgresql_project.EntitiesControl.ReportTypeControl;
import org.example.postgresql_project.ErrorClass;

import java.sql.Date;
import java.sql.SQLException;
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

//ONLY FOR ADMIN
    @FXML
    private ComboBox<String> IDComboBox, LoginComboBox, NameComboBox,SurnameComboBox,PasswordComboBox, EmailComboBox;
    @FXML
    private ComboBox<Integer> AgeComboBox, BirthYearComboBox;
//ONLY FOR ReportType
    @FXML
    private ComboBox<String> IDReportTypeComboBox,ReportTypeNameComboBox;
//ONLY FOR Report
    @FXML
    private ComboBox<String> ReportIDComboBox, ReportTypeIDComboBox, IDAdminComboBox, ReportContentComboBox,ReportIdRecyclingComboBox;
    @FXML
    private ComboBox<String> ReportDateComboBox;
//ONLY FOR RecyclingRule
    @FXML
    private ComboBox<String> RecyclingRuleIDComboBox, RecyclingRuleContentComboBox;
//ONLY FOR Recycling
    @FXML
    private ComboBox<String> RecyclingIDComboBox, RecyclingStatusIdComboBox, RecyclingRecyclingRuleIDComboBox, RecyclingIDTrashInfoComboBox;
    @FXML
    private ComboBox<String> RecyclingDateComboBox;
//ONLY FOR RecyclingStatus
    @FXML
    private ComboBox<String> RecyclingStatusIDComboBox, RecyclingStatusNameComboBox, RecyclingStatusContentComboBox;
//ONLY FOR TrashInfo
    @FXML
    private ComboBox<String> TrashInfoIDComboBox, TrashInfoComboBoxIDUser, TrashInfoComboBoxIDTrashType;
    @FXML
    private ComboBox<Integer> TrashInfoComboBoxTrashQuantity;
//ONLY FOR TrashType
    @FXML
    private ComboBox<String> TrashTypeComboBoxID,TrashTypeComboBoxName;
//ONLY FOR User
    @FXML
    private ComboBox<String> UserIDComboBox,UserLoginComboBox, UserNameComboBox, UserSurnameComboBox, UserPasswordComboBox, UserEmailComboBjx;


    private final ForAllEntities forAllEntities = new ForAllEntities(DataBaseConnection.getConnection());
    @FXML
    public void initialize() {
        //Установка данных в таблице
        setupTab1();
        setupTab2();
        setupTab3();
        setupTab4();
        setupTab5();
        setupTab6();
        setupTab7();
        setupTab8();
        setupTab9();
        //Слушатель для переноса строк таблицы
        AdminTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillFieldsFromSelectedRowAdmin(newValue);
            }
        });
        ReportTypeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillFieldsFromSelectedRowReportType(newValue);
            }
        });
        ReportTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillFieldsFromSelectedRowReport(newValue);
            }
        });
        RecyclingRuleTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillFieldsFromSelectedRowRecyclingRule(newValue);
            }
        });
        RecyclingTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillFieldsFromSelectedRowRecycling(newValue);
            }
        });
        RecyclingStatusTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillFieldsFromSelectedRowRecyclingStatus(newValue);
            }
        });
        TrashInfoTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillFieldsFromSelectedRowTrashInfo(newValue);
            }
        });
        TrashTypeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillFieldsFromSelectedRowTrashType(newValue);
            }
        });
        UserTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillFieldsFromSelectedRowUser(newValue);
            }
        });
    }
    //обработчики для добавления строки в таблицу
    @FXML
    private void AddRowToAdmin(){
        if (IDComboBox.getValue() != null && !IDComboBox.getValue().trim().isEmpty()) {
            new ErrorClass().startError("ID генерируется автоматически", "Пожалуйста, очистите ячейку ID для выполнения операции и попробуйте еще раз");
            return;
        }
        try {
            Integer age = Integer.parseInt(AgeComboBox.getEditor().getText());
            Integer birthYear = Integer.parseInt(BirthYearComboBox.getEditor().getText());
            Admin admin = Admin.builder()
                    .login(LoginComboBox.getValue())
                    .name(NameComboBox.getValue())
                    .surname(SurnameComboBox.getValue())
                    .password(PasswordComboBox.getValue())
                    .email(EmailComboBox.getValue())
                    .age(age)
                    .birthYear(birthYear)
                    .build();
            AdminControl adminC = new AdminControl(DataBaseConnection.getConnection());
            boolean res = adminC.insertAdmin(admin);
            if(res){
                new ErrorClass().startSuccess("Успех", "Добавление строки прошло успешно");
                setupTab1();
            }
        } catch (NumberFormatException e) {
            new ErrorClass().startError("Ошибка ввода", "Пожалуйста, введите корректное числовое значение.");
        }catch (SQLException et) {
            System.err.println("Ошибка при добавлении администратора: " + et.getMessage());
        }
    }
    @FXML
    private void AddRowToReportType(){
        if (IDReportTypeComboBox.getValue() != null && !IDReportTypeComboBox.getValue().trim().isEmpty()) {
            new ErrorClass().startError("ID генерируется автоматически", "Пожалуйста, очистите ячейку ID для выполнения операции и попробуйте еще раз");
            return;
        }
        try {
            ReportType reportType = ReportType.builder()
                    .reportTypeName(ReportTypeNameComboBox.getValue())
                    .build();
            ReportTypeControl control = new ReportTypeControl(DataBaseConnection.getConnection());
            boolean res = control.insertReportType(reportType);
            if(res){
                new ErrorClass().startSuccess("Успех", "Добавление строки прошло успешно");
                setupTab2();
            }
        }catch (SQLException et) {
            System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
        }
    }
    @FXML
    private void AddRowToReport(){
        //ReportIDComboBox, ReportTypeIDComboBox, IDAdminComboBox, ReportContentComboBox,ReportIdRecyclingComboBox;
        //@FXML
        //private ComboBox<Date> ReportDateComboBox;
        if (ReportIDComboBox.getValue() != null && !ReportIDComboBox.getValue().trim().isEmpty()) {
            new ErrorClass().startError("ID генерируется автоматически", "Пожалуйста, очистите ячейку ID для выполнения операции и попробуйте еще раз");
            return;
        }
        try {
            java.sql.Date sqlDate = null;
            if (ReportDateComboBox.getValue() != null) {
                sqlDate = java.sql.Date.valueOf(ReportDateComboBox.getValue());
            }
            Report report = Report.builder()
                    .reportTypeId(ReportTypeIDComboBox.getValue())
                    .adminId(IDAdminComboBox.getValue())
                    .content(ReportContentComboBox.getValue())
                    .recyclingId(ReportIdRecyclingComboBox.getValue())
                    .reportDate(sqlDate)
                    .build();
            ReportControl control = new ReportControl(DataBaseConnection.getConnection());
            boolean res = control.insertReport(report);
            if(res){
                new ErrorClass().startSuccess("Успех", "Добавление строки прошло успешно");
                setupTab3();
            }
        }catch (IllegalArgumentException e) {
            new ErrorClass().startError("Ошибка", "Неверный тип данных", "Значение должно соответствовать формату yyyy-MM-dd");
        }catch (SQLException et) {
            System.err.println("Ошибка при добавлении отчета: " + et.getMessage());
        }
    }
    @FXML
    private void AddRowToRecyclingRule(){
    }
    @FXML
    private void AddRowToRecycling(){
    }
    @FXML
    private void AddRowToRecyclingStatus(){
    }
    @FXML
    private void AddRowToRecyclingInfo(){
    }
    @FXML
    private void AddRowToUser(){
    }
    @FXML
    private void AddRowToTrashType(){
    }





    //для метода поиска, главное не забыть вставить его в самом обработчике...
    private boolean flag=false;
    //Обработчики кнопок очищения ячеек
    @FXML
    private void CleanComboBoxTrashType(){
        TrashTypeComboBoxID.setValue(null);
        TrashTypeComboBoxName.setValue(null);
        if(flag){
            flag=false;
            setupTab8();
        }
    }
    @FXML
    private void CleanComboBoxUser(){
        UserIDComboBox.setValue(null);
        UserLoginComboBox.setValue(null);
        UserNameComboBox.setValue(null);
        UserSurnameComboBox.setValue(null);
        UserPasswordComboBox.setValue(null);
        UserEmailComboBjx.setValue(null);
        if(flag){
            flag=false;
            setupTab9();
        }
    }
    @FXML
    private void CleanComboBoxTrashInfo(){
        TrashInfoIDComboBox.setValue(null);
        TrashInfoComboBoxIDUser.setValue(null);
        TrashInfoComboBoxIDTrashType.setValue(null);
        TrashInfoComboBoxTrashQuantity.setValue(null);
        if(flag){
            flag=false;
            setupTab7();
        }
    }
    @FXML
    private void CleanComboBoxRecyclingStatus(){
        RecyclingStatusIDComboBox.setValue(null);
        RecyclingStatusNameComboBox.setValue(null);
        RecyclingStatusContentComboBox.setValue(null);
        if(flag){
            flag=false;
            setupTab6();
        }
    }
    @FXML
    private void CleanComboBoxRecycling(){
        RecyclingIDComboBox.setValue(null);
        RecyclingStatusIdComboBox.setValue(null);
        RecyclingRecyclingRuleIDComboBox.setValue(null);
        RecyclingIDTrashInfoComboBox.setValue(null);
        RecyclingDateComboBox.setValue(null);
        if(flag){
            flag=false;
            setupTab5();
        }
    }
    @FXML
    private void CleanComboBoxRecyclingRule(){
        RecyclingRuleIDComboBox.setValue(null);
        RecyclingRuleContentComboBox.setValue(null);
        if(flag){
            flag=false;
            setupTab4();
        }
    }
    @FXML
    private void CleanComboBoxAdmin(){
        IDComboBox.setValue(null);
        LoginComboBox.setValue(null);
        NameComboBox.setValue(null);
        SurnameComboBox.setValue(null);
        PasswordComboBox.setValue(null);
        EmailComboBox.setValue(null);
        AgeComboBox.setValue(null);
        BirthYearComboBox.setValue(null);
        if(flag){
            flag=false;
            setupTab1();
        }
    }
    @FXML
    private void CleanComboBoxReportType(){
        IDReportTypeComboBox.setValue(null);
        ReportTypeNameComboBox.setValue(null);
        if(flag){
            flag=false;
            setupTab2();
        }
    }
    @FXML
    private void CleanComboBoxReport(){
        ReportIDComboBox.setValue(null);
        ReportTypeIDComboBox.setValue(null);
        IDAdminComboBox.setValue(null);
        ReportContentComboBox.setValue(null);
        ReportIdRecyclingComboBox.setValue(null);
        ReportDateComboBox.setValue(null);
        if(flag){
            flag=false;
            setupTab3();
        }
    }
    //Заполнение ячеек при нажатии на строку таблицы
    private void fillFieldsFromSelectedRowUser(User selected) {
        UserIDComboBox.setValue(selected.getUserId());
        UserLoginComboBox.setValue(selected.getLogin());
        UserNameComboBox.setValue(selected.getName());
        UserSurnameComboBox.setValue(selected.getSurname());
        UserPasswordComboBox.setValue(selected.getPassword());
        UserEmailComboBjx.setValue(selected.getEmail());
    }
    private void fillFieldsFromSelectedRowTrashType(TrashType selected) {
        TrashTypeComboBoxID.setValue(selected.getTrashTypeId());
        TrashTypeComboBoxName.setValue(selected.getTrashTypeName());
    }
    private void fillFieldsFromSelectedRowTrashInfo(TrashInfo selected) {
        TrashInfoIDComboBox.setValue(selected.getTrashInfoId());
        TrashInfoComboBoxIDUser.setValue(selected.getUserId());
        TrashInfoComboBoxIDTrashType.setValue(selected.getTrashTypeId());
        TrashInfoComboBoxTrashQuantity.setValue(selected.getTrashQuantity());
    }
    private void fillFieldsFromSelectedRowRecyclingStatus(RecyclingStatus selected) {
        RecyclingStatusIDComboBox.setValue(selected.getRecyclingStatusId());
        RecyclingStatusNameComboBox.setValue(selected.getRecyclingStatusName());
        RecyclingStatusContentComboBox.setValue(selected.getCurrentProcessDescription());
    }
    private void fillFieldsFromSelectedRowRecycling(Recycling selected) {
        RecyclingIDComboBox.setValue(selected.getRecyclingId());
        RecyclingStatusIdComboBox.setValue(selected.getRecyclingStatusId());
        RecyclingRecyclingRuleIDComboBox.setValue(selected.getRuleId());
        RecyclingIDTrashInfoComboBox.setValue(selected.getTrashInfoId());
        RecyclingDateComboBox.setValue(String.valueOf(selected.getRecyclingDate()));
    }
    private void fillFieldsFromSelectedRowRecyclingRule(RecyclingRule selected) {
        RecyclingRuleIDComboBox.setValue(selected.getRuleId());
        RecyclingRuleContentComboBox.setValue(selected.getContent());
    }
    private void fillFieldsFromSelectedRowReport(Report selected) {
        ReportIDComboBox.setValue(selected.getReportId());
        ReportTypeIDComboBox.setValue(selected.getReportTypeId());
        IDAdminComboBox.setValue(selected.getAdminId());
        ReportContentComboBox.setValue(selected.getContent());
        ReportIdRecyclingComboBox.setValue(selected.getRecyclingId());
        ReportDateComboBox.setValue(String.valueOf(selected.getReportDate()));
    }
    private void fillFieldsFromSelectedRowReportType(ReportType selected) {
        IDReportTypeComboBox.setValue(selected.getReportTypeId());
        ReportTypeNameComboBox.setValue(selected.getReportTypeName());
    }
    private void fillFieldsFromSelectedRowAdmin(Admin selected) {
        IDComboBox.setValue(selected.getAdminId());
        LoginComboBox.setValue(selected.getLogin());
        NameComboBox.setValue(selected.getName());
        SurnameComboBox.setValue(selected.getSurname());
        PasswordComboBox.setValue(selected.getPassword());
        EmailComboBox.setValue(selected.getEmail());
        AgeComboBox.setValue(selected.getAge());
        BirthYearComboBox.setValue(selected.getBirthYear());
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
        for (Admin admin : admins) {
            IDComboBox.getItems().add(admin.getAdminId());
            LoginComboBox.getItems().add(admin.getLogin());
            if(!admin.getName().isEmpty())NameComboBox.getItems().add(admin.getName());
            if(!admin.getSurname().isEmpty())SurnameComboBox.getItems().add(admin.getSurname());
            PasswordComboBox.getItems().add(admin.getPassword());
            if(!admin.getEmail().isEmpty())EmailComboBox.getItems().add(admin.getEmail());
            AgeComboBox.getItems().add(admin.getAge());
            BirthYearComboBox.getItems().add(admin.getBirthYear());
        }
    }

    private void setupTab2() {
        @SuppressWarnings("unchecked")
        List<ReportType> rows = (List<ReportType>) forAllEntities.getAllRows("report_type");
        ObservableList<ReportType> observableList = FXCollections.observableArrayList(rows);
        ReportType1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReportTypeId()));
        ReportType2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReportTypeName()));
        ReportTypeTableView.setItems(observableList);
        for (ReportType row : rows) {
            IDReportTypeComboBox.getItems().add(row.getReportTypeId());
            ReportTypeNameComboBox.getItems().add(row.getReportTypeName());
        }
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
        for (Report row : rows) {
            ReportIDComboBox.getItems().add(row.getReportId());
            ReportTypeIDComboBox.getItems().add(row.getReportTypeId());
            IDAdminComboBox.getItems().add(row.getAdminId());
            ReportContentComboBox.getItems().add(row.getContent());
            ReportIdRecyclingComboBox.getItems().add(row.getRecyclingId());
            ReportDateComboBox.getItems().add(String.valueOf(row.getReportDate()));
        }
    }
    private void setupTab4() {
        @SuppressWarnings("unchecked")
        List<RecyclingRule> rows = (List<RecyclingRule>) forAllEntities.getAllRows("recycling_rule");
        ObservableList<RecyclingRule> observableList = FXCollections.observableArrayList(rows);
        RecyclingRule1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRuleId()));
        RecyclingRule2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContent()));
        RecyclingRuleTableView.setItems(observableList);
        for (RecyclingRule row : rows) {
            RecyclingRuleIDComboBox.getItems().add(row.getRuleId());
            RecyclingRuleContentComboBox.getItems().add(row.getContent());
        }
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
        for (Recycling row : rows) {
            RecyclingIDComboBox.getItems().add(row.getRecyclingId());
            RecyclingStatusIdComboBox.getItems().add(row.getRecyclingStatusId());
            RecyclingRecyclingRuleIDComboBox.getItems().add(row.getRuleId());
            RecyclingIDTrashInfoComboBox.getItems().add(row.getTrashInfoId());
            RecyclingDateComboBox.getItems().add(String.valueOf(row.getRecyclingDate()));

        }
    }
    private void setupTab6() {
        @SuppressWarnings("unchecked")
        List<RecyclingStatus> rows = (List<RecyclingStatus>) forAllEntities.getAllRows("recycling_status");
        ObservableList<RecyclingStatus> observableList = FXCollections.observableArrayList(rows);
        RecyclingStatus1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingStatusId()));
        RecyclingStatus2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingStatusName()));
        RecyclingStatus3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurrentProcessDescription()));

        RecyclingStatusTableView.setItems(observableList);
        for (RecyclingStatus row : rows) {
            RecyclingStatusIDComboBox.getItems().add(row.getRecyclingStatusId());
            RecyclingStatusNameComboBox.getItems().add(row.getRecyclingStatusName());
            RecyclingStatusContentComboBox.getItems().add(row.getCurrentProcessDescription());
        }
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
        for (TrashInfo row : rows) {
            TrashInfoIDComboBox.getItems().add(row.getTrashInfoId());
            TrashInfoComboBoxIDUser.getItems().add(row.getUserId());
            TrashInfoComboBoxIDTrashType.getItems().add(row.getTrashTypeId());
            TrashInfoComboBoxTrashQuantity.getItems().add(row.getTrashQuantity());
        }
    }
    private void setupTab8() {
        @SuppressWarnings("unchecked")
        List<TrashType> rows = (List<TrashType>) forAllEntities.getAllRows("trash_type");
        ObservableList<TrashType> observableList = FXCollections.observableArrayList(rows);
        TrashType1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashTypeId()));
        TrashType2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashTypeName()));

        TrashTypeTableView.setItems(observableList);
        for (TrashType row : rows) {
            TrashTypeComboBoxID.getItems().add(row.getTrashTypeId());
            TrashTypeComboBoxName.getItems().add(row.getTrashTypeName());
        }
    }
    private void setupTab9() {
        @SuppressWarnings("unchecked")
        List<User> rows = (List<User>) forAllEntities.getAllRows("users");
        ObservableList<User> adminObservableList = FXCollections.observableArrayList(rows);
        User1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserId()));
        User2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));
        User3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        User4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        User5.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        User6.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        UserTableView.setItems(adminObservableList);
        for (User row : rows) {
            UserIDComboBox.getItems().add(row.getUserId());
            UserLoginComboBox.getItems().add(row.getLogin());
            if(!row.getName().isEmpty())UserNameComboBox.getItems().add(row.getName());
            if(!row.getSurname().isEmpty())UserSurnameComboBox.getItems().add(row.getSurname());
            UserPasswordComboBox.getItems().add(row.getPassword());
            if(!row.getEmail().isEmpty())UserEmailComboBjx.getItems().add(row.getEmail());
        }
    }
}
