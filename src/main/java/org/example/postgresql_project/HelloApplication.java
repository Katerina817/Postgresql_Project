package org.example.postgresql_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.postgresql_project.Entities.*;
import org.example.postgresql_project.EntitiesControl.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class HelloApplication extends Application {
    private static final String USERS_FILE = "users.txt";
    private static final String ADMINS_FILE = "admins.txt";
    private static HashMap<String, String> users = new HashMap<>();
    private static HashMap<String, String> admins = new HashMap<>();
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Вход в систему");

        // Создаем интерфейс
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Поля ввода
        Label loginLabel = new Label("Логин:");
        grid.add(loginLabel, 0, 1);
        TextField loginField = new TextField();
        grid.add(loginField, 1, 1);

        Label passwordLabel = new Label("Пароль:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        // Кнопки
        Button loginButton = new Button("Войти");
        Button authButton = new Button("Авторизация");
        VBox buttonBox = new VBox(10, loginButton, authButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 1, 4);

        // Радиокнопки для выбора роли
        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton adminRadioButton = new RadioButton("Админ");
        adminRadioButton.setToggleGroup(roleGroup);
        RadioButton userRadioButton = new RadioButton("Пользователь");
        userRadioButton.setToggleGroup(roleGroup);
        grid.add(adminRadioButton, 1, 3);
        grid.add(userRadioButton, 2, 3);
        Connection con=connect_to_db("recyclingdb","postgres","");
        // Обработчик для кнопки "Войти"
        loginButton.setOnAction(e -> {
            /*СЮДА ВСТАВЛЯЕМ ДЛЯ ТЕСТИРОВАНИЯ КЕК*/



            /*Report r=new Report();
            r.setReportTypeId("0b344983-e87a-48c8-bdb9-450bb7a6bcfd");
            r.setAdminId("2a994fab-7b4b-416a-bd59-69ef191c3b90");
            r.setContent("aaaaaaaaa");
            r.setRecyclingId("0d1b8d3a-bc1e-4a25-b990-aa7cba4988bc");
            String dateString = "2024-11-08";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date utilDate = dateFormat.parse(dateString); // Преобразуем строку в объект Date
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                r.setReportDate(sqlDate); // Устанавливаем дату
            } catch (ParseException ep) {
                ep.printStackTrace(); // Обработка ошибки преобразования
            }
            ReportControl rC=new ReportControl(con);
            try {
                rC.insertReport(r);
            } catch (SQLException et) {
                et.printStackTrace();
                System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
            }*/

            /*Recycling r=new Recycling();
            r.setRecyclingStatusId("4575fcad-f246-46c7-a929-6b6af491190d");
            r.setRuleId("12e38941-5cc6-4c29-b188-997388472560");
            r.setTrashInfoId("7e160b7c-db1b-4dc7-b4b8-72d545e31004");
            String dateString = "2024-11-08";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date utilDate = dateFormat.parse(dateString); // Преобразуем строку в объект Date
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                r.setRecyclingDate(sqlDate); // Устанавливаем дату
            } catch (ParseException ep) {
                ep.printStackTrace(); // Обработка ошибки преобразования
            }
            RecyclingControl rC=new RecyclingControl(con);
            try {
                rC.insertRecycling(r);
            } catch (SQLException et) {
                et.printStackTrace();
                System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
            }*/
            /*TrashInfo r=new TrashInfo();
            r.setUserId("9a4b72d1-6ea0-425a-8a87-b08e13f44ff3");
            r.setTrashTypeId("c386b62d-d133-4af2-9f16-da3b6d0433e0");
            r.setTrashQuantity(55);
            TrashInfoControl rC=new TrashInfoControl(con);
            try {
                rC.insertTrashInfo(r);
            } catch (SQLException et) {
                et.printStackTrace();
                System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
            }*/

            /*RecyclingRule r=new RecyclingRule();
            RecyclingRuleControl rC=new RecyclingRuleControl(con);
            try {
                rC.insertRecyclingRule(r);
            } catch (SQLException et) {
                et.printStackTrace();
                System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
            }*/


            /*RecyclingStatus r=new RecyclingStatus();
            r.setRecyclingStatusName("kkk");
            r.setCurrentProcessDescription("aaa");
            RecyclingStatusControl rC=new RecyclingStatusControl(con);
            try {
                rC.insertRecyclingStatus(r);
            } catch (SQLException et) {
                et.printStackTrace();
                System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
            }*/
            /*User admin = new User();
            //admin.setAdminId("1234");
            admin.setLogin("111111111111111");
            admin.setName("bh");
            admin.setSurname("Doe");
            //admin.setPassword("");

            UserControl adminC = new UserControl(con);
            try {
                adminC.insertUser(admin);
            } catch (SQLException et) {
                et.printStackTrace();
                System.err.println("Ошибка при добавлении gjkmpjdfntkmz: " + et.getMessage());
            }*/
            /*TrashType r=new TrashType();
            r.setTrashTypeName("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
            TrashTypeControl rC=new TrashTypeControl(con);
            try {
                rC.insertReportType(r);
            } catch (SQLException et) {
                et.printStackTrace();
                System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
            }*/

            /*ReportType r=new ReportType();
            r.setReportTypeName("k");
            ReportTypeControl rC=new ReportTypeControl(con);
            try {
                rC.insertReportType(r);
            } catch (SQLException et) {
                et.printStackTrace();
                System.err.println("Ошибка при добавлении типа отчета: " + et.getMessage());
            }*/


            /*Admin admin = new Admin();
            //admin.setAdminId("1234");
            admin.setLogin("11111111111111111111111111111111111111111111111");
            admin.setName("bh");
            admin.setSurname("Doe");
            admin.setPassword("password123");
            admin.setEmail("john.doe@example.com");
            admin.setAge(15);
            admin.setBirthYear(2020);

            AdminControl adminC = new AdminControl(con);
            try {
                adminC.insertAdmin(admin);
            } catch (SQLException et) {
                et.printStackTrace();
                System.err.println("Ошибка при добавлении администратора: " + et.getMessage());
            }*/



            String login = loginField.getText();
            String password = passwordField.getText();
            loginField.clear();
            passwordField.clear();

            if (adminRadioButton.isSelected()) {
                if (admins.containsKey(login) && admins.get(login).equals(password)) {
                    showMessage("Добро пожаловать, админ " + login + "!");
                    primaryStage.close();
                    //openTablesWindow(0);
                } else {
                    showMessage("Неверный логин или пароль :(");
                }
            } else if (userRadioButton.isSelected()) {
                if (users.containsKey(login) && users.get(login).equals(password)) {
                    showMessage("Добро пожаловать, пользователь " + login + "!");
                    primaryStage.close();
                    //openTablesWindow(1);
                } else {
                    showMessage("Неверный логин или пароль :(");
                }
            } else {
                showMessage("Пожалуйста, выберите роль");
            }
        });

        // Обработчик для кнопки "Авторизация"
        authButton.setOnAction(e -> {
            String login = loginField.getText();
            String password = passwordField.getText();
            loginField.clear();
            passwordField.clear();

            if (adminRadioButton.isSelected()) {
                if (admins.containsKey(login)) {
                    showMessage("Пользователь с таким логином уже существует");
                } else {
                    admins.put(login, password);
                    showMessage("Авторизация с ролью администратор прошла успешно!");
                    saveUsers(ADMINS_FILE, login, password);
                }
            } else if (userRadioButton.isSelected()) {
                if (users.containsKey(login)) {
                    showMessage("Пользователь с таким логином уже существует");
                } else {
                    users.put(login, password);
                    showMessage("Авторизация с ролью пользователь прошла успешно!");
                    saveUsers(USERS_FILE, login, password);
                }
            } else {
                showMessage("Пожалуйста, выберите роль");
            }
        });

        Scene scene = new Scene(grid, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        loadUsers(USERS_FILE, users);
        loadUsers(ADMINS_FILE, admins);
        launch(args);
    }
    private static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private static void loadUsers(String filename, HashMap<String, String> map) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    map.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + filename);
            throw new RuntimeException(e);}
    }

    //для сохранения данных пользователей в файл
    private static void saveUsers(String filename, String key, String value) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.write(key + ":" + value);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + filename);
        }
    }
    //класс подключения
    public static Connection connect_to_db(String dbname, String user, String pass)
    {
        Connection con_obj=null;
        String url="jdbc:postgresql://localhost:5432/";
        try
        {
            con_obj= DriverManager.getConnection(url+dbname,user,pass);
            if(con_obj!=null)
            {
                System.out.println("Connection established successfully !");
            }
            else
            {
                System.out.println("Connection failed !!");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return con_obj;
    }
}