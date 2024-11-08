package org.example.postgresql_project.EntitiesControl;

import lombok.NonNull;
import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.Recycling;
import org.example.postgresql_project.Entities.Report;
import org.example.postgresql_project.ErrorClass;
import org.example.postgresql_project.InvalidLengthException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class ReportControl {
    private Connection connection;
    private CheckClass report_check= new CheckClass();
    public ReportControl(Connection connection){
        this.connection=connection;
    }

    //Метод для добавления записи в таблицу report
    public boolean insertReport(@NonNull Report report) throws SQLException {
        try{
            report_check.CheckReport(report);
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка","Неверная длина",e.getMessage());
            return false;
        }
        if (!checkReportTypeExists(report.getReportTypeId()) || !checkAdminExists(report.getAdminId())|| !checkRecyclingExists(report.getRecyclingId())) {
            new ErrorClass().startError("Ошибка", "Тип мусора, админ или информация о переработке не найдены в базе данных");
            return false;
        }
        report.setReportId(UUID.randomUUID().toString());
        String sql = "INSERT INTO report (report_id, report_type_id,admin_id,content,report_date, recycling_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, report.getReportId());
            statement.setString(2, report.getReportTypeId());
            statement.setString(3, report.getAdminId());
            statement.setString(4, report.getContent());
            statement.setDate(5, report.getReportDate());
            statement.setString(6, report.getRecyclingId());
            statement.executeUpdate();
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вставке информации об отчете",e.getMessage());
            return false;
        }
        return true;
    }
    // метод для проверки существования типа мусора
    private boolean checkReportTypeExists(String reportTypeId) throws SQLException {
        String sql = "SELECT 1 FROM report_type WHERE report_type_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reportTypeId);
            return statement.executeQuery().next();
        }
    }
    //метод для проверки существования админа
    private boolean checkAdminExists(String adminId) throws SQLException {
        String sql = "SELECT 1 FROM admin WHERE admin_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, adminId);
            return statement.executeQuery().next();
        }
    }
    //метод для проверки существования информации о переработке
    private boolean checkRecyclingExists(String recyclingId) throws SQLException {
        String sql = "SELECT 1 FROM recycling WHERE recycling_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, recyclingId);
            return statement.executeQuery().next();
        }
    }



    //Метод для удаления записи из таблицы report по id
    public boolean deleteReportById(String reportId) {
        String sql = "DELETE FROM report WHERE report_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reportId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при удалении записи об отчете",e.getMessage());
            return false;
        }
    }
}
