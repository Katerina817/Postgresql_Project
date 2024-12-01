package org.example.postgresql_project.EntitiesControl;

import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.ReportAndAdmin;
import org.example.postgresql_project.ErrorClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProceduresAndFunctions {
    private final Connection connection;
    public ProceduresAndFunctions(Connection connection){
        this.connection=connection;
    }


    // Метод для вызова процедуры update_recycling_status_description
    public boolean callUpdateRecyclingStatusProcedure(String recyclingId, String newDesc){
        String sql = "CALL update_recycling_status_description(?, ?)";
        if(newDesc.length()>400){
            new ErrorClass().startError("Ошибка","Описание должно состоять не более чем из 400 символов");
            return false;
        }
        try (CallableStatement statement = connection.prepareCall(sql)) {
            statement.setString(1, recyclingId);
            statement.setString(2, newDesc);
            statement.execute();
            new ErrorClass().startSuccess("Успех", "Запись успешно обновлена");
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вызове процедуры update_recycling_status_description: " + e.getMessage());
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
    //Процедура для добавления
    public boolean callAddTrashTypeProcedure(String trashTypeName) throws SQLException {
        String sql = "CALL add_trash_type(?)";
        if(trashTypeName.length()>60){
            new ErrorClass().startError("Ошибка","Наименование должно состоять не более чем из 60 символов");
            return false;
        }
        if (!isNameUnique(trashTypeName)) {
            new ErrorClass().startError("Ошибка","Название уже существует","Пожалуйста, выберите другое название.");
            return false;
        }
        try (CallableStatement statement = connection.prepareCall(sql)) {
            statement.setString(1, trashTypeName);
            statement.execute();
            new ErrorClass().startSuccess("Успех", "Добавление строки прошло успешно");
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вызове процедуры add_trash_type: " + e.getMessage());
            return false;
        }
        return true;
    }

    //метод для поиска TrashQuantity по юзерId
    public int getTotalTrashQuantityByUser(String userId) {
        String sql = "SELECT get_total_trash_quantity_by_user(?)";
        int totalQuantity = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    totalQuantity = resultSet.getInt(1);
                }
            }catch (SQLException e) {
                new ErrorClass().startError("Ошибка","Ошибка при вызове процедуры get_total_trash_quantity_by_user: " + e.getMessage());
            }
        }catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вызове процедуры get_total_trash_quantity_by_user: " + e.getMessage());
        }
        return totalQuantity;
    }

    //метод для подсчета количества отчетов по типу
    public List<ReportAndAdmin> getReportsByType(String reportTypeName) {
        //String sql = "SELECT get_reports_by_type(?)";
        String sql = "SELECT * FROM get_reports_by_type(?)";
        List<ReportAndAdmin> reportAndAdmins=new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reportTypeName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reportAndAdmins.add(ReportAndAdmin.builder()
                            .reportId(resultSet.getString(1))
                            .content(resultSet.getString(2))
                            .reportDate(resultSet.getDate(3))
                            .name(resultSet.getString(4))
                            .surname(resultSet.getString(5))
                            .build());
                }
            }catch (SQLException e) {
                new ErrorClass().startError("Ошибка","Ошибка при вызове процедуры get_reports_by_type: " + e.getMessage());
            }
        }catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вызове процедуры get_reports_by_type: " + e.getMessage());
        }
        return reportAndAdmins;
    }
}
