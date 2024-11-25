package org.example.postgresql_project.FXControllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.postgresql_project.DataBaseConnection;
import org.example.postgresql_project.Entities.*;
import org.example.postgresql_project.EntitiesControl.*;
import org.example.postgresql_project.ErrorClass;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

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
    private TabPane mainTabPane;


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
        setupTab1();setupTab2();setupTab3();
        setupTab4();setupTab5();setupTab6();
        setupTab7();setupTab8();setupTab9();
        mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(AdminTab)){
                setupTab1();CleanComboBoxAdmin();
            } else if (newValue.equals(ReportTypeTab)) {
                setupTab2();CleanComboBoxReportType();
            } else if (newValue.equals(ReportTab)) {
                setupTab3();CleanComboBoxReport();
            } else if (newValue.equals(RecyclingRuleTab)) {
                setupTab4();CleanComboBoxRecyclingRule();
            } else if (newValue.equals(RecyclingTab)) {
                setupTab5();CleanComboBoxRecycling();
            }else if (newValue.equals(RecyclingStatusTab)) {
                setupTab6();CleanComboBoxRecyclingStatus();
            }else if (newValue.equals(TrashInfoTab)) {
                setupTab7();CleanComboBoxTrashInfo();
            }else if (newValue.equals(TrashTypeTab)) {
                setupTab8();CleanComboBoxTrashType();
            }else if (newValue.equals(UserTab)) {
                setupTab9();CleanComboBoxUser();
            }
        });
        //Слушатель для переноса строки из таблички кек
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
    //обработчики для поиска
    @FXML
    private void findAdmin(){
        try {
            Map<String, Object> params = new HashMap<>();
            if (IDComboBox.getValue() != null && !IDComboBox.getValue().isEmpty()) {
                params.put("admin_id", IDComboBox.getValue());
            }if (LoginComboBox.getValue() != null && !LoginComboBox.getValue().isEmpty()) {
                params.put("login", LoginComboBox.getValue());
            }if (NameComboBox.getValue() != null && !NameComboBox.getValue().isEmpty()) {
                params.put("name", NameComboBox.getValue());
            }if (SurnameComboBox.getValue() != null && !SurnameComboBox.getValue().isEmpty()) {
                params.put("surname", SurnameComboBox.getValue());
            }if (PasswordComboBox.getValue() != null && !PasswordComboBox.getValue().isEmpty()) {
                params.put("password", PasswordComboBox.getValue());
            }if (EmailComboBox.getValue() != null && !EmailComboBox.getValue().isEmpty()) {
                params.put("email", EmailComboBox.getValue());
            }if (AgeComboBox.getValue() != null && !AgeComboBox.getEditor().getText().isEmpty()) {
                params.put("age", Integer.parseInt(AgeComboBox.getEditor().getText()));
            }if (BirthYearComboBox.getValue() != null && !BirthYearComboBox.getEditor().getText().isEmpty()) {
                params.put("birth_year", Integer.parseInt(BirthYearComboBox.getEditor().getText()));}
            if (params.isEmpty()) {
                new ErrorClass().startError("Ошибка", "Укажите хотя бы один параметр для поиска.");
                return;}
            AdminControl adminControl = new AdminControl(DataBaseConnection.getConnection());
            List<Admin> admins = adminControl.searchAdminByParameters(params);
            if (!admins.isEmpty()) {
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
                new ErrorClass().startSuccess("Успех", "Найдены " + admins.size() + " запись(и).");
            } else {
                new ErrorClass().startError("Результат", "Записи не найдены.");}
        } catch (NumberFormatException e) {
            new ErrorClass().startError("Ошибка", "Пожалуйста, введите корректные числовые значения для возраста или года рождения.");
        }flag=true;
    }
    @FXML
    private void findReportType(){
        Map<String, Object> params = new HashMap<>();
        if (IDReportTypeComboBox.getValue() != null && !IDReportTypeComboBox.getValue().isEmpty()) {
            params.put("report_type_id", IDReportTypeComboBox.getValue());
        }if (ReportTypeNameComboBox.getValue() != null && !ReportTypeNameComboBox.getValue().isEmpty()) {
            params.put("report_type_name", ReportTypeNameComboBox.getValue());
        }if (params.isEmpty()) {
            new ErrorClass().startError("Ошибка", "Укажите хотя бы один параметр для поиска.");
            return;}
        ReportTypeControl reportTypeControl = new ReportTypeControl(DataBaseConnection.getConnection());
        List<ReportType> reportTypes = reportTypeControl.searchReportTypeByParameters(params);
        if (!reportTypes.isEmpty()) {
            ObservableList<ReportType> observableList = FXCollections.observableArrayList(reportTypes);
            ReportType1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReportTypeId()));
            ReportType2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReportTypeName()));
            ReportTypeTableView.setItems(observableList);
            new ErrorClass().startSuccess("Успех", "Найдены " + reportTypes.size() + " запись(и/ей).");
        } else {
            new ErrorClass().startError("Результат", "Записи не найдены.");
        }flag=true;
    }@FXML
    private void findReport(){
        Map<String, Object> params = new HashMap<>();
        if (ReportIDComboBox.getValue() != null && !ReportIDComboBox.getValue().isEmpty()) {
            params.put("report_id", ReportIDComboBox.getValue());
        }if (ReportTypeIDComboBox.getValue() != null && !ReportTypeIDComboBox.getValue().isEmpty()) {
            params.put("report_type_id", ReportTypeIDComboBox.getValue());
        }if (IDAdminComboBox.getValue() != null && !IDAdminComboBox.getValue().isEmpty()) {
            params.put("admin_id", IDAdminComboBox.getValue());
        }if (ReportContentComboBox.getValue() != null && !ReportContentComboBox.getValue().isEmpty()) {
            params.put("content", ReportContentComboBox.getValue());
        }if (ReportIdRecyclingComboBox.getValue() != null && !ReportIdRecyclingComboBox.getValue().isEmpty()) {
            params.put("recycling_id", ReportIdRecyclingComboBox.getValue());
        }if (ReportDateComboBox.getValue() != null && !ReportDateComboBox.getValue().isEmpty()) {
            try {
                java.sql.Date date = java.sql.Date.valueOf(ReportDateComboBox.getValue());
                params.put("report_date", date);
            } catch (IllegalArgumentException e) {
                new ErrorClass().startError("Ошибка", "Неверный формат даты", "Значение должно соответствовать формату yyyy-MM-dd");
                return;
            }
        }if (params.isEmpty()) {
            new ErrorClass().startError("Ошибка", "Укажите хотя бы один параметр для поиска.");
            return;}
        ReportControl reportControl = new ReportControl(DataBaseConnection.getConnection());
        List<Report> reports = reportControl.searchReportByParameters(params);
        if (!reports.isEmpty()) {
            ObservableList<Report> observableList = FXCollections.observableArrayList(reports);
            Report1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReportId()));
            Report2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReportTypeId()));
            Report3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdminId()));
            Report4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContent()));
            Report5.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getReportDate()));
            Report6.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingId()));
            ReportTableView.setItems(observableList);
            new ErrorClass().startSuccess("Успех", "Найдены " + reports.size() + " отчет(а/ов).");
        } else {
            new ErrorClass().startError("Результат", "Записи не найдены.");
        }flag=true;
    }@FXML
    private void findRecyclingRule(){
        Map<String, Object> params = new HashMap<>();
        if (RecyclingRuleContentComboBox.getValue() != null && !RecyclingRuleContentComboBox.getValue().isEmpty()) {
            params.put("content", RecyclingRuleContentComboBox.getValue());
        }if (RecyclingRuleIDComboBox.getValue() != null && !RecyclingRuleIDComboBox.getValue().isEmpty()) {
            params.put("rule_id", RecyclingRuleIDComboBox.getValue());
        }if (params.isEmpty()) {
            new ErrorClass().startError("Ошибка", "Укажите хотя бы один параметр для поиска.");
            return;}
        RecyclingRuleControl recyclingRuleControl = new RecyclingRuleControl(DataBaseConnection.getConnection());
        List<RecyclingRule> recyclingRules = recyclingRuleControl.searchRecyclingRuleByParameters(params);
        if (!recyclingRules.isEmpty()) {
            ObservableList<RecyclingRule> observableList = FXCollections.observableArrayList(recyclingRules);
            RecyclingRule1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRuleId()));
            RecyclingRule2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContent()));
            RecyclingRuleTableView.setItems(observableList);
            new ErrorClass().startSuccess("Успех", "Найдены " + recyclingRules.size() + " правило(а/ов).");
        } else {
            new ErrorClass().startError("Результат", "Записи не найдены.");
        }flag=true;
    }@FXML
    private void findRecycling(){
        Map<String, Object> params = new HashMap<>();
        if (RecyclingIDComboBox.getValue() != null && !RecyclingIDComboBox.getValue().isEmpty()) {
            params.put("recycling_id", RecyclingIDComboBox.getValue());
        }if (RecyclingStatusIdComboBox.getValue() != null && !RecyclingStatusIdComboBox.getValue().isEmpty()) {
            params.put("recycling_status_id", RecyclingStatusIdComboBox.getValue());
        }if (RecyclingRecyclingRuleIDComboBox.getValue() != null && !RecyclingRecyclingRuleIDComboBox.getValue().isEmpty()) {
            params.put("rule_id", RecyclingRecyclingRuleIDComboBox.getValue());
        }if (RecyclingIDTrashInfoComboBox.getValue() != null && !RecyclingIDTrashInfoComboBox.getValue().isEmpty()) {
            params.put("trash_info_id", RecyclingIDTrashInfoComboBox.getValue());
        }if (RecyclingDateComboBox.getValue() != null && !RecyclingDateComboBox.getValue().isEmpty()) {
            try {
                java.sql.Date date = java.sql.Date.valueOf(RecyclingDateComboBox.getValue());
                params.put("recycling_date", date);
            } catch (IllegalArgumentException e) {
                new ErrorClass().startError("Ошибка", "Неверный формат даты", "Значение должно соответствовать формату yyyy-MM-dd");
                return;
            }
        }if (params.isEmpty()) {
            new ErrorClass().startError("Ошибка", "Укажите хотя бы один параметр для поиска.");
            return;}
        RecyclingControl recyclingControl = new RecyclingControl(DataBaseConnection.getConnection());
        List<Recycling> recyclings = recyclingControl.searchRecyclingByParameters(params);
        if (!recyclings.isEmpty()) {
            ObservableList<Recycling> observableList = FXCollections.observableArrayList(recyclings);
            Recycling1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingId()));
            Recycling2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingStatusId()));
            Recycling3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRuleId()));
            Recycling4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashInfoId()));
            Recycling5.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRecyclingDate()));
            RecyclingTableView.setItems(observableList);
            new ErrorClass().startSuccess("Успех", "Найдено " + recyclings.size() + " строк(и)");
        } else {
            new ErrorClass().startError("Результат", "Записи не найдены.");
        }flag=true;
    }@FXML
    private void findRecyclingStatus(){
        Map<String, Object> params = new HashMap<>();
        if (RecyclingStatusIDComboBox.getValue() != null && !RecyclingStatusIDComboBox.getValue().isEmpty()) {
            params.put("recycling_status_id", RecyclingStatusIDComboBox.getValue());
        }if (RecyclingStatusNameComboBox.getValue() != null && !RecyclingStatusNameComboBox.getValue().isEmpty()) {
            params.put("recycling_status_name", RecyclingStatusNameComboBox.getValue());
        }if (RecyclingStatusContentComboBox.getValue() != null && !RecyclingStatusContentComboBox.getValue().isEmpty()) {
            params.put("current_process_description", RecyclingStatusContentComboBox.getValue());
        }if (params.isEmpty()) {
            new ErrorClass().startError("Ошибка", "Укажите хотя бы один параметр для поиска.");
            return;}
        RecyclingStatusControl recyclingStatusControl = new RecyclingStatusControl(DataBaseConnection.getConnection());
        List<RecyclingStatus> recyclingStatuses = recyclingStatusControl.searchRecyclingStatusByParameters(params);
        if (!recyclingStatuses.isEmpty()) {
            ObservableList<RecyclingStatus> observableList = FXCollections.observableArrayList(recyclingStatuses);
            RecyclingStatus1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingStatusId()));
            RecyclingStatus2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingStatusName()));
            RecyclingStatus3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurrentProcessDescription()));
            RecyclingStatusTableView.setItems(observableList);
            new ErrorClass().startSuccess("Успех", "Найдено " + recyclingStatuses.size() + " статус(а/ов) переработки.");
        } else {
            new ErrorClass().startError("Результат", "Записи не найдены.");
        }flag=true;
    }@FXML
    private void findTrashInfo(){
        try {
            Map<String, Object> params = new HashMap<>();
            if (TrashInfoIDComboBox.getValue() != null && !TrashInfoIDComboBox.getValue().isEmpty()) {
                params.put("trash_info_id", TrashInfoIDComboBox.getValue());
            }if (TrashInfoComboBoxIDUser.getValue() != null && !TrashInfoComboBoxIDUser.getValue().isEmpty()) {
                params.put("user_id", TrashInfoComboBoxIDUser.getValue());
            }if (TrashInfoComboBoxIDTrashType.getValue() != null && !TrashInfoComboBoxIDTrashType.getValue().isEmpty()) {
                params.put("trash_type_id", TrashInfoComboBoxIDTrashType.getValue());
            }if (TrashInfoComboBoxTrashQuantity.getValue() != null && !TrashInfoComboBoxTrashQuantity.getEditor().getText().isEmpty()) {
                params.put("trash_quantity", Integer.parseInt(TrashInfoComboBoxTrashQuantity.getEditor().getText()));
            }if (params.isEmpty()) {
                new ErrorClass().startError("Ошибка", "Укажите хотя бы один параметр для поиска.");
                return;}
            TrashInfoControl trashInfoControl = new TrashInfoControl(DataBaseConnection.getConnection());
            List<TrashInfo> trashInfos = trashInfoControl.searchTrashInfoByParameters(params);
            if (!trashInfos.isEmpty()) {
                ObservableList<TrashInfo> observableList = FXCollections.observableArrayList(trashInfos);
                TrashInfo1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashInfoId()));
                TrashInfo2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserId()));
                TrashInfo3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashTypeId()));
                TrashInfo4.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTrashQuantity()));
                TrashInfoTableView.setItems(observableList);
                new ErrorClass().startSuccess("Успех", "Найдены " + trashInfos.size() + " запись(и/ей).");
            } else {
                new ErrorClass().startError("Результат", "Записи не найдены.");
            }
        } catch (NumberFormatException e) {
            new ErrorClass().startError("Ошибка", "Пожалуйста, введите корректные числовые значения для возраста или года рождения.");
        }flag=true;
    }@FXML
    private void findTrashType(){
        Map<String, Object> params = new HashMap<>();
        if (TrashTypeComboBoxID.getValue() != null && !TrashTypeComboBoxID.getValue().isEmpty()) {
            params.put("trash_type_id", TrashTypeComboBoxID.getValue());
        }if (TrashTypeComboBoxName.getValue() != null && !TrashTypeComboBoxName.getValue().isEmpty()) {
            params.put("trash_type_name", TrashTypeComboBoxName.getValue());}
        if (params.isEmpty()) {
            new ErrorClass().startError("Ошибка", "Укажите хотя бы один параметр для поиска.");
            return;}
        TrashTypeControl trashTypeControl = new TrashTypeControl(DataBaseConnection.getConnection());
        List<TrashType> trashTypes = trashTypeControl.searchTrashTypeByParameters(params);
        if (!trashTypes.isEmpty()) {
            ObservableList<TrashType> observableList = FXCollections.observableArrayList(trashTypes);
            TrashType1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashTypeId()));
            TrashType2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashTypeName()));
            TrashTypeTableView.setItems(observableList);
            new ErrorClass().startSuccess("Успех", "Найдено " + trashTypes.size() + " тип(а/ов) мусора.");
        } else {
            new ErrorClass().startError("Результат", "Записи не найдены.");
        }flag=true;
    }@FXML
    private void findUser(){
        Map<String, Object> params = new HashMap<>();
        if (UserIDComboBox.getValue() != null && !UserIDComboBox.getValue().isEmpty()) {
            params.put("user_id", UserIDComboBox.getValue());
        }if (UserLoginComboBox.getValue() != null && !UserLoginComboBox.getValue().isEmpty()) {
            params.put("login", UserLoginComboBox.getValue());
        }if (UserNameComboBox.getValue() != null && !UserNameComboBox.getValue().isEmpty()) {
            params.put("name", UserNameComboBox.getValue());
        }if (UserSurnameComboBox.getValue() != null && !UserSurnameComboBox.getValue().isEmpty()) {
            params.put("surname", UserSurnameComboBox.getValue());
        }if (UserPasswordComboBox.getValue() != null && !UserPasswordComboBox.getValue().isEmpty()) {
            params.put("password", UserPasswordComboBox.getValue());
        }if (UserEmailComboBjx.getValue() != null && !UserEmailComboBjx.getValue().isEmpty()) {
            params.put("email", UserEmailComboBjx.getValue());
        }if (params.isEmpty()) {
            new ErrorClass().startError("Ошибка", "Укажите хотя бы один параметр для поиска.");
            return;}
        UserControl userControl = new UserControl(DataBaseConnection.getConnection());
        List<User> users = userControl.searchUserByParameters(params);
        if (!users.isEmpty()) {
            ObservableList<User> adminObservableList = FXCollections.observableArrayList(users);
            User1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserId()));
            User2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));
            User3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            User4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
            User5.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
            User6.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
            UserTableView.setItems(adminObservableList);
            new ErrorClass().startSuccess("Успех", "Найдено " + users.size() + " пользователь(я/ей).");
        } else {
            new ErrorClass().startError("Результат", "Записи не найдены.");
        }flag=true;
    }

















    //обработчики для добавления строки в таблицу
    @FXML
    private void AddRowToAdmin(){
        if (IDComboBox.getValue() != null && !IDComboBox.getValue().trim().isEmpty()) {
            new ErrorClass().startError("ID генерируется автоматически", "Пожалуйста, очистите ячейку ID для выполнения операции и попробуйте еще раз");
            return;
        }
        try {
            int age = Integer.parseInt(AgeComboBox.getEditor().getText());
            int birthYear = Integer.parseInt(BirthYearComboBox.getEditor().getText());
            if(age<1 || birthYear<1){
                new ErrorClass().startError("Ошибка", "Год рождения и возраст не могут быть меньше 1");
                return;
            }
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
                setupTab1();IDAdminComboBox.getItems().add(admin.getAdminId());
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
                setupTab2();ReportTypeIDComboBox.getItems().add(reportType.getReportTypeId());
            }
        }catch (SQLException et) {
            System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
        }
    }
    @FXML
    private void AddRowToReport(){
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
            System.err.println("Ошибка при добавлении отчета: " + et.getMessage()); //0b344983-e87a-48c8-bdb9-450bb7a6bcfd 2a994fab-7b4b-416a-bd59-69ef191c3b98 1f4ca7d4-9d13-44cc-82b5-32006a00bae3
        }
    }
    @FXML
    private void AddRowToRecyclingRule(){//RecyclingRecyclingRuleIDComboBox.getItems().add(row.getRuleId());
        if (RecyclingRuleIDComboBox.getValue() != null && !RecyclingRuleIDComboBox.getValue().trim().isEmpty()) {
            new ErrorClass().startError("ID генерируется автоматически", "Пожалуйста, очистите ячейку ID для выполнения операции и попробуйте еще раз");
            return;
        }
        try {
            RecyclingRule recyclingRule = RecyclingRule.builder()
                    .content(RecyclingRuleContentComboBox.getValue())
                    .build();
            RecyclingRuleControl control = new RecyclingRuleControl(DataBaseConnection.getConnection());
            boolean res = control.insertRecyclingRule(recyclingRule);
            if(res){
                new ErrorClass().startSuccess("Успех", "Добавление строки прошло успешно");
                setupTab4();RecyclingRecyclingRuleIDComboBox.getItems().add(recyclingRule.getRuleId());
            }
        }catch (SQLException et) {
            System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
        }
    }
    @FXML
    private void AddRowToRecycling(){//ReportIdRecyclingComboBox.getItems().add(row.getRecyclingId());
        if (RecyclingIDComboBox.getValue() != null && !RecyclingIDComboBox.getValue().trim().isEmpty()) {
            new ErrorClass().startError("ID генерируется автоматически", "Пожалуйста, очистите ячейку ID для выполнения операции и попробуйте еще раз");
            return;
        }
        try {
            java.sql.Date sqlDate = null;
            if (RecyclingDateComboBox.getValue() != null) {
                sqlDate = java.sql.Date.valueOf(RecyclingDateComboBox.getValue());
            }
            Recycling recycling = Recycling.builder()
                    .recyclingStatusId(RecyclingStatusIdComboBox.getValue())
                    .ruleId(RecyclingRecyclingRuleIDComboBox.getValue())
                    .trashInfoId(RecyclingIDTrashInfoComboBox.getValue())
                    .recyclingDate(sqlDate)
                    .build();
            RecyclingControl control = new RecyclingControl(DataBaseConnection.getConnection());
            boolean res = control.insertRecycling(recycling);
            if(res){
                new ErrorClass().startSuccess("Успех", "Добавление строки прошло успешно");
                setupTab5();ReportIdRecyclingComboBox.getItems().add(recycling.getRecyclingId());
            }
        }catch (IllegalArgumentException e) {
            new ErrorClass().startError("Ошибка", "Неверный тип данных", "Значение должно соответствовать формату yyyy-MM-dd");
        }catch (SQLException et) {
            System.err.println("Ошибка при добавлении отчета: " + et.getMessage());
        }
    }
    @FXML
    private void AddRowToRecyclingStatus(){//RecyclingStatusIdComboBox.getItems().add(row.getRecyclingStatusId());
        //ComboBox<String> RecyclingStatusIDComboBox, RecyclingStatusNameComboBox, RecyclingStatusContentComboBox;
        if (RecyclingStatusIDComboBox.getValue() != null && !RecyclingStatusIDComboBox.getValue().trim().isEmpty()) {
            new ErrorClass().startError("ID генерируется автоматически", "Пожалуйста, очистите ячейку ID для выполнения операции и попробуйте еще раз");
            return;
        }
        try {
            RecyclingStatus recyclingStatus = RecyclingStatus.builder()
                    .recyclingStatusName(RecyclingStatusNameComboBox.getValue())
                    .currentProcessDescription(RecyclingStatusContentComboBox.getValue())
                    .build();
            RecyclingStatusControl control = new RecyclingStatusControl(DataBaseConnection.getConnection());
            boolean res = control.insertRecyclingStatus(recyclingStatus);
            if(res){
                new ErrorClass().startSuccess("Успех", "Добавление строки прошло успешно");
                setupTab6();RecyclingStatusIdComboBox.getItems().add(recyclingStatus.getRecyclingStatusId());
            }
        }catch (SQLException et) {
            System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
        }
    }
    @FXML
    private void AddRowToTrashInfo(){//RecyclingIDTrashInfoComboBox.getItems().add(row.getTrashInfoId());
        //ComboBox<String> TrashInfoIDComboBox, TrashInfoComboBoxIDUser, TrashInfoComboBoxIDTrashType;
        //    @FXML
        //    private ComboBox<Integer> TrashInfoComboBoxTrashQuantity;
        if (TrashInfoIDComboBox.getValue() != null && !TrashInfoIDComboBox.getValue().trim().isEmpty()) {
            new ErrorClass().startError("ID генерируется автоматически", "Пожалуйста, очистите ячейку ID для выполнения операции и попробуйте еще раз");
            return;
        }
        try {
            int quantity = Integer.parseInt(TrashInfoComboBoxTrashQuantity.getEditor().getText());
            if(quantity<1){
                new ErrorClass().startError("Ошибка", "Количество мусора не может быть меньше 1");
                return;
            }
            TrashInfo trashInfo = TrashInfo.builder()
                    .userId(TrashInfoComboBoxIDUser.getValue())
                    .trashTypeId(TrashInfoComboBoxIDTrashType.getValue())
                    .trashQuantity(quantity)
                    .build();
            TrashInfoControl control = new TrashInfoControl(DataBaseConnection.getConnection());
            boolean res = control.insertTrashInfo(trashInfo);
            if(res){
                new ErrorClass().startSuccess("Успех", "Добавление строки прошло успешно");
                setupTab7();RecyclingIDTrashInfoComboBox.getItems().add(trashInfo.getTrashInfoId());
            }
        } catch (NumberFormatException e) {
            new ErrorClass().startError("Ошибка ввода", "Пожалуйста, введите корректное числовое значение.");
        }catch (SQLException et) {
            System.err.println("Ошибка при добавлении администратора: " + et.getMessage());
        }

    }
    @FXML
    private void AddRowToUser(){//TrashInfoComboBoxIDUser.getItems().add(row.getUserId());
        //UserIDComboBox,UserLoginComboBox, UserNameComboBox, UserSurnameComboBox, UserPasswordComboBox, UserEmailComboBjx;
        if (UserIDComboBox.getValue() != null && !UserIDComboBox.getValue().trim().isEmpty()) {
            new ErrorClass().startError("ID генерируется автоматически", "Пожалуйста, очистите ячейку ID для выполнения операции и попробуйте еще раз");
            return;
        }
        try {
            User user1 = User.builder()
                    .login(UserLoginComboBox.getValue())
                    .name(UserNameComboBox.getValue())
                    .surname(UserSurnameComboBox.getValue())
                    .password(UserPasswordComboBox.getValue())
                    .email(UserEmailComboBjx.getValue())
                    .build();
            UserControl control = new UserControl(DataBaseConnection.getConnection());
            boolean res = control.insertUser(user1);
            if(res){
                new ErrorClass().startSuccess("Успех", "Добавление строки прошло успешно");
                setupTab9();TrashInfoComboBoxIDUser.getItems().add(user1.getUserId());
            }
        }catch (SQLException et) {
            System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
        }
    }
    @FXML
    private void AddRowToTrashType(){//TrashInfoComboBoxIDTrashType.getItems().add(row.getTrashTypeId());
        //TrashTypeComboBoxID,TrashTypeComboBoxName;
        if (TrashTypeComboBoxID.getValue() != null && !TrashTypeComboBoxID.getValue().trim().isEmpty()) {
            new ErrorClass().startError("ID генерируется автоматически", "Пожалуйста, очистите ячейку ID для выполнения операции и попробуйте еще раз");
            return;
        }
        try {
            TrashType trashType = TrashType.builder()
                    .trashTypeName(TrashTypeComboBoxName.getValue())
                    .build();
            TrashTypeControl control = new TrashTypeControl(DataBaseConnection.getConnection());
            boolean res = control.insertReportType(trashType);
            if(res){
                new ErrorClass().startSuccess("Успех", "Добавление строки прошло успешно");
                setupTab8();TrashInfoComboBoxIDTrashType.getItems().add(trashType.getTrashTypeId());
            }
        }catch (SQLException et) {
            System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
        }
    }


    //для метода поиска, главное не забыть вставить его в самом обработчике...
    private boolean flag=false;
    //Обработчики кнопок очищения ячеек
    @FXML
    private void CleanComboBoxTrashType(){
        if(flag){
            flag=false;
            setupTab8();
        }
        TrashTypeComboBoxID.setValue(null);
        TrashTypeComboBoxName.setValue(null);
    }
    @FXML
    private void CleanComboBoxUser(){
        if(flag){
            flag=false;
            setupTab9();
        }
        UserIDComboBox.setValue(null);
        UserLoginComboBox.setValue(null);
        UserNameComboBox.setValue(null);
        UserSurnameComboBox.setValue(null);
        UserPasswordComboBox.setValue(null);
        UserEmailComboBjx.setValue(null);
    }
    @FXML
    private void CleanComboBoxTrashInfo(){
        if(flag){
            flag=false;
            setupTab7();
        }
        TrashInfoIDComboBox.setValue(null);
        TrashInfoComboBoxIDUser.setValue(null);
        TrashInfoComboBoxIDTrashType.setValue(null);
        TrashInfoComboBoxTrashQuantity.setValue(null);
    }
    @FXML
    private void CleanComboBoxRecyclingStatus(){
        if(flag){
            flag=false;
            setupTab6();
        }
        RecyclingStatusIDComboBox.setValue(null);
        RecyclingStatusNameComboBox.setValue(null);
        RecyclingStatusContentComboBox.setValue(null);
    }
    @FXML
    private void CleanComboBoxRecycling(){
        if(flag){
            flag=false;
            setupTab5();
        }
        RecyclingIDComboBox.setValue(null);
        RecyclingStatusIdComboBox.setValue(null);
        RecyclingRecyclingRuleIDComboBox.setValue(null);
        RecyclingIDTrashInfoComboBox.setValue(null);
        RecyclingDateComboBox.setValue(null);
    }
    @FXML
    private void CleanComboBoxRecyclingRule(){
        if(flag){
            flag=false;
            setupTab4();
        }
        RecyclingRuleIDComboBox.setValue(null);
        RecyclingRuleContentComboBox.setValue(null);
    }
    @FXML
    private void CleanComboBoxAdmin(){
        if(flag){
            flag=false;
            setupTab1();
        }
        IDComboBox.setValue(null);
        LoginComboBox.setValue(null);
        NameComboBox.setValue(null);
        SurnameComboBox.setValue(null);
        PasswordComboBox.setValue(null);
        EmailComboBox.setValue(null);
        AgeComboBox.setValue(null);
        BirthYearComboBox.setValue(null);
    }
    @FXML
    private void CleanComboBoxReportType(){
        if(flag){
            flag=false;
            setupTab2();
        }
        IDReportTypeComboBox.setValue(null);
        ReportTypeNameComboBox.setValue(null);
    }
    @FXML
    private void CleanComboBoxReport(){
        if(flag){
            flag=false;
            setupTab3();
        }
        ReportIDComboBox.setValue(null);
        ReportTypeIDComboBox.setValue(null);
        IDAdminComboBox.setValue(null);
        ReportContentComboBox.setValue(null);
        ReportIdRecyclingComboBox.setValue(null);
        ReportDateComboBox.setValue(null);
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
    }private boolean f1=false;
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
        if(f1)return;
        Set<String> uniqueNames = new HashSet<>();
        Set<String> uniqueSurnames = new HashSet<>();
        Set<String> uniquePasswords = new HashSet<>();
        Set<String> uniqueEmails = new HashSet<>();
        Set<Integer> uniqueAges = new HashSet<>();
        Set<Integer> uniqueBirthYears = new HashSet<>();
        for (Admin admin : admins) {
            IDComboBox.getItems().add(admin.getAdminId());
            if (admin.getName() != null && !admin.getName().isEmpty()) uniqueNames.add(admin.getName());
            if (admin.getSurname() != null && !admin.getSurname().isEmpty()) uniqueSurnames.add(admin.getSurname());
            uniquePasswords.add(admin.getPassword());
            if (admin.getEmail() != null && !admin.getEmail().isEmpty()) uniqueEmails.add(admin.getEmail());
            uniqueAges.add(admin.getAge());
            uniqueBirthYears.add(admin.getBirthYear());
            LoginComboBox.getItems().add(admin.getLogin());
            IDAdminComboBox.getItems().add(admin.getAdminId());
        }
        NameComboBox.getItems().setAll(uniqueNames);
        SurnameComboBox.getItems().setAll(uniqueSurnames);
        PasswordComboBox.getItems().setAll(uniquePasswords);
        EmailComboBox.getItems().setAll(uniqueEmails);
        AgeComboBox.getItems().setAll(uniqueAges);
        BirthYearComboBox.getItems().setAll(uniqueBirthYears);
        /*for (Admin admin : admins) {
            IDComboBox.getItems().add(admin.getAdminId());
            LoginComboBox.getItems().add(admin.getLogin());
            if(admin.getName() != null && !admin.getName().isEmpty())NameComboBox.getItems().add(admin.getName());
            if(admin.getSurname() != null && !admin.getSurname().isEmpty())SurnameComboBox.getItems().add(admin.getSurname());
            PasswordComboBox.getItems().add(admin.getPassword());
            if(admin.getEmail() != null && !admin.getEmail().isEmpty())EmailComboBox.getItems().add(admin.getEmail());
            AgeComboBox.getItems().add(admin.getAge());
            BirthYearComboBox.getItems().add(admin.getBirthYear());
            IDAdminComboBox.getItems().add(admin.getAdminId());
        }*/f1=true;
    }private boolean f2=false;
    private void setupTab2() {
        @SuppressWarnings("unchecked")
        List<ReportType> rows = (List<ReportType>) forAllEntities.getAllRows("report_type");
        ObservableList<ReportType> observableList = FXCollections.observableArrayList(rows);
        ReportType1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReportTypeId()));
        ReportType2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReportTypeName()));
        ReportTypeTableView.setItems(observableList);if(f2)return;
        for (ReportType row : rows) {
            IDReportTypeComboBox.getItems().add(row.getReportTypeId());
            ReportTypeNameComboBox.getItems().add(row.getReportTypeName());
            ReportTypeIDComboBox.getItems().add(row.getReportTypeId());
        }f2=true;
    }private boolean f3=false;
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
        ReportTableView.setItems(observableList);if(f3)return;
        Set<String> uniqueReportContents = new HashSet<>();
        Set<String> uniqueReportDates = new HashSet<>();
        for (Report row : rows) {
            ReportIDComboBox.getItems().add(row.getReportId());
            uniqueReportContents.add(row.getContent());
            if (row.getReportDate() != null) {
                uniqueReportDates.add(String.valueOf(row.getReportDate()));
            }
        }
        ReportContentComboBox.getItems().setAll(uniqueReportContents);
        ReportDateComboBox.getItems().setAll(uniqueReportDates);
        /*for (Report row : rows) {
            ReportIDComboBox.getItems().add(row.getReportId());
            //ReportTypeIDComboBox.getItems().add(row.getReportTypeId());
            //IDAdminComboBox.getItems().add(row.getAdminId());
            ReportContentComboBox.getItems().add(row.getContent());
            //ReportIdRecyclingComboBox.getItems().add(row.getRecyclingId());
            ReportDateComboBox.getItems().add(String.valueOf(row.getReportDate()));
        }*/f3=true;
    }private boolean f4=false;
    private void setupTab4() {
        @SuppressWarnings("unchecked")
        List<RecyclingRule> rows = (List<RecyclingRule>) forAllEntities.getAllRows("recycling_rule");
        ObservableList<RecyclingRule> observableList = FXCollections.observableArrayList(rows);
        RecyclingRule1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRuleId()));
        RecyclingRule2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContent()));
        RecyclingRuleTableView.setItems(observableList);if(f4)return;
        Set<String> uniqueContents = new HashSet<>();
        for (RecyclingRule row : rows) {
            RecyclingRuleIDComboBox.getItems().add(row.getRuleId());
            uniqueContents.add(row.getContent());
            RecyclingRecyclingRuleIDComboBox.getItems().add(row.getRuleId());
        }
        RecyclingRuleContentComboBox.getItems().setAll(uniqueContents);
        /*for (RecyclingRule row : rows) {
            RecyclingRuleIDComboBox.getItems().add(row.getRuleId());
            RecyclingRuleContentComboBox.getItems().add(row.getContent());
            RecyclingRecyclingRuleIDComboBox.getItems().add(row.getRuleId());
        }*/f4=true;
    }private boolean f5=false;
    private void setupTab5() {
        @SuppressWarnings("unchecked")
        List<Recycling> rows = (List<Recycling>) forAllEntities.getAllRows("recycling");
        ObservableList<Recycling> observableList = FXCollections.observableArrayList(rows);
        Recycling1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingId()));
        Recycling2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingStatusId()));
        Recycling3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRuleId()));
        Recycling4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashInfoId()));
        Recycling5.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRecyclingDate()));
        RecyclingTableView.setItems(observableList);if(f5)return;
        Set<String> uniqueRecyclingDates = new HashSet<>();
        for (Recycling row : rows) {
            RecyclingIDComboBox.getItems().add(row.getRecyclingId());
            ReportIdRecyclingComboBox.getItems().add(row.getRecyclingId());
            uniqueRecyclingDates.add(String.valueOf(row.getRecyclingDate()));
        }
        RecyclingDateComboBox.getItems().setAll(uniqueRecyclingDates);
        /*for (Recycling row : rows) {
            RecyclingIDComboBox.getItems().add(row.getRecyclingId());
            //RecyclingStatusIdComboBox.getItems().add(row.getRecyclingStatusId());
            //RecyclingRecyclingRuleIDComboBox.getItems().add(row.getRuleId());
            //RecyclingIDTrashInfoComboBox.getItems().add(row.getTrashInfoId());
            RecyclingDateComboBox.getItems().add(String.valueOf(row.getRecyclingDate()));
            ReportIdRecyclingComboBox.getItems().add(row.getRecyclingId());
        }*/f5=true;
    }private boolean f6=false;
    private void setupTab6() {
        @SuppressWarnings("unchecked")
        List<RecyclingStatus> rows = (List<RecyclingStatus>) forAllEntities.getAllRows("recycling_status");
        ObservableList<RecyclingStatus> observableList = FXCollections.observableArrayList(rows);
        RecyclingStatus1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingStatusId()));
        RecyclingStatus2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecyclingStatusName()));
        RecyclingStatus3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurrentProcessDescription()));
        RecyclingStatusTableView.setItems(observableList);if(f6)return;
        Set<String> uniqueDescriptions = new HashSet<>();
        for (RecyclingStatus row : rows) {
            RecyclingStatusIDComboBox.getItems().add(row.getRecyclingStatusId());
            RecyclingStatusIdComboBox.getItems().add(row.getRecyclingStatusId());
            RecyclingStatusNameComboBox.getItems().add(row.getRecyclingStatusName());
            uniqueDescriptions.add(row.getCurrentProcessDescription());
        }
        RecyclingStatusContentComboBox.getItems().setAll(uniqueDescriptions);
        /*for (RecyclingStatus row : rows) {
            RecyclingStatusIDComboBox.getItems().add(row.getRecyclingStatusId());
            RecyclingStatusNameComboBox.getItems().add(row.getRecyclingStatusName());
            RecyclingStatusContentComboBox.getItems().add(row.getCurrentProcessDescription());
            RecyclingStatusIdComboBox.getItems().add(row.getRecyclingStatusId());
        }*/f6=true;
    }private boolean f7=false;
    private void setupTab7() {
        @SuppressWarnings("unchecked")
        List<TrashInfo> rows = (List<TrashInfo>) forAllEntities.getAllRows("trash_info");
        ObservableList<TrashInfo> observableList = FXCollections.observableArrayList(rows);
        TrashInfo1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashInfoId()));
        TrashInfo2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserId()));
        TrashInfo3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashTypeId()));
        TrashInfo4.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTrashQuantity()));
        TrashInfoTableView.setItems(observableList);if(f7)return;
        Set<Integer> uniqueTrashQuantities = new HashSet<>();
        for (TrashInfo row : rows) {
            TrashInfoIDComboBox.getItems().add(row.getTrashInfoId());
            RecyclingIDTrashInfoComboBox.getItems().add(row.getTrashInfoId());
            uniqueTrashQuantities.add(row.getTrashQuantity());
        }
        TrashInfoComboBoxTrashQuantity.getItems().setAll(uniqueTrashQuantities);
        /*for (TrashInfo row : rows) {
            TrashInfoIDComboBox.getItems().add(row.getTrashInfoId());
            //TrashInfoComboBoxIDUser.getItems().add(row.getUserId());
            //TrashInfoComboBoxIDTrashType.getItems().add(row.getTrashTypeId());
            TrashInfoComboBoxTrashQuantity.getItems().add(row.getTrashQuantity());
            RecyclingIDTrashInfoComboBox.getItems().add(row.getTrashInfoId());
        }*/f7=true;
    }private boolean f8=false;
    private void setupTab8() {
        @SuppressWarnings("unchecked")
        List<TrashType> rows = (List<TrashType>) forAllEntities.getAllRows("trash_type");
        ObservableList<TrashType> observableList = FXCollections.observableArrayList(rows);
        TrashType1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashTypeId()));
        TrashType2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrashTypeName()));
        TrashTypeTableView.setItems(observableList);if(f8)return;
        for (TrashType row : rows) {
            TrashTypeComboBoxID.getItems().add(row.getTrashTypeId());
            TrashTypeComboBoxName.getItems().add(row.getTrashTypeName());
            TrashInfoComboBoxIDTrashType.getItems().add(row.getTrashTypeId());
        }f8=true;
    }private boolean f9=false;
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
        UserTableView.setItems(adminObservableList);if(f9)return;
        Set<String> uniqueNames = new HashSet<>();
        Set<String> uniqueSurnames = new HashSet<>();
        Set<String> uniquePasswords = new HashSet<>();
        Set<String> uniqueEmails = new HashSet<>();
        for (User row : rows) {
            UserIDComboBox.getItems().add(row.getUserId());
            UserLoginComboBox.getItems().add(row.getLogin());
            TrashInfoComboBoxIDUser.getItems().add(row.getUserId());
            if (row.getName() != null && !row.getName().isEmpty()) uniqueNames.add(row.getName());
            if (row.getSurname() != null && !row.getSurname().isEmpty()) uniqueSurnames.add(row.getSurname());
            uniquePasswords.add(row.getPassword());
            if (row.getEmail() != null && !row.getEmail().isEmpty()) uniqueEmails.add(row.getEmail());
        }
        UserNameComboBox.getItems().setAll(uniqueNames);
        UserSurnameComboBox.getItems().setAll(uniqueSurnames);
        UserPasswordComboBox.getItems().setAll(uniquePasswords);
        UserEmailComboBjx.getItems().setAll(uniqueEmails);
        /*for (User row : rows) {
            UserIDComboBox.getItems().add(row.getUserId());
            UserLoginComboBox.getItems().add(row.getLogin());
            if(row.getName() != null &&!row.getName().isEmpty())UserNameComboBox.getItems().add(row.getName());
            if(row.getSurname() != null &&!row.getSurname().isEmpty())UserSurnameComboBox.getItems().add(row.getSurname());
            UserPasswordComboBox.getItems().add(row.getPassword());
            if(row.getEmail() != null &&!row.getEmail().isEmpty())UserEmailComboBjx.getItems().add(row.getEmail());
            TrashInfoComboBoxIDUser.getItems().add(row.getUserId());
        }*/f9=true;
    }
}
