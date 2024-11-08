package org.example.postgresql_project.EntitiesControl;

import lombok.NonNull;
import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.TrashType;
import org.example.postgresql_project.ErrorClass;
import org.example.postgresql_project.InvalidLengthException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TrashTypeControl {
    private Connection connection;
    private CheckClass trashType_check= new CheckClass();
    public TrashTypeControl(Connection connection){
        this.connection=connection;
    }


    //Метод для добавления записи в таблицу trash_type
    public void insertReportType(@NonNull TrashType trashType) throws SQLException {
        try{
            trashType_check.CheckTrashType(trashType);
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка","Неверная длина",e.getMessage());
            return;
        }

        if (!isNameUnique(trashType.getTrashTypeName())) {
            new ErrorClass().startError("Ошибка","Название уже существует","Пожалуйста, выберите другое название.");
            return;
        }
        trashType.setTrashTypeId(UUID.randomUUID().toString());
        String sql = "INSERT INTO trash_type (trash_type_id, trash_type_name) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trashType.getTrashTypeId());
            statement.setString(2, trashType.getTrashTypeName());
            statement.executeUpdate();
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вставке типа мусора",e.getMessage());
        }
    }
    //метод для проверки уникальности названия
    private boolean isNameUnique(String trash_type_name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM trash_type WHERE trash_type_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trash_type_name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
            return true;
        }
    }
}
