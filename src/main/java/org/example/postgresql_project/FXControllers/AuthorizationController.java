package org.example.postgresql_project.FXControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.postgresql_project.DataBaseConnection;
import org.example.postgresql_project.Entities.Admin;
import org.example.postgresql_project.Entities.User;
import org.example.postgresql_project.EntitiesControl.AdminControl;
import org.example.postgresql_project.EntitiesControl.UserControl;
import org.example.postgresql_project.ErrorClass;

import java.sql.SQLException;

public class AuthorizationController {
    private Boolean user=true;// false-админ;true-пользователь
    @FXML
    private TextField loginField,nameField,surnameField,emailField;
    @FXML
    private ComboBox<Integer> ageField,birthYearField;
    @FXML
    private PasswordField passwordField,passwordField2;
    @FXML
    private Label age,birthyear;
    @FXML
    private ProgressBar progressBar;
    private void updateProgressBar() {
        int totalFields = 8;
        if(user) totalFields = 6;
        int filledFields = 0;
        if (!loginField.getText().isEmpty()) filledFields++;
        if (!nameField.getText().isEmpty()) filledFields++;
        if (!surnameField.getText().isEmpty()) filledFields++;
        if (!emailField.getText().isEmpty()) filledFields++;
        if(!user){
            if (!(ageField.getValue()==null)) filledFields++;
            if (!(birthYearField.getValue()==null)) filledFields++;
        }
        if (!passwordField.getText().isEmpty()) filledFields++;
        if (!passwordField2.getText().isEmpty()) filledFields++;
        double progress = (double) filledFields / totalFields;
        progressBar.setProgress(progress);
    }
    @FXML
    public void initialize() {
        for (int i = 5; i <= 100; i++) {
            ageField.getItems().add(i);
        }
        for (int i = 1900; i <= 2024; i++) {
            birthYearField.getItems().add(i);
        }
        loginField.textProperty().addListener((obs, oldText, newText) -> updateProgressBar());
        nameField.textProperty().addListener((obs, oldText, newText) -> updateProgressBar());
        surnameField.textProperty().addListener((obs, oldText, newText) -> updateProgressBar());
        emailField.textProperty().addListener((obs, oldText, newText) -> updateProgressBar());
        ageField.valueProperty().addListener((obs, oldText, newText) -> updateProgressBar());
        birthYearField.valueProperty().addListener((obs, oldText, newText) -> updateProgressBar());
        passwordField.textProperty().addListener((obs, oldText, newText) -> updateProgressBar());
        passwordField2.textProperty().addListener((obs, oldText, newText) -> updateProgressBar());
        updateProgressBar();
    }
    @FXML
    protected void onNextPage() {
        Boolean flag=false;
        if(loginField.getText().isEmpty() || passwordField.getText().isEmpty()){
            new ErrorClass().startError("Ошибка", "Поля логин и пароль не могут быть пустыми","Заполните необходимые поля для авторизации");
            return;
        }
        if(!passwordField.getText().equals(passwordField2.getText())){
            new ErrorClass().startError("Ошибка", "Поля пароль и пароль(2) должны совпадать");
            return;
        }
        //new ErrorClass().startError("ВСЕ ОК", "ААААААААААААААААААААА");
        if(user){
            User user1 = User.builder()
                    .login(loginField.getText())
                    .name(nameField.getText())
                    .surname(surnameField.getText())
                    .password(passwordField.getText())
                    .email(emailField.getText())
                    .build();
            UserControl userControl = new UserControl(DataBaseConnection.getConnection());
            try {
                flag = userControl.insertUser(user1);
            } catch (SQLException et) {
                new ErrorClass().startError("Ошибка", "Ошибка при добавлении пользователя: " + et.getMessage());
                return;
            }
        }
        else{
            /*if(ageField.getValue()==null || birthYearField.getValue()==null){
                new ErrorClass().startError("Ошибка", "Поля возраст и год рождения не могут быть пустыми","Заполните необходимые поля для авторизации");
                return;
            }*/
            Admin admin1 = Admin.builder()
                    .login(loginField.getText())
                    .name(nameField.getText())
                    .surname(surnameField.getText())
                    .password(passwordField.getText())
                    .email(emailField.getText())
                    .age(ageField.getValue())
                    .birthYear(birthYearField.getValue())
                    .build();
            AdminControl adminControl = new AdminControl(DataBaseConnection.getConnection());
            try {
                flag = adminControl.insertAdmin(admin1);
            } catch (SQLException et) {
                new ErrorClass().startError("Ошибка", "Ошибка при добавлении админа: " + et.getMessage());
                return;
            }
        }
        if(flag){
            new ErrorClass().startSuccess("Успех", "Добавление пользователя прошло успешно");
        }
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.close();
    }

    public void setInfo(Boolean info) {
        this.user = info;
        if (user) {
            ageField.setVisible(false);
            birthYearField.setVisible(false);
            age.setVisible(false);
            birthyear.setVisible(false);
        }
        updateProgressBar();
    }
}
