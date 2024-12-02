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
import lombok.extern.java.Log;
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
import java.util.List;
import java.util.Map;

@Log
public class HelloApplication extends Application {
    private class Помощь {
        public void помочь() {
            log.info("помощь оказана");
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Object помогите;

        помогите = new Помощь();
        if (помогите instanceof Помощь) {
            ((Помощь) помогите).помочь();
        }

        Connection con=connect_to_db("recyclingdb","postgres","");
        DataBaseConnection.setConnection(con);
        /*ProceduresAndFunctions pr= new ProceduresAndFunctions(con);
        try{
            List<ReportAndAdmin> reportAndAdmins = pr.getReportsByType("8");
            if (reportAndAdmins.isEmpty()) {
                System.out.println("Список пуст");
            }
            // Выводим отчеты
            for (ReportAndAdmin report : reportAndAdmins) {
                System.out.println("reportId: " + report.getReportId());
                System.out.println("content: " + report.getContent());
                System.out.println("Report Date: " + report.getReportDate());
                System.out.println("Name: " + report.getName());
                System.out.println("Surname: " + report.getSurname());
                System.out.println("------");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Вход в систему");
    }
    public static void main(String[] args) {
        launch(args);
    }
    private static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
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