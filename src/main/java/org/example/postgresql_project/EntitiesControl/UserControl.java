package org.example.postgresql_project.EntitiesControl;

import lombok.NonNull;
import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.User;
import org.example.postgresql_project.ErrorClass;
import org.example.postgresql_project.InvalidLengthException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserControl {
    private Connection connection;
    private CheckClass user_check= new CheckClass();
    public UserControl(Connection connection){
        this.connection=connection;
    }

    //Метод для добавления записи в таблицу users
    public boolean insertUser(@NonNull User user) throws SQLException {
        try{
            user_check.CheckUser(user);
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка","Неверная длина",e.getMessage());
            return false;
        }

        if (!isLoginUnique(user.getLogin())) {
            new ErrorClass().startError("Ошибка","Логин уже существует","Пожалуйста, выберите другой логин.");
            return false;
        }
        user.setUserId(UUID.randomUUID().toString());
        String sql = "INSERT INTO users (user_id, login, name, surname, password, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserId());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getName() != null ? user.getName() : "");
            statement.setString(4, user.getSurname() != null ? user.getSurname() : "");
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getEmail() != null ? user.getEmail() : "");
            statement.executeUpdate();
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вставке пользователя",e.getMessage());
            return false;
        }
        return true;
    }
    //метод для проверки уникальности логина
    private boolean isLoginUnique(String login) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE login = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
            return true;
        }
    }
    public User getUserById(String userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getString("user_id"));
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                return user;
            } else {
                throw new SQLException("Пользователь с ID " + userId + " не найден.");
            }
        }
    }



    //Метод для удаления записи из таблицы users
    public boolean deleteUsersByColumnName(String column_name,String value) {
        String selectSql = "SELECT user_id FROM users WHERE " + column_name + " = ?";
        String sql = "DELETE FROM users WHERE "+column_name+" = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            selectStatement.setString(1, value);
            statement.setString(1, value);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                TrashInfoControl trashInfoControl = new TrashInfoControl(connection);
                trashInfoControl.deleteTrashInfoByColumnName("user_id", userId);
            }
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при удалении записи о пользователе",e.getMessage());
            return false;
        }
    }
}
