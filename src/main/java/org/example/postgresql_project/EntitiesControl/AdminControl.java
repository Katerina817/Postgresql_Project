package org.example.postgresql_project.EntitiesControl;

import lombok.NonNull;
import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.Admin;
import org.example.postgresql_project.ErrorClass;
import org.example.postgresql_project.InvalidLengthException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AdminControl {
    private Connection connection;
    private CheckClass admin_check= new CheckClass();
    public AdminControl(Connection connection){
        this.connection=connection;
    }

    //Метод для добавления записи в таблицу админ
    public void insertAdmin(@NonNull Admin admin) throws SQLException {
        try{
            admin_check.CheckAdmin(admin);
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка","Неверная длина",e.getMessage());
            return;
        }

        if (!isLoginUnique(admin.getLogin())) {
            new ErrorClass().startError("Ошибка","Логин уже существует","Пожалуйста, выберите другой логин.");
            return;
        }
        admin.setAdminId(UUID.randomUUID().toString());
        String sql = "INSERT INTO admin (admin_id, login, name, surname, password, email, age, birth_year, tel_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, admin.getAdminId());
            statement.setString(2, admin.getLogin());
            statement.setString(3, admin.getName() != null ? admin.getName() : "");
            statement.setString(4, admin.getSurname() != null ? admin.getSurname() : "");
            statement.setString(5, admin.getPassword());
            statement.setString(6, admin.getEmail() != null ? admin.getEmail() : "");
            statement.setInt(7, admin.getAge());
            statement.setInt(8, admin.getBirthYear());
            statement.executeUpdate();
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вставке пользователя",e.getMessage());
        }
    }
    //метод для проверки уникальности логина
    private boolean isLoginUnique(String login) throws SQLException {
        String sql = "SELECT COUNT(*) FROM admin WHERE login = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
            return true;
        }
    }
}
