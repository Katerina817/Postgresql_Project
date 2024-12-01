package org.example.postgresql_project.EntitiesControl;

import lombok.NonNull;
import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.RecyclingRule;
import org.example.postgresql_project.Entities.RecyclingStatus;
import org.example.postgresql_project.ErrorClass;
import org.example.postgresql_project.InvalidLengthException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RecyclingStatusControl {
    private final Connection connection;
    private CheckClass recyclingStatus_check= new CheckClass();
    public RecyclingStatusControl(Connection connection){
        this.connection=connection;
    }

    //Метод для добавления записи в таблицу recycling_status
    public boolean insertRecyclingStatus(@NonNull RecyclingStatus recyclingStatus) throws SQLException {
        try{
            recyclingStatus_check.CheckRecyclingStatus(recyclingStatus);
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка","Неверная длина",e.getMessage());
            return false;
        }
        try {
            if (!isStatusNameUnique(recyclingStatus.getRecyclingStatusName())) {
                new ErrorClass().startError("Ошибка","Название уже существует","Пожалуйста, выберите другое название.");
                return false;
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при проверке уникальности", e.getMessage());
            return false;
        }
        recyclingStatus.setRecyclingStatusId(UUID.randomUUID().toString());
        String sql = "INSERT INTO recycling_status (recycling_status_id, recycling_status_name, current_process_description) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, recyclingStatus.getRecyclingStatusId());
            statement.setString(2, recyclingStatus.getRecyclingStatusName());
            statement.setString(3, recyclingStatus.getCurrentProcessDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вставке статуса переработки",e.getMessage());
            return false;
        }
        return true;
    }
    //метод для проверки уникальности названия
    private boolean isStatusNameUnique(String recycling_status_name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM recycling_status WHERE recycling_status_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, recycling_status_name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
            return true;
        }
    }



    //Метод для удаления записи из таблицы recycling_status
    public boolean deleteRecyclingStatusByColumnName(String column_name,String value) {
        String selectSql = "SELECT recycling_status_id FROM recycling_status WHERE " + column_name + " = ?";
        String sql = "DELETE FROM recycling_status WHERE "+column_name+" = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            selectStatement.setString(1, value);
            statement.setString(1, value);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                String recyclingStatusId = resultSet.getString("recycling_status_id");
                RecyclingControl recyclingControl = new RecyclingControl(connection);
                recyclingControl.deleteRecyclingByColumnName("recycling_status_id", recyclingStatusId);
            }
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при удалении записи о статусе переработки",e.getMessage());
            return false;
        }
    }

    // Метод для обновления записи в таблице recycling_status по recycling_status_id
    public boolean updateRecyclingStatusField(String recyclingStatusId, String columnName, Object newValue) {
        if(columnName.equals("recycling_status_id")){
            new ErrorClass().startError("Ошибка", "Нельзя изменить значение ID");
            return false;
        }
        try {
            recyclingStatus_check.validateRecyclingStatusForUpdate(columnName, newValue.toString());
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка", "Неверная длина строки", e.getMessage());
            return false;
        }
        if (columnName.equals("recycling_status_name")) {
            try {
                if (!isStatusNameUnique(newValue.toString())) {
                    new ErrorClass().startError("Ошибка", "Название уже существует", "Пожалуйста, выберите другое название.");
                    return false;
                }
            } catch (SQLException e) {
                new ErrorClass().startError("Ошибка", "Ошибка при проверке уникальности названия", e.getMessage());
                return false;
            }
        }
        String sql = "UPDATE recycling_status SET " + columnName + " = ? WHERE recycling_status_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newValue.toString());
            statement.setString(2, recyclingStatusId);
            int rowsAffected = statement.executeUpdate();
            new ErrorClass().startSuccess("Успех", "Запись успешно обновлена");
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при обновлении записи", e.getMessage());
            return false;
        }
    }




    //метод для поиска RecyclingStatus по нескольким параметрам
    public List<RecyclingStatus> searchRecyclingStatusByParameters(Map<String, Object> params) {
        List<RecyclingStatus> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM recycling_status WHERE ");
        List<Object> values = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String column = entry.getKey();
            Object value = entry.getValue();
            sql.append(column).append(" = ? AND ");
            values.add(value);
        }
        sql.delete(sql.length() - 4, sql.length()-1); //8 0-7
        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object value : values) {
                statement.setString(index++, value.toString());
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                RecyclingStatus recyclingStatus = new RecyclingStatus();
                recyclingStatus.setRecyclingStatusId(resultSet.getString("recycling_status_id"));
                recyclingStatus.setRecyclingStatusName(resultSet.getString("recycling_status_name"));
                recyclingStatus.setCurrentProcessDescription(resultSet.getString("current_process_description"));
                results.add(recyclingStatus);
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при выполнении поиска", e.getMessage());
        }
        return results;
    }


}
