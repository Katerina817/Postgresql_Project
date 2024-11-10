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
import java.util.Objects;
import java.util.UUID;

public class AdminControl {
    private Connection connection;
    private CheckClass admin_check= new CheckClass();
    public AdminControl(Connection connection){
        this.connection=connection;
    }

    //Метод для добавления записи в таблицу админ
    public boolean insertAdmin(@NonNull Admin admin) throws SQLException {
        try{
            admin_check.CheckAdmin(admin);
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка","Неверная длина",e.getMessage());
            return false;
        }
        try {
            if (!isLoginUnique(admin.getLogin())) {
                new ErrorClass().startError("Ошибка","Логин уже существует","Пожалуйста, выберите другой логин.");
                return false;
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при проверке уникальности названия", e.getMessage());
            return false;
        }
        admin.setAdminId(UUID.randomUUID().toString());
        String sql = "INSERT INTO admin (admin_id, login, name, surname, password, email, age, birth_year) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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
            return false;
        }
        return true;
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



    //Метод для удаления записи из таблицы админ
    public boolean deleteAdminByColumnName(String column_name,Object value) {
        String selectSql = "SELECT admin_id FROM admin WHERE " + column_name + " = ?";
        String sql = "DELETE FROM admin WHERE "+column_name+" = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if(Objects.equals(column_name, "age") || Objects.equals(column_name, "birth_age")){
                try {
                    int intValue = Integer.parseInt(value.toString());
                    selectStatement.setInt(1, intValue);
                    statement.setInt(1, intValue);
                } catch (NumberFormatException e) {
                    new ErrorClass().startError("Ошибка", "Неверный тип данных", "Значение должно быть целым числом");
                    return false;
                }
            } else {
                selectStatement.setString(1, value.toString());
                statement.setString(1, value.toString());
            }

            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                String adminId = resultSet.getString("admin_id");
                ReportControl reportControl = new ReportControl(connection);
                reportControl.deleteReportByColumnName("admin_id", adminId);
            }
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при удалении админа",e.getMessage());
            return false;
        }
    }


    //Метод для обновления записи в таблице админ по admin_id
    public boolean updateAdminField(String adminId, String columnName, Object newValue) {
        try {
            admin_check.validateAdminForUpdate(columnName, newValue.toString());
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка", "Неверная длина строки", e.getMessage());
            return false;
        }
        if (columnName.equals("login")) {
            try {
                if (!isLoginUnique(newValue.toString())) {
                    new ErrorClass().startError("Ошибка", "Логин уже существует", "Пожалуйста, выберите другой логин.");
                    return false;
                }
            } catch (SQLException e) {
                new ErrorClass().startError("Ошибка", "Ошибка при проверке уникальности названия", e.getMessage());
                return false;
            }
        }
        String sql = "UPDATE admin SET " + columnName + " = ? WHERE admin_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (Objects.equals(columnName, "age") || Objects.equals(columnName, "birth_year")) {
                try {
                    int intValue = Integer.parseInt(newValue.toString());
                    statement.setInt(1, intValue);
                } catch (NumberFormatException e) {
                    new ErrorClass().startError("Ошибка", "Неверный тип данных", "Значение должно быть целым числом");
                    return false;
                }
            } else {
                statement.setString(1, newValue.toString());
            }
            statement.setString(2, adminId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при обновлении записи", e.getMessage());
            return false;
        }
    }

}
