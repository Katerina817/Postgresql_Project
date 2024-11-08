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
    public boolean insertReportType(@NonNull TrashType trashType) throws SQLException {
        try{
            trashType_check.CheckTrashType(trashType);
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка","Неверная длина",e.getMessage());
            return false;
        }

        if (!isNameUnique(trashType.getTrashTypeName())) {
            new ErrorClass().startError("Ошибка","Название уже существует","Пожалуйста, выберите другое название.");
            return false;
        }
        trashType.setTrashTypeId(UUID.randomUUID().toString());
        String sql = "INSERT INTO trash_type (trash_type_id, trash_type_name) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trashType.getTrashTypeId());
            statement.setString(2, trashType.getTrashTypeName());
            statement.executeUpdate();
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вставке типа мусора",e.getMessage());
            return false;
        }
        return true;
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




    //Метод для удаления записи из таблицы trash_type
    public boolean deleteTrashTypeByColumnName(String column_name,String value) {
        String selectSql = "SELECT trash_type_id FROM trash_type WHERE " + column_name + " = ?";
        String sql = "DELETE FROM trash_type WHERE "+column_name+" = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            selectStatement.setString(1, value);
            statement.setString(1, value);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                String trashTypeId = resultSet.getString("trash_type_id");
                TrashInfoControl trashInfoControl = new TrashInfoControl(connection);
                trashInfoControl.deleteTrashInfoByColumnName("trash_type_id", trashTypeId);
            }
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при удалении записи о типе мусора",e.getMessage());
            return false;
        }
    }
}
