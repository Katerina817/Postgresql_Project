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
import java.util.*;

public class AdminControl {
    private final Connection connection;
    private final CheckClass admin_check= new CheckClass();
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
            if (e.getMessage().contains("Age") && e.getMessage().contains("cannot be greater than or equal to Birth Year")) {
                new ErrorClass().startError("Ошибка","Возраст не может быть больше или равен году рождения.");
            } else {
                new ErrorClass().startError("Ошибка","Ошибка при вставке пользователя",e.getMessage());
            }
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
        if(columnName.equals("admin_id")){
            new ErrorClass().startError("Ошибка", "Нельзя изменить значение ID");
            return false;
        }
        try{
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




    //метод для поиска администратора по нескольким параметрам
    public List<Admin> searchAdminByParameters(Map<String, Object> params) {
        List<Admin> admins = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM admin WHERE ");
        List<Object> values = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String column = entry.getKey();
            Object value = entry.getValue();
            if (value.equals("null") ) {
                sql.append(column).append(" IS NULL OR ").append(column).append(" = '' AND ");
            } else {
                sql.append(column).append(" = ? AND ");
                values.add(value);
            }
        }
        sql.delete(sql.length() - 4, sql.length());
        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object value : values) {
                if (value instanceof Integer) {
                    statement.setInt(index++, (Integer) value);
                } else {
                    statement.setString(index++, value.toString()); //to retest
                }
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Admin admin = new Admin();
                /*admins.add(new Admin.AdminBuilder()
                        .adminId()
                        .login()
                                .
                        .build());*/
                admin.setAdminId(resultSet.getString(1));
                admin.setLogin(resultSet.getString(2));
                admin.setName(resultSet.getString(3));
                admin.setSurname(resultSet.getString(4));
                admin.setPassword(resultSet.getString(5));
                admin.setEmail(resultSet.getString(6));
                admin.setAge(resultSet.getInt(7));
                admin.setBirthYear(resultSet.getInt(8));
                admins.add(admin);
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при выполнении поиска", e.getMessage());
        }
        return admins;
    }
}
