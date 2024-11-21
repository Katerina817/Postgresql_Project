package org.example.postgresql_project.FXControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.postgresql_project.DataBaseConnection;
import org.example.postgresql_project.Entities.Admin;
import org.example.postgresql_project.Entities.User;
import org.example.postgresql_project.EntitiesControl.AdminControl;
import org.example.postgresql_project.EntitiesControl.UserControl;
import org.example.postgresql_project.ErrorClass;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController {
    @FXML
    private TextField LoginTextField;
    @FXML
    private PasswordField PasswordTextField;
    @FXML
    private RadioButton AdminRadioButton;
    @FXML
    private RadioButton UserRadioButton;
    @FXML
    private ToggleGroup toggleGroup1;
    private final ErrorClass errorClass=new ErrorClass();

    public void initialize() {
        toggleGroup1.selectToggle(AdminRadioButton);
    }
    @FXML
    protected void onEnterButtonClick()throws IOException {
        String login=LoginTextField.getText();
        String password=PasswordTextField.getText();
        if(login.isEmpty()|| password.isEmpty()){
            errorClass.startError("Ошибка ввода", "Все поля должны быть заполнены");
        }
        else if(UserRadioButton.isSelected()){
            UserControl userControl=new UserControl(DataBaseConnection.getConnection());
            Map<String, Object> params = new HashMap<>();
            params.put("login",login);
            params.put("password",password);
            List<User> res=userControl.searchUserByParameters(params);
            if(res.isEmpty()){
                LoginTextField.setText("");
                PasswordTextField.setText("");
                errorClass.startError("Ошибка поиска", "Пользователь с такими данными не найден","Пожалуйста, проверьте корректность введенных данных");
            }
            else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/postgresql_project/mainPage.fxml"));
                Parent root = loader.load();
                MainPageController mainPageController = loader.getController();
                mainPageController.setInfo(true);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
        }
        else{
            AdminControl adminControl=new AdminControl(DataBaseConnection.getConnection());
            Map<String, Object> params = new HashMap<>();
            params.put("login",login);
            params.put("password",password);
            List<Admin> res=adminControl.searchAdminByParameters(params);
            if(res.isEmpty()){
                LoginTextField.setText("");
                PasswordTextField.setText("");
                errorClass.startError("Ошибка поиска", "Администратор с такими данными не найден","Пожалуйста, проверьте корректность введенных данных");
            }
            else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/postgresql_project/mainPage.fxml"));
                Parent root = loader.load();
                MainPageController mainPageController = loader.getController();
                mainPageController.setInfo(false);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
        }
    }
    @FXML
    protected void onAuthorizationButtonClick()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/postgresql_project/authorization.fxml"));
        Parent root = loader.load();
        AuthorizationController authorizationController = loader.getController();
        if(UserRadioButton.isSelected()){
            authorizationController.setInfo(true);
        }
        else {
            authorizationController.setInfo(false);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}