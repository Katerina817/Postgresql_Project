package org.example.postgresql_project.EntitiesControl;

import lombok.NonNull;
import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.RecyclingStatus;
import org.example.postgresql_project.ErrorClass;
import org.example.postgresql_project.InvalidLengthException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RecyclingStatusControl {
    private Connection connection;
    private CheckClass recyclingStatus_check= new CheckClass();
    public RecyclingStatusControl(Connection connection){
        this.connection=connection;
    }

    //Метод для добавления записи в таблицу recycling_status
    public void insertRecyclingStatus(@NonNull RecyclingStatus recyclingStatus) throws SQLException {
        try{
            recyclingStatus_check.CheckRecyclingStatus(recyclingStatus);
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка","Неверная длина",e.getMessage());
            return;
        }

        if (!isStatusNameUnique(recyclingStatus.getRecyclingStatusName())) {
            new ErrorClass().startError("Ошибка","Название уже существует","Пожалуйста, выберите другое название.");
            return;
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
        }
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
}
