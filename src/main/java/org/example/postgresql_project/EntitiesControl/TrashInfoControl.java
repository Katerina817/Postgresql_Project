package org.example.postgresql_project.EntitiesControl;

import lombok.NonNull;
import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.TrashInfo;
import org.example.postgresql_project.ErrorClass;
import org.example.postgresql_project.InvalidLengthException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class TrashInfoControl {
    private Connection connection;
    private CheckClass trash_info_check= new CheckClass();
    public TrashInfoControl(Connection connection){
        this.connection=connection;
    }

    //Метод для добавления записи в таблицу trash_info
    public void insertTrashInfo(@NonNull TrashInfo trashInfo) throws SQLException {
        try{
            trash_info_check.CheckTrashInfo(trashInfo);
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка","Неверная длина",e.getMessage());
            return;
        }
        if (!checkUserExists(trashInfo.getUserId()) || !checkTrashTypeExists(trashInfo.getTrashTypeId())) {
            new ErrorClass().startError("Ошибка", "Пользователь или тип мусора не найден в базе данных");
            return;
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
        }
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
}
