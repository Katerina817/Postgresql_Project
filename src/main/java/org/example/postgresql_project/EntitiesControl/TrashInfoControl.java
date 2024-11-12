package org.example.postgresql_project.EntitiesControl;

import lombok.NonNull;
import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.Recycling;
import org.example.postgresql_project.Entities.TrashInfo;
import org.example.postgresql_project.ErrorClass;
import org.example.postgresql_project.InvalidLengthException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TrashInfoControl {
    private Connection connection;
    private CheckClass trash_info_check= new CheckClass();
    public TrashInfoControl(Connection connection){
        this.connection=connection;
    }

    //Метод для добавления записи в таблицу trash_info
    public boolean insertTrashInfo(@NonNull TrashInfo trashInfo) throws SQLException {
        try{
            trash_info_check.CheckTrashInfo(trashInfo);
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка","Неверная длина",e.getMessage());
            return false;
        }
        try {
            if (!checkUserExists(trashInfo.getUserId()) || !checkTrashTypeExists(trashInfo.getTrashTypeId())) {
                new ErrorClass().startError("Ошибка", "Пользователь или тип мусора не найден в базе данных");
                return false;
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при проверке существования элементов", e.getMessage());
            return false;
        }
        trashInfo.setTrashInfoId(UUID.randomUUID().toString());
        String sql = "INSERT INTO trash_info (trash_info_id, user_id,trash_type_id,trash_quantity) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trashInfo.getTrashInfoId());
            statement.setString(2, trashInfo.getUserId());
            statement.setString(3, trashInfo.getTrashTypeId());
            statement.setInt(4, trashInfo.getTrashQuantity());
            statement.executeUpdate();
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вставке информации о мусоре",e.getMessage());
            return false;
        }
        return true;
    }
    // метод для проверки существования пользователя
    private boolean checkUserExists(String userId) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            return statement.executeQuery().next();
        }
    }
    //метод для проверки существования типа мусора
    private boolean checkTrashTypeExists(String trashTypeId) throws SQLException {
        String sql = "SELECT 1 FROM trash_type WHERE trash_type_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trashTypeId);
            return statement.executeQuery().next();
        }
    }





    //Метод для удаления записи из таблицы trash_info
    public boolean deleteTrashInfoByColumnName(String column_name,Object value) {
        String selectSql = "SELECT trash_info_id FROM trash_info WHERE " + column_name + " = ?";
        String sql = "DELETE FROM trash_info WHERE "+column_name+" = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if(Objects.equals(column_name, "trash_quantity")){
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
                String trashInfoId = resultSet.getString("trash_info_id");
                RecyclingControl recyclingControl = new RecyclingControl(connection);
                recyclingControl.deleteRecyclingByColumnName("trash_info_id", trashInfoId);
            }
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при удалении записи об информации о мусоре",e.getMessage());
            return false;
        }
    }


    // Метод для обновления записи в таблице trash_info по trash_info_id
    public boolean updateTrashInfoField(String trashInfoId, String columnName, Object newValue) {
        try {
            trash_info_check.validateTrashInfoForUpdate(columnName, newValue.toString());
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка", "Неверная длина строки", e.getMessage());
            return false;
        }
        try {
            if (columnName.equals("user_id") && !checkUserExists(newValue.toString())) {
                new ErrorClass().startError("Ошибка", "Пользователь не найден", "Указанный пользователь не существует.");
                return false;
            }
            if (columnName.equals("trash_type_id") && !checkTrashTypeExists(newValue.toString())) {
                new ErrorClass().startError("Ошибка", "Тип мусора не найден", "Указанный тип мусора не существует.");
                return false;
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при проверке данных", e.getMessage());
            return false;
        }
        String sql = "UPDATE trash_info SET " + columnName + " = ? WHERE trash_info_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (columnName.equals("trash_quantity")) {
                try {
                    int quantity = Integer.parseInt(newValue.toString());
                    statement.setInt(1, quantity);
                } catch (NumberFormatException e) {
                    new ErrorClass().startError("Ошибка", "Неверный тип данных", "Количество должно быть целым числом.");
                    return false;
                }
            } else {
                statement.setString(1, newValue.toString());
            }
            statement.setString(2, trashInfoId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при обновлении записи информации о мусоре", e.getMessage());
            return false;
        }
    }



    //метод для поиска TrashInfo по нескольким параметрам
    public List<TrashInfo> searchTrashInfoByParameters(Map<String, Object> params) {
        List<TrashInfo> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM trash_info WHERE ");
        List<Object> values = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String column = entry.getKey();
            Object value = entry.getValue();
            sql.append(column).append(" = ? AND ");
            values.add(value);
        }
        sql.delete(sql.length() - 4, sql.length());
        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object value : values) {
                if (value instanceof Integer) {
                    statement.setInt(index++, (Integer) value);
                } else {
                    statement.setString(index++, value.toString());
                }
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TrashInfo trashInfo = new TrashInfo();
                trashInfo.setTrashInfoId(resultSet.getString("trash_info_id"));
                trashInfo.setUserId(resultSet.getString("user_id"));
                trashInfo.setTrashTypeId(resultSet.getString("trash_type_id"));
                trashInfo.setTrashQuantity(resultSet.getInt("trash_quantity"));
                results.add(trashInfo);
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при выполнении поиска", e.getMessage());
        }
        return results;
    }
}
