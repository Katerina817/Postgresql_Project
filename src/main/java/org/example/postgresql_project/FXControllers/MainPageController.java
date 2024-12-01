package org.example.postgresql_project.FXControllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.postgresql_project.DataBaseConnection;
import org.example.postgresql_project.Entities.*;
import org.example.postgresql_project.Entities.ReportAndAdmin;
import org.example.postgresql_project.EntitiesControl.*;
import org.example.postgresql_project.ErrorClass;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

public class MainPageController {
    Boolean user;
    public void setInfo(Boolean info) {
        this.user = info;
        if(user){
            AdminTab.setDisable(true);
            ReportTypeTab.setDisable(true);
            ReportTab.setDisable(true);
            RecyclingStatusTab.setDisable(true);
            TrashInfoTab.setDisable(true);
        }
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
    //FOR UPDATE
    @FXML
    private ComboBox<String> updateAdminID,updateAdminColumnName;
    @FXML
    private TextField newValueAdmin;
    //FOR DELETE
    @FXML
    private ChoiceBox<String> deleteAdminColumnName;
    @FXML
    private TextField delValueAdmin;
//ONLY FOR ReportType
    @FXML
    private ComboBox<String> IDReportTypeComboBox,ReportTypeNameComboBox;
    //FOR UPDATE
    @FXML
    private ComboBox<String> updateReportTypeID,updateReportTypeColumnName;
    @FXML
    private TextField newValueReportType;
    //FOR DELETE
    @FXML
    private ChoiceBox<String> deleteReportTypeColumnName;
    @FXML
    private TextField delValueReportType;
//ONLY FOR Report
    @FXML
    private ComboBox<String> ReportIDComboBox, ReportTypeIDComboBox, IDAdminComboBox, ReportContentComboBox,ReportIdRecyclingComboBox;
    @FXML
    private ComboBox<String> ReportDateComboBox;
    //FOR UPDATE
    @FXML
    private ComboBox<String> updateReportID,updateReportColumnName;
    @FXML
    private TextField newValueReport;
    //FOR DELETE
    @FXML
    private ChoiceBox<String> deleteReportColumnName;
    @FXML
    private TextField delValueReport;
//ONLY FOR RecyclingRule
    @FXML
    private ComboBox<String> RecyclingRuleIDComboBox, RecyclingRuleContentComboBox;
    //FOR UPDATE
    @FXML
    private ComboBox<String> updateRecyclingRuleID,updateRecyclingRuleColumnName;
    @FXML
    private TextField newValueRecyclingRule;
    //FOR DELETE
    @FXML
    private ChoiceBox<String> deleteRecyclingRuleColumnName;
    @FXML
    private TextField delValueRecyclingRule;
//ONLY FOR Recycling
    @FXML
    private ComboBox<String> RecyclingIDComboBox, RecyclingStatusIdComboBox, RecyclingRecyclingRuleIDComboBox, RecyclingIDTrashInfoComboBox;
    @FXML
    private ComboBox<String> RecyclingDateComboBox;
    //FOR UPDATE
    @FXML
    private ComboBox<String> updateRecyclingID,updateRecyclingColumnName;
    @FXML
    private TextField newValueRecycling;
    //FOR DELETE
    @FXML
    private ChoiceBox<String> deleteRecyclingColumnName;
    @FXML
    private TextField delValueRecycling;
//ONLY FOR RecyclingStatus
    @FXML
    private ComboBox<String> RecyclingStatusIDComboBox, RecyclingStatusNameComboBox, RecyclingStatusContentComboBox;
    //FOR UPDATE
    @FXML
    private ComboBox<String> updateRecyclingStatusID,updateRecyclingStatusColumnName;
    @FXML
    private TextField newValueRecyclingStatus;
    //FOR DELETE
    @FXML
    private ChoiceBox<String> deleteRecyclingStatusColumnName;
    @FXML
    private TextField delValueRecyclingStatus;
//ONLY FOR TrashInfo
    @FXML
    private ComboBox<String> TrashInfoIDComboBox, TrashInfoComboBoxIDUser, TrashInfoComboBoxIDTrashType;
    @FXML
    private ComboBox<Integer> TrashInfoComboBoxTrashQuantity;
    //FOR UPDATE
    @FXML
    private ComboBox<String> updateTrashInfoID,updateTrashInfoColumnName;
    @FXML
    private TextField newValueTrashInfo;
    //FOR DELETE
    @FXML
    private ChoiceBox<String> deleteTrashInfoColumnName;
    @FXML
    private TextField delValueTrashInfo;

//ONLY FOR TrashType
    @FXML
    private ComboBox<String> TrashTypeComboBoxID,TrashTypeComboBoxName;
    //FOR UPDATE
    @FXML
    private ComboBox<String> updateTrashTypeID,updateTrashTypeColumnName;
    @FXML
    private TextField newValueTrashType;
    //FOR DELETE
    @FXML
    private ChoiceBox<String> deleteTrashTypeColumnName;
    @FXML
    private TextField delValueTrashType;
//ONLY FOR User
    @FXML
    private ComboBox<String> UserIDComboBox,UserLoginComboBox, UserNameComboBox, UserSurnameComboBox, UserPasswordComboBox, UserEmailComboBjx;
    //FOR UPDATE
    @FXML
    private ComboBox<String> updateUserID,updateUserColumnName;
    @FXML
    private TextField newValueUser;
    //FOR DELETE
    @FXML
    private ChoiceBox<String> deleteUserColumnName;
    @FXML
    private TextField delValueUser;


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
        updateAdminColumnName.getItems().add("Логин");updateAdminColumnName.getItems().add("Имя");
        updateAdminColumnName.getItems().add("Фамилия");updateAdminColumnName.getItems().add("Пароль");updateAdminColumnName.getItems().add("Почта");
        updateAdminColumnName.getItems().add("Возраст");updateAdminColumnName.getItems().add("Год рождения");
        updateReportTypeColumnName.getItems().add("Наименование типа отчета");
        updateReportColumnName.getItems().add("ID типа отчета");updateReportColumnName.getItems().add("ID админа");
        updateReportColumnName.getItems().add("Содержание");updateReportColumnName.getItems().add("Дата создания отчета");updateReportColumnName.getItems().add("ID переработки");
        updateRecyclingRuleColumnName.getItems().add("Содержание");
        updateRecyclingColumnName.getItems().add("ID статуса переработки");
        updateRecyclingColumnName.getItems().add("ID правила переработки");updateRecyclingColumnName.getItems().add("ID информации о мусоре");updateRecyclingColumnName.getItems().add("Дата переработки");
        updateRecyclingStatusColumnName.getItems().add("Наименование статуса переработки");updateRecyclingStatusColumnName.getItems().add("Описание текущего процесса");
        updateTrashInfoColumnName.getItems().add("ID пользователя");
        updateTrashInfoColumnName.getItems().add("ID типа мусора");updateTrashInfoColumnName.getItems().add("Количество мусора");
        updateUserColumnName.getItems().add("Логин");updateUserColumnName.getItems().add("Имя");
        updateUserColumnName.getItems().add("Фамилия");updateUserColumnName.getItems().add("Пароль");updateUserColumnName.getItems().add("Почта");
        updateTrashTypeColumnName.getItems().add("Наименование типа мусора");

        deleteAdminColumnName.getItems().add("ID");deleteAdminColumnName.getItems().add("Логин");deleteAdminColumnName.getItems().add("Имя");
        deleteAdminColumnName.getItems().add("Фамилия");deleteAdminColumnName.getItems().add("Пароль");deleteAdminColumnName.getItems().add("Почта");
        deleteAdminColumnName.getItems().add("Возраст");deleteAdminColumnName.getItems().add("Год рождения");
        deleteReportTypeColumnName.getItems().add("ID");deleteReportTypeColumnName.getItems().add("Наименование типа отчета");
        deleteReportColumnName.getItems().add("ID");deleteReportColumnName.getItems().add("ID типа отчета");deleteReportColumnName.getItems().add("ID админа");
        deleteReportColumnName.getItems().add("Содержание");deleteReportColumnName.getItems().add("Дата создания отчета");deleteReportColumnName.getItems().add("ID переработки");
        deleteRecyclingRuleColumnName.getItems().add("ID");deleteRecyclingRuleColumnName.getItems().add("Содержание");
        deleteRecyclingColumnName.getItems().add("ID");deleteRecyclingColumnName.getItems().add("ID статуса переработки");
        deleteRecyclingColumnName.getItems().add("ID правила переработки");deleteRecyclingColumnName.getItems().add("ID информации о мусоре");deleteRecyclingColumnName.getItems().add("Дата переработки");
        deleteRecyclingStatusColumnName.getItems().add("ID");deleteRecyclingStatusColumnName.getItems().add("Наименование статуса переработки");deleteRecyclingStatusColumnName.getItems().add("Описание текущего процесса");
        deleteTrashInfoColumnName.getItems().add("ID");deleteTrashInfoColumnName.getItems().add("ID пользователя");
        deleteTrashInfoColumnName.getItems().add("ID типа мусора");deleteTrashInfoColumnName.getItems().add("Количество мусора");
        deleteUserColumnName.getItems().add("ID");deleteUserColumnName.getItems().add("Логин");deleteUserColumnName.getItems().add("Имя");
        deleteUserColumnName.getItems().add("Фамилия");deleteUserColumnName.getItems().add("Пароль");deleteUserColumnName.getItems().add("Почта");
        deleteTrashTypeColumnName.getItems().add("ID");deleteTrashTypeColumnName.getItems().add("Наименование типа мусора");
    }
    //обработчики для удаления
    @FXML
    private void onDeleteAdmin(){
        if (deleteAdminColumnName.getValue()==null || delValueAdmin.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции изменения информации");
            return;
        }
        String columnName = switch (deleteAdminColumnName.getValue()) {
            case "ID" -> "admin_id";
            case "Логин" -> "login";
            case "Имя" -> "name";
            case "Фамилия" -> "surname";
            case "Пароль" -> "password";
            case "Почта" -> "email";
            case "Возраст" -> "age";
            case "Год рождения" -> "birth_year";
            default -> "";
        };
        AdminControl control=new AdminControl(DataBaseConnection.getConnection());
        boolean res=control.deleteAdminByColumnName(columnName, delValueAdmin.getText());
        if(res){
            new ErrorClass().startSuccess("Успех", "Запись успешно удалена");
        }
        f1=false;setupTab1();CleanComboBoxAdmin();
    }@FXML
    private void onDeleteReportType(){
        if (deleteReportTypeColumnName.getValue()==null || delValueReportType.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции удаления информации");
            return;
        }
        String columnName = switch (deleteReportTypeColumnName.getValue()) {
            case "ID" -> "report_type_id";
            case "Наименование типа отчета" -> "report_type_name";
            default -> "";
        };
        ReportTypeControl control=new ReportTypeControl(DataBaseConnection.getConnection());
        boolean res=control.deleteReportTypeByColumnName(columnName, delValueReportType.getText());
        if(res){
            new ErrorClass().startSuccess("Успех", "Запись успешно удалена");
        }f2=false;setupTab2();CleanComboBoxReportType();
    }@FXML
    private void onDeleteReport(){
        if (deleteReportColumnName.getValue()==null || delValueReport.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции удаления информации");
            return;
        }
        String columnName = switch (deleteReportColumnName.getValue()) {
            case "ID" -> "report_id";
            case "ID типа отчета" -> "report_type_id";
            case "ID админа" -> "admin_id";
            case "Содержание" -> "content";
            case "Дата создания отчета" -> "report_date";
            case "ID переработки" -> "recycling_id";
            default -> "";
        };
        ReportControl control=new ReportControl(DataBaseConnection.getConnection());
        boolean res=control.deleteReportByColumnName(columnName, delValueReport.getText());
        if(res){
            new ErrorClass().startSuccess("Успех", "Запись успешно удалена");
        }f3=false;setupTab3();CleanComboBoxReport();
    }@FXML
    private void onDeleteRecyclingRule(){
        if (deleteRecyclingRuleColumnName.getValue()==null || delValueRecyclingRule.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции удаления информации");
            return;
        }
        String columnName = switch (deleteRecyclingRuleColumnName.getValue()) {
            case "ID" -> "rule_id";
            case "Содержание" -> "content";
            default -> "";
        };
        RecyclingRuleControl control=new RecyclingRuleControl(DataBaseConnection.getConnection());
        boolean res=control.deleteRecyclingRuleByColumnName(columnName, delValueRecyclingRule.getText());
        if(res){
            new ErrorClass().startSuccess("Успех", "Запись успешно удалена");
        }f4=false;setupTab4();CleanComboBoxRecyclingRule();
    }@FXML
    private void onDeleteRecycling(){
        if (deleteRecyclingColumnName.getValue()==null || delValueRecycling.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции удаления информации");
            return;
        }
        String columnName = switch (deleteRecyclingColumnName.getValue()) {
            case "ID" -> "recycling_id";
            case "ID статуса переработки" -> "recycling_status_id";
            case "ID правила переработки" -> "rule_id";
            case "ID информации о мусоре" -> "trash_info_id";
            case "Дата переработки" -> "recycling_date";
            default -> "";
        };
        RecyclingControl control=new RecyclingControl(DataBaseConnection.getConnection());
        boolean res=control.deleteRecyclingByColumnName(columnName, delValueRecycling.getText());
        if(res){
            new ErrorClass().startSuccess("Успех", "Запись успешно удалена");
        }f5=false;setupTab5();CleanComboBoxRecycling();
    }@FXML
    private void onDeleteRecyclingStatus(){
        if (deleteRecyclingStatusColumnName.getValue()==null || delValueRecyclingStatus.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции удаления информации");
            return;
        }
        String columnName = switch (deleteRecyclingStatusColumnName.getValue()) {
            case "ID" -> "recycling_status_id";
            case "Наименование статуса переработки" -> "recycling_status_name";
            case "Описание текущего процесса" -> "current_process_description";
            default -> "";
        };
        RecyclingStatusControl control=new RecyclingStatusControl(DataBaseConnection.getConnection());
        boolean res=control.deleteRecyclingStatusByColumnName(columnName, delValueRecyclingStatus.getText());
        if(res){
            new ErrorClass().startSuccess("Успех", "Запись успешно удалена");
        }f6=false;setupTab6();CleanComboBoxRecyclingStatus();
    }@FXML
    private void onDeleteTrashInfo(){
        if (deleteTrashInfoColumnName.getValue()==null || delValueTrashInfo.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции удаления информации");
            return;
        }
        String columnName = switch (deleteTrashInfoColumnName.getValue()) {
            case "ID" -> "trash_info_id";
            case "ID пользователя" -> "user_id";
            case "ID типа мусора" -> "trash_type_id";
            case "Количество мусора" -> "trash_quantity";
            default -> "";
        };
        TrashInfoControl control=new TrashInfoControl(DataBaseConnection.getConnection());
        boolean res=control.deleteTrashInfoByColumnName(columnName, delValueTrashInfo.getText());
        if(res){
            new ErrorClass().startSuccess("Успех", "Запись успешно удалена");
        }f7=false;setupTab7();CleanComboBoxTrashInfo();
    }
    @FXML
    private void getTotalQuantity(){
        if (deleteTrashInfoColumnName.getValue()==null || delValueTrashInfo.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции удаления информации");
            return;
        }
        if(deleteTrashInfoColumnName.getValue().equals("ID пользователя")){
            ProceduresAndFunctions proceduresAndFunctions=new ProceduresAndFunctions(DataBaseConnection.getConnection());
            int res=proceduresAndFunctions.getTotalTrashQuantityByUser(delValueTrashInfo.getText());
            if (res==0){
                new ErrorClass().startSuccess("Успех","Общее количество мусора пользователя с ID "+delValueTrashInfo.getText()+" равно: "+res,"Возможно вы ввели неверное значение ID пользователя. Для получения результата введите верное значение.");
            }
            else {
                new ErrorClass().startSuccess("Успех","Общее количество мусора пользователя с ID "+delValueTrashInfo.getText()+" равно: "+res);
            }CleanComboBoxTrashInfo();
        }
        else{
            new ErrorClass().startError("Ошибка","Для выполнения данной операции необходимо ввести название колонки: ID пользователя");
        }
    }
    @FXML
    private void onDeleteUser(){
        if (deleteUserColumnName.getValue()==null || delValueUser.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции удаления информации");
            return;
        }
        String columnName = switch (deleteUserColumnName.getValue()) {
            case "ID" -> "user_id";
            case "Логин" -> "login";
            case "Имя" -> "name";
            case "Фамилия" -> "surname";
            case "Пароль" -> "password";
            case "Почта" -> "email";
            default -> "";
        };
        UserControl control=new UserControl(DataBaseConnection.getConnection());
        boolean res=control.deleteUsersByColumnName(columnName, delValueUser.getText());
        if(res){
            new ErrorClass().startSuccess("Успех", "Запись успешно удалена");
        }f9=false;setupTab9();CleanComboBoxUser();
    }@FXML
    private void onDeleteTrashType(){
        if (deleteTrashTypeColumnName.getValue()==null || delValueTrashType.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции удаления информации");
            return;
        }
        String columnName = switch (deleteTrashTypeColumnName.getValue()) {
            case "ID" -> "trash_type_id";
            case "Наименование типа мусора" -> "trash_type_name";
            default -> "";
        };
        TrashTypeControl control=new TrashTypeControl(DataBaseConnection.getConnection());
        boolean res=control.deleteTrashTypeByColumnName(columnName, delValueTrashType.getText());
        if(res){
            new ErrorClass().startSuccess("Успех", "Запись успешно удалена");
        }f8=false;setupTab8();CleanComboBoxTrashType();
    }
    //обработчики для изменения
    @FXML
    private void onUpdateAdmin(){
        if (updateAdminID.getValue()==null || updateAdminColumnName.getValue()==null || newValueAdmin.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции изменения информации");
            return;
        }
        String columnName = switch (updateAdminColumnName.getValue()) {
            case "Логин" -> "login";
            case "Имя" -> "name";
            case "Фамилия" -> "surname";
            case "Пароль" -> "password";
            case "Почта" -> "email";
            case "Возраст" -> "age";
            case "Год рождения" -> "birth_year";
            default -> "";
        };
        AdminControl control=new AdminControl(DataBaseConnection.getConnection());
        control.updateAdminField(updateAdminID.getValue(),columnName, newValueAdmin.getText());f1=false;setupTab1();
        CleanComboBoxAdmin();
    }
    @FXML
    private void onUpdateReportType(){
        if (updateReportTypeID.getValue()==null || updateReportTypeColumnName.getValue()==null || newValueReportType.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции изменения информации");
            return;
        }
        String columnName = switch (updateReportTypeColumnName.getValue()) {
            case "Наименование типа отчета" -> "report_type_name";
            default -> "";
        };
        ReportTypeControl control=new ReportTypeControl(DataBaseConnection.getConnection());
        control.updateReportTypeField(updateReportTypeID.getValue(),columnName, newValueReportType.getText());f2=false;setupTab2();
        CleanComboBoxReportType();
    }@FXML
    private void onUpdateReport(){
        if (updateReportID.getValue()==null || updateReportColumnName.getValue()==null || newValueReport.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции изменения информации");
            return;
        }
        String columnName = switch (updateReportColumnName.getValue()) {
            case "ID типа отчета" -> "report_type_id";
            case "ID админа" -> "admin_id";
            case "Содержание" -> "content";
            case "Дата создания отчета" -> "report_date";
            case "ID переработки" -> "recycling_id";
            default -> "";
        };
        ReportControl control=new ReportControl(DataBaseConnection.getConnection());
        control.updateReportField(updateReportID.getValue(),columnName, newValueReport.getText());f3=false;setupTab3();
        CleanComboBoxReport();
    }@FXML
    private void onUpdateRecyclingRule(){
        if (updateRecyclingRuleID.getValue()==null || updateRecyclingRuleColumnName.getValue()==null || newValueRecyclingRule.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции изменения информации");
            return;
        }
        String columnName = switch (updateRecyclingRuleColumnName.getValue()) {
            case "Содержание" -> "content";
            default -> "";
        };
        RecyclingRuleControl control=new RecyclingRuleControl(DataBaseConnection.getConnection());
        control.updateRecyclingRuleField(updateRecyclingRuleID.getValue(),columnName, newValueRecyclingRule.getText());f4=false;setupTab4();
        CleanComboBoxRecyclingRule();
    }@FXML
    private void onUpdateRecycling(){
        if (updateRecyclingID.getValue()==null || updateRecyclingColumnName.getValue()==null || newValueRecycling.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции изменения информации");
            return;
        }
        String columnName = switch (updateRecyclingColumnName.getValue()) {
            case "ID статуса переработки" -> "recycling_status_id";
            case "ID правила переработки" -> "rule_id";
            case "ID информации о мусоре" -> "trash_info_id";
            case "Дата переработки" -> "recycling_date";
            default -> "";
        };
        RecyclingControl control=new RecyclingControl(DataBaseConnection.getConnection());
        control.updateRecyclingField(updateRecyclingID.getValue(),columnName, newValueRecycling.getText());f5=false;setupTab5();
        CleanComboBoxRecycling();
    }@FXML
    private void onUpdateRecyclingStatus(){
        if (updateRecyclingStatusID.getValue()==null || updateRecyclingStatusColumnName.getValue()==null || newValueRecyclingStatus.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции изменения информации");
            return;
        }
        String columnName = switch (updateRecyclingStatusColumnName.getValue()) {
            case "Наименование статуса переработки" -> "recycling_status_name";
            case "Описание текущего процесса" -> "current_process_description";
            default -> "";
        };
        if (updateRecyclingStatusColumnName.getValue().equals("Описание текущего процесса")){
            ProceduresAndFunctions proceduresAndFunctions=new ProceduresAndFunctions(DataBaseConnection.getConnection());
            proceduresAndFunctions.callUpdateRecyclingStatusProcedure(updateRecyclingStatusID.getValue(),newValueRecyclingStatus.getText());
        }
        else{
            RecyclingStatusControl control=new RecyclingStatusControl(DataBaseConnection.getConnection());
            control.updateRecyclingStatusField(updateRecyclingStatusID.getValue(),columnName, newValueRecyclingStatus.getText());
        }
        f6=false;setupTab6();
        CleanComboBoxRecyclingStatus();
    }@FXML
    private void onUpdateTrashInfo(){
        if (updateTrashInfoID.getValue()==null || updateTrashInfoColumnName.getValue()==null || newValueTrashInfo.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции изменения информации");
            return;
        }
        String columnName = switch (updateTrashInfoColumnName.getValue()) {
            case "ID пользователя" -> "user_id";
            case "ID типа мусора" -> "trash_type_id";
            case "Количество мусора" -> "trash_quantity";
            default -> "";
        };
        TrashInfoControl control=new TrashInfoControl(DataBaseConnection.getConnection());
        control.updateTrashInfoField(updateTrashInfoID.getValue(),columnName, newValueTrashInfo.getText());f7=false;setupTab7();
        CleanComboBoxTrashInfo();
    }@FXML
    private void onUpdateUser(){
        if (updateUserID.getValue()==null || updateUserColumnName.getValue()==null || newValueUser.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции изменения информации");
            return;
        }
        String columnName = switch (updateUserColumnName.getValue()) {
            case "Логин" -> "login";
            case "Имя" -> "name";
            case "Фамилия" -> "surname";
            case "Пароль" -> "password";
            case "Почта" -> "email";
            default -> "";
        };
        UserControl control=new UserControl(DataBaseConnection.getConnection());
        control.updateUserField(updateUserID.getValue(),columnName, newValueUser.getText());f9=false;setupTab9();
        CleanComboBoxUser();
    }@FXML
    private void onUpdateTrashType(){
        if (updateTrashTypeID.getValue()==null || updateTrashTypeColumnName.getValue()==null || newValueTrashType.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции изменения информации");
            return;
        }
        String columnName = switch (updateTrashTypeColumnName.getValue()) {
            case "Наименование типа мусора" -> "trash_type_name";
            default -> "";
        };
        TrashTypeControl control=new TrashTypeControl(DataBaseConnection.getConnection());
        control.updateTrashTypeField(updateTrashTypeID.getValue(),columnName, newValueTrashType.getText());f8=false;setupTab8();
        CleanComboBoxTrashType();
    }
    @FXML
    private void getReportsByRecyclingType()throws IOException {
        if (deleteReportTypeColumnName.getValue()==null || delValueReportType.getText()==null){
            new ErrorClass().startError("Ошибка","Поля не заполнены","Пожалуйста, заполните все поля для выполнения операции");
            return;
        }
        if(deleteReportTypeColumnName.getValue().equals("Наименование типа отчета")){
            ProceduresAndFunctions proceduresAndFunctions=new ProceduresAndFunctions(DataBaseConnection.getConnection());
            List<ReportAndAdmin>res=proceduresAndFunctions.getReportsByType(delValueReportType.getText());CleanComboBoxReportType();
            if (res == null || res.isEmpty()) {
                new ErrorClass().startError("Ошибка","Список пуст");
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/postgresql_project/reportAndAdmin.fxml"));
            Parent root = loader.load();
            ReportAndAdminController reportAndAdminController = loader.getController();
            reportAndAdminController.setInfo(res);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }else{
            new ErrorClass().startError("Ошибка","Для выполнения данной операции необходимо ввести название колонки: Наименование типа отчета");
        }
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
                f1=false;setupTab1();CleanComboBoxAdmin();//IDAdminComboBox.getItems().add(admin.getAdminId());
                //IDComboBox.getItems().add(admin.getAdminId());updateAdminID.getItems().add(admin.getAdminId());
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
                f2=false;setupTab2();CleanComboBoxReportType();//ReportTypeIDComboBox.getItems().add(reportType.getReportTypeId());
                //IDReportTypeComboBox.getItems().add(reportType.getReportTypeId());updateReportTypeID.getItems().add(reportType.getReportTypeId());
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
                f3=false;setupTab3();CleanComboBoxReport();//ReportIDComboBox.getItems().add(report.getReportId());updateReportID.getItems().add(report.getReportId());
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
                f4=false;setupTab4();CleanComboBoxRecyclingRule();//RecyclingRecyclingRuleIDComboBox.getItems().add(recyclingRule.getRuleId());
                //RecyclingRuleIDComboBox.getItems().add(recyclingRule.getRuleId());updateRecyclingRuleID.getItems().add(recyclingRule.getRuleId());
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
                f5=false;setupTab5();CleanComboBoxRecycling();//ReportIdRecyclingComboBox.getItems().add(recycling.getRecyclingId());
                //RecyclingIDComboBox.getItems().add(recycling.getRecyclingId());updateRecyclingID.getItems().add(recycling.getRecyclingId());
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
                f6=false;setupTab6();CleanComboBoxRecyclingStatus();//RecyclingStatusIdComboBox.getItems().add(recyclingStatus.getRecyclingStatusId());
                //RecyclingStatusIDComboBox.getItems().add(recyclingStatus.getRecyclingStatusId());updateRecyclingStatusID.getItems().add(recyclingStatus.getRecyclingStatusId());
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
                f7=false;setupTab7();CleanComboBoxTrashInfo();//RecyclingIDTrashInfoComboBox.getItems().add(trashInfo.getTrashInfoId());
                //TrashInfoIDComboBox.getItems().add(trashInfo.getTrashInfoId());updateTrashInfoID.getItems().add(trashInfo.getTrashInfoId());
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
                f9=false;setupTab9();CleanComboBoxUser();//TrashInfoComboBoxIDUser.getItems().add(user1.getUserId());
                //UserIDComboBox.getItems().add(user1.getUserId());updateUserID.getItems().add(user1.getUserId());
            }
        }catch (SQLException et) {
            System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
        }
    }
    @FXML
    private void AddRowToTrashType(){
        if (TrashTypeComboBoxID.getValue() != null && !TrashTypeComboBoxID.getValue().trim().isEmpty()) {
            new ErrorClass().startError("ID генерируется автоматически", "Пожалуйста, очистите ячейку ID для выполнения операции и попробуйте еще раз");
            return;
        }
        try {
            /*TrashType trashType = TrashType.builder()
                    .trashTypeName(TrashTypeComboBoxName.getValue())
                    .build();*/
            ProceduresAndFunctions proceduresAndFunctions=new ProceduresAndFunctions(DataBaseConnection.getConnection());
            proceduresAndFunctions.callAddTrashTypeProcedure(TrashTypeComboBoxName.getValue());
            f8=false;setupTab8();CleanComboBoxTrashType();
            /*TrashTypeControl control = new TrashTypeControl(DataBaseConnection.getConnection());
            boolean res = control.insertReportType(trashType);
            if(res){
                new ErrorClass().startSuccess("Успех", "Добавление строки прошло успешно");
                f8=false;setupTab8();//TrashInfoComboBoxIDTrashType.getItems().add(trashType.getTrashTypeId());
                //TrashTypeComboBoxID.getItems().add(trashType.getTrashTypeId());updateTrashTypeID.getItems().add(trashType.getTrashTypeId());
            }*/
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
        updateTrashTypeID.setValue(null);
        updateTrashTypeColumnName.setValue(null);
        newValueTrashType.setText(null);
        deleteTrashTypeColumnName.setValue(null);
        delValueTrashType.setText(null);
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
        updateUserID.setValue(null);
        updateUserColumnName.setValue(null);
        newValueUser.setText(null);
        deleteUserColumnName.setValue(null);
        delValueUser.setText(null);
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
        updateTrashInfoID.setValue(null);
        updateTrashInfoColumnName.setValue(null);
        newValueTrashInfo.setText(null);
        deleteTrashInfoColumnName.setValue(null);
        delValueTrashInfo.setText(null);
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
        updateRecyclingStatusID.setValue(null);
        updateRecyclingStatusColumnName.setValue(null);
        newValueRecyclingStatus.setText(null);
        deleteRecyclingStatusColumnName.setValue(null);
        delValueRecyclingStatus.setText(null);
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
        updateRecyclingID.setValue(null);
        updateRecyclingColumnName.setValue(null);
        newValueRecycling.setText(null);
        deleteRecyclingColumnName.setValue(null);
        delValueRecycling.setText(null);
    }
    @FXML
    private void CleanComboBoxRecyclingRule(){
        if(flag){
            flag=false;
            setupTab4();
        }
        RecyclingRuleIDComboBox.setValue(null);
        RecyclingRuleContentComboBox.setValue(null);
        updateRecyclingRuleID.setValue(null);
        updateRecyclingRuleColumnName.setValue(null);
        newValueRecyclingRule.setText(null);
        deleteRecyclingRuleColumnName.setValue(null);
        delValueRecyclingRule.setText(null);
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
        updateAdminID.setValue(null);
        updateAdminColumnName.setValue(null);
        newValueAdmin.setText(null);
        deleteAdminColumnName.setValue(null);
        delValueAdmin.setText(null);
    }
    @FXML
    private void CleanComboBoxReportType(){
        if(flag){
            flag=false;
            setupTab2();
        }
        IDReportTypeComboBox.setValue(null);
        ReportTypeNameComboBox.setValue(null);
        updateReportTypeID.setValue(null);
        updateReportTypeColumnName.setValue(null);
        newValueReportType.setText(null);
        deleteReportTypeColumnName.setValue(null);
        delValueReportType.setText(null);
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
        updateReportID.setValue(null);
        updateReportColumnName.setValue(null);
        newValueReport.setText(null);
        deleteReportColumnName.setValue(null);
        delValueReport.setText(null);
    }
    //Заполнение ячеек при нажатии на строку таблицы
    private void fillFieldsFromSelectedRowUser(User selected) {
        UserIDComboBox.setValue(selected.getUserId());
        UserLoginComboBox.setValue(selected.getLogin());
        UserNameComboBox.setValue(selected.getName());
        UserSurnameComboBox.setValue(selected.getSurname());
        UserPasswordComboBox.setValue(selected.getPassword());
        UserEmailComboBjx.setValue(selected.getEmail());
        updateUserID.setValue(selected.getUserId());
    }
    private void fillFieldsFromSelectedRowTrashType(TrashType selected) {
        TrashTypeComboBoxID.setValue(selected.getTrashTypeId());
        TrashTypeComboBoxName.setValue(selected.getTrashTypeName());
        updateTrashTypeID.setValue(selected.getTrashTypeId());
    }
    private void fillFieldsFromSelectedRowTrashInfo(TrashInfo selected) {
        TrashInfoIDComboBox.setValue(selected.getTrashInfoId());
        TrashInfoComboBoxIDUser.setValue(selected.getUserId());
        TrashInfoComboBoxIDTrashType.setValue(selected.getTrashTypeId());
        TrashInfoComboBoxTrashQuantity.setValue(selected.getTrashQuantity());
        updateTrashInfoID.setValue(selected.getTrashInfoId());
    }
    private void fillFieldsFromSelectedRowRecyclingStatus(RecyclingStatus selected) {
        RecyclingStatusIDComboBox.setValue(selected.getRecyclingStatusId());
        RecyclingStatusNameComboBox.setValue(selected.getRecyclingStatusName());
        RecyclingStatusContentComboBox.setValue(selected.getCurrentProcessDescription());
        updateRecyclingStatusID.setValue(selected.getRecyclingStatusId());
    }
    private void fillFieldsFromSelectedRowRecycling(Recycling selected) {
        RecyclingIDComboBox.setValue(selected.getRecyclingId());
        RecyclingStatusIdComboBox.setValue(selected.getRecyclingStatusId());
        RecyclingRecyclingRuleIDComboBox.setValue(selected.getRuleId());
        RecyclingIDTrashInfoComboBox.setValue(selected.getTrashInfoId());
        RecyclingDateComboBox.setValue(String.valueOf(selected.getRecyclingDate()));
        updateRecyclingID.setValue(selected.getRecyclingId());
    }
    private void fillFieldsFromSelectedRowRecyclingRule(RecyclingRule selected) {
        RecyclingRuleIDComboBox.setValue(selected.getRuleId());
        RecyclingRuleContentComboBox.setValue(selected.getContent());
        updateRecyclingRuleID.setValue(selected.getRuleId());
    }
    private void fillFieldsFromSelectedRowReport(Report selected) {
        ReportIDComboBox.setValue(selected.getReportId());
        ReportTypeIDComboBox.setValue(selected.getReportTypeId());
        IDAdminComboBox.setValue(selected.getAdminId());
        ReportContentComboBox.setValue(selected.getContent());
        ReportIdRecyclingComboBox.setValue(selected.getRecyclingId());
        ReportDateComboBox.setValue(String.valueOf(selected.getReportDate()));
        updateReportID.setValue(selected.getReportId());
    }
    private void fillFieldsFromSelectedRowReportType(ReportType selected) {
        IDReportTypeComboBox.setValue(selected.getReportTypeId());
        ReportTypeNameComboBox.setValue(selected.getReportTypeName());
        updateReportTypeID.setValue(selected.getReportTypeId());
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
        updateAdminID.setValue(selected.getAdminId());
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
        Set<String> uniqueID = new HashSet<>();
        Set<String> uniqueLogin = new HashSet<>();
        Set<String> uniqueNames = new HashSet<>();
        Set<String> uniqueSurnames = new HashSet<>();
        Set<String> uniquePasswords = new HashSet<>();
        Set<String> uniqueEmails = new HashSet<>();
        Set<Integer> uniqueAges = new HashSet<>();
        Set<Integer> uniqueBirthYears = new HashSet<>();
        for (Admin admin : admins) {
            uniqueID.add(admin.getAdminId());
            uniqueLogin.add(admin.getLogin());
            if (admin.getName() != null && !admin.getName().isEmpty()) uniqueNames.add(admin.getName());
            if (admin.getSurname() != null && !admin.getSurname().isEmpty()) uniqueSurnames.add(admin.getSurname());
            uniquePasswords.add(admin.getPassword());
            if (admin.getEmail() != null && !admin.getEmail().isEmpty()) uniqueEmails.add(admin.getEmail());
            uniqueAges.add(admin.getAge());
            uniqueBirthYears.add(admin.getBirthYear());
        }

        LoginComboBox.getItems().setAll(uniqueLogin);
        IDComboBox.getItems().setAll(uniqueID);
        updateAdminID.getItems().setAll(uniqueID);
        NameComboBox.getItems().setAll(uniqueNames);
        SurnameComboBox.getItems().setAll(uniqueSurnames);
        PasswordComboBox.getItems().setAll(uniquePasswords);
        EmailComboBox.getItems().setAll(uniqueEmails);
        AgeComboBox.getItems().setAll(uniqueAges);
        BirthYearComboBox.getItems().setAll(uniqueBirthYears);
        IDAdminComboBox.getItems().setAll(uniqueID);

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
        ReportTypeTableView.setItems(observableList);
        if(f2)return;Set<String> uniqueID = new HashSet<>();
        Set<String> uniqueName = new HashSet<>();
        for (ReportType row : rows) {
            uniqueID.add(row.getReportTypeId());
            uniqueName.add(row.getReportTypeName());
        }
        updateReportTypeID.getItems().setAll(uniqueID);
        IDReportTypeComboBox.getItems().setAll(uniqueID);
        ReportTypeNameComboBox.getItems().setAll(uniqueName);
        ReportTypeIDComboBox.getItems().setAll(uniqueID);
        f2=true;
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
        Set<String> uniqueID = new HashSet<>();
        Set<String> uniqueReportContents = new HashSet<>();
        Set<String> uniqueReportDates = new HashSet<>();
        for (Report row : rows) {
            uniqueID.add(row.getReportId());
            uniqueReportContents.add(row.getContent());
            if (row.getReportDate() != null) {
                uniqueReportDates.add(String.valueOf(row.getReportDate()));
            }
        }
        updateReportID.getItems().setAll(uniqueID);
        ReportIDComboBox.getItems().setAll(uniqueID);
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
        Set<String> uniqueID = new HashSet<>();
        for (RecyclingRule row : rows) {
            uniqueID.add(row.getRuleId());
            uniqueContents.add(row.getContent());
        }
        RecyclingRecyclingRuleIDComboBox.getItems().setAll(uniqueID);
        updateRecyclingRuleID.getItems().setAll(uniqueID);
        RecyclingRuleIDComboBox.getItems().setAll(uniqueID);
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
        Set<String> uniqueID = new HashSet<>();
        for (Recycling row : rows) {
            uniqueID.add(row.getRecyclingId());
            uniqueRecyclingDates.add(String.valueOf(row.getRecyclingDate()));
        }
        updateRecyclingID.getItems().setAll(uniqueID);
        RecyclingIDComboBox.getItems().setAll(uniqueID);
        ReportIdRecyclingComboBox.getItems().setAll(uniqueID);
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
        Set<String> uniqueID = new HashSet<>();
        Set<String> uniqueStatus = new HashSet<>();
        for (RecyclingStatus row : rows) {
            uniqueDescriptions.add(row.getCurrentProcessDescription());
            uniqueID.add(row.getRecyclingStatusId());
            uniqueStatus.add(row.getRecyclingStatusName());
        }
        updateRecyclingStatusID.getItems().setAll(uniqueID);
        RecyclingStatusIDComboBox.getItems().setAll(uniqueID);
        RecyclingStatusIdComboBox.getItems().setAll(uniqueID);
        RecyclingStatusNameComboBox.getItems().setAll(uniqueStatus);
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
        Set<String> uniqueID = new HashSet<>();
        for (TrashInfo row : rows) {
            uniqueTrashQuantities.add(row.getTrashQuantity());
            uniqueID.add(row.getTrashInfoId());
        }
        updateTrashInfoID.getItems().setAll(uniqueID);
        TrashInfoIDComboBox.getItems().setAll(uniqueID);
        RecyclingIDTrashInfoComboBox.getItems().setAll(uniqueID);
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
        Set<String> uniqueID = new HashSet<>();Set<String> uniqueName = new HashSet<>();
        for (TrashType row : rows) {
            uniqueID.add(row.getTrashTypeId());
            uniqueName.add(row.getTrashTypeName());
        }
        updateTrashTypeID.getItems().setAll(uniqueID);
        TrashTypeComboBoxID.getItems().setAll(uniqueID);
        TrashTypeComboBoxName.getItems().setAll(uniqueName);
        TrashInfoComboBoxIDTrashType.getItems().setAll(uniqueID);
        f8=true;
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
        Set<String> uniqueID = new HashSet<>();
        Set<String> uniqueLogin = new HashSet<>();
        Set<String> uniqueNames = new HashSet<>();
        Set<String> uniqueSurnames = new HashSet<>();
        Set<String> uniquePasswords = new HashSet<>();
        Set<String> uniqueEmails = new HashSet<>();
        for (User row : rows) {
            uniqueID.add(row.getUserId());
            uniqueLogin.add(row.getLogin());
            if (row.getName() != null && !row.getName().isEmpty()) uniqueNames.add(row.getName());
            if (row.getSurname() != null && !row.getSurname().isEmpty()) uniqueSurnames.add(row.getSurname());
            uniquePasswords.add(row.getPassword());
            if (row.getEmail() != null && !row.getEmail().isEmpty()) uniqueEmails.add(row.getEmail());
        }
        updateUserID.getItems().setAll(uniqueID);
        UserIDComboBox.getItems().setAll(uniqueID);
        UserLoginComboBox.getItems().setAll(uniqueLogin);
        TrashInfoComboBoxIDUser.getItems().setAll(uniqueID);
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
