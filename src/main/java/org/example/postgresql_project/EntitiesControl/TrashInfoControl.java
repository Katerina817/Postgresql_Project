package org.example.postgresql_project.EntitiesControl;

import lombok.NonNull;
import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.TrashInfo;
import org.example.postgresql_project.ErrorClass;
import org.example.postgresql_project.InvalidLengthException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

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
        if (!checkUserExists(trashInfo.getUserId()) || !checkTrashTypeExists(trashInfo.getTrashTypeId())) {
            new ErrorClass().startError("Ошибка", "Пользователь или тип мусора не найден в базе данных");
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
}
