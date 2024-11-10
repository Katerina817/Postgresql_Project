package org.example.postgresql_project.EntitiesControl;

import lombok.NonNull;
import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.Recycling;
import org.example.postgresql_project.ErrorClass;
import org.example.postgresql_project.InvalidLengthException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class RecyclingControl {
    private Connection connection;
    private CheckClass recycling_check= new CheckClass();
    public RecyclingControl(Connection connection){
        this.connection=connection;
    }

    //Метод для добавления записи в таблицу recycling
    public boolean insertRecycling(@NonNull Recycling recycling) throws SQLException {
        try{
            recycling_check.CheckRecycling(recycling);
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка","Неверная длина",e.getMessage());
            return false;
        }
        try {
            if (!checkStatusExists(recycling.getRecyclingStatusId()) || !checkRuleExists(recycling.getRuleId())|| !checkTrashInfoExists(recycling.getTrashInfoId())) {
                new ErrorClass().startError("Ошибка", "Статус, правило или информация о мусоре не найдена в базе данных");
                return false;
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при проверке существования элементов", e.getMessage());
            return false;
        }
        recycling.setRecyclingId(UUID.randomUUID().toString());
        String sql = "INSERT INTO recycling (recycling_id, recycling_status_id,rule_id,trash_info_id,recycling_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, recycling.getRecyclingId());
            statement.setString(2, recycling.getRecyclingStatusId());
            statement.setString(3, recycling.getRuleId());
            statement.setString(4, recycling.getTrashInfoId());
            statement.setDate(5, recycling.getRecyclingDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вставке информации о переработке",e.getMessage());
            return false;
        }
        return true;
    }

    //Метод для удаления записи из таблицы recycling
    public boolean deleteRecyclingByColumnName(String column_name,Object value) {
        String selectSql = "SELECT recycling_id FROM recycling WHERE " + column_name + " = ?";
        String sql = "DELETE FROM recycling WHERE "+column_name+" = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if(Objects.equals(column_name, "recycling_date")){
                try {
                    java.sql.Date sqlDate = java.sql.Date.valueOf(value.toString());
                    selectStatement.setDate(1, sqlDate);
                    statement.setDate(1, sqlDate);
                } catch (IllegalArgumentException e) {
                    new ErrorClass().startError("Ошибка", "Неверный тип данных", "Значение должно соответствовать формату yyyy-MM-dd");
                    return false;
                }
            } else {
                selectStatement.setString(1, value.toString());
                statement.setString(1, value.toString());
            }
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                String recyclingId = resultSet.getString("recycling_id");
                ReportControl reportControl = new ReportControl(connection);
                reportControl.deleteReportByColumnName("recycling_id", recyclingId);
            }
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {

            new ErrorClass().startError("Ошибка","Ошибка при удалении записи о переработке",e.getMessage());
            return false;
        }
    }


    public boolean updateRecyclingField(String recyclingId, String columnName, Object newValue) {
        try {
            recycling_check.validateRecyclingForUpdate(columnName, newValue.toString());
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка", "Неверная длина строки", e.getMessage());
            return false;
        }
        try {
            if (columnName.equals("recycling_status_id") && !checkStatusExists(newValue.toString())) {
                new ErrorClass().startError("Ошибка", "Статус не найден", "Указанный статус не существует.");
                return false;
            }
            if (columnName.equals("rule_id") && !checkRuleExists(newValue.toString())) {
                new ErrorClass().startError("Ошибка", "Правило не найдено", "Указанное правило не существует.");
                return false;
            }
            if (columnName.equals("trash_info_id") && !checkTrashInfoExists(newValue.toString())) {
                new ErrorClass().startError("Ошибка", "Информация о мусоре не найдена", "Указанная информация о мусоре не существует.");
                return false;
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при проверке статуса", e.getMessage());
            return false;
        }

        String sql = "UPDATE recycling SET " + columnName + " = ? WHERE recycling_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (columnName.equals("recycling_date")) {
                try {
                    java.sql.Date sqlDate = java.sql.Date.valueOf(newValue.toString());
                    statement.setDate(1, sqlDate);
                } catch (IllegalArgumentException e) {
                    new ErrorClass().startError("Ошибка", "Неверный формат даты", "Значение должно соответствовать формату yyyy-MM-dd");
                    return false;
                }
            } else {
                statement.setString(1, newValue.toString());
            }
            statement.setString(2, recyclingId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при обновлении записи", e.getMessage());
            return false;
        }
    }
    // метод для проверки существования статуса
    private boolean checkStatusExists(String recyclingStatusId) throws SQLException {
        String sql = "SELECT 1 FROM recycling_status WHERE recycling_status_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, recyclingStatusId);
            return statement.executeQuery().next();
        }
    }
    //метод для проверки существования правила переработки
    private boolean checkRuleExists(String ruleId) throws SQLException {
        String sql = "SELECT 1 FROM recycling_rule WHERE rule_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ruleId);
            return statement.executeQuery().next();
        }
    }
    //метод для проверки существования информации о мусоре
    private boolean checkTrashInfoExists(String trashInfoId) throws SQLException {
        String sql = "SELECT 1 FROM trash_info WHERE trash_info_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trashInfoId);
            return statement.executeQuery().next();
        }
    }

}
