package org.example.postgresql_project.EntitiesControl;

import lombok.NonNull;
import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.Recycling;
import org.example.postgresql_project.Entities.Report;
import org.example.postgresql_project.ErrorClass;
import org.example.postgresql_project.InvalidLengthException;

import java.sql.*;
import java.util.*;
import java.util.Date;

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
        try {
            if (!checkReportTypeExists(report.getReportTypeId()) || !checkAdminExists(report.getAdminId())|| !checkRecyclingExists(report.getRecyclingId())) {
                new ErrorClass().startError("Ошибка", "Тип отчета, админ или информация о переработке не найдены в базе данных");
                return false;
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при проверке существования элементов", e.getMessage());
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



    //Метод для удаления записи из таблицы report
    public boolean deleteReportByColumnName(String column_name,Object value) {
        String sql = "DELETE FROM report WHERE "+column_name+" = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if(Objects.equals(column_name, "report_date")){
                try {
                    java.sql.Date sqlDate = java.sql.Date.valueOf(value.toString());
                    statement.setDate(1, sqlDate);
                } catch (IllegalArgumentException e) {
                    new ErrorClass().startError("Ошибка", "Неверный тип данных", "Значение должно соответствовать формату yyyy-MM-dd");
                    return false;
                }
            } else {
                statement.setString(1, value.toString());
            }
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при удалении записи об отчете",e.getMessage());
            return false;
        }
    }

    // Метод для обновления записи в таблице report по report_id
    public boolean updateReportField(String reportId, String columnName, Object newValue) {
        if(columnName.equals("report_id")){
            new ErrorClass().startError("Ошибка", "Нельзя изменить значение ID");
            return false;
        }
        try {
            report_check.validateReportForUpdate(columnName, newValue.toString());
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка", "Неверная длина строки", e.getMessage());
            return false;
        }
        try {
            if (columnName.equals("recycling_id") && !checkRecyclingExists(newValue.toString())) {
                new ErrorClass().startError("Ошибка", "Информация о переработке не найдена", "Указанная информация не существует.");
                return false;
            }
            if (columnName.equals("admin_id") && !checkAdminExists(newValue.toString())) {
                new ErrorClass().startError("Ошибка", "Админ не найден", "Указанный админ не существует.");
                return false;
            }
            if (columnName.equals("report_type_id") && !checkReportTypeExists(newValue.toString())) {
                new ErrorClass().startError("Ошибка", "Тип отчета не найден", "Указанный тип отчета не существует.");
                return false;
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при проверке статуса", e.getMessage());
            return false;
        }
        String sql = "UPDATE report SET " + columnName + " = ? WHERE report_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (columnName.equals("report_date")) {
                try {
                    java.sql.Date sqlDate = java.sql.Date.valueOf(newValue.toString());
                    statement.setDate(1, sqlDate);
                } catch (IllegalArgumentException e) {
                    new ErrorClass().startError("Ошибка", "Неверный тип данных", "Значение должно соответствовать формату yyyy-MM-dd");
                    return false;
                }
            } else {
                statement.setString(1, newValue.toString());
            }
            statement.setString(2, reportId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при обновлении записи отчета", e.getMessage());
            return false;
        }
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



    //метод для поиска Report по нескольким параметрам
    public List<Report> searchReportByParameters(Map<String, Object> params) {
        List<Report> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM report WHERE ");
        List<Object> values = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String column = entry.getKey();
            Object value = entry.getValue();
            sql.append(column).append(" = ? AND ");
            /*if ("report_date".equals(column) && value instanceof String) {
                try {
                    value = java.sql.Date.valueOf(value.toString());
                } catch (IllegalArgumentException e) {
                    new ErrorClass().startError("Ошибка", "Неверный формат даты", "Значение должно соответствовать формату yyyy-MM-dd");
                    return results;
                }
                //value = java.sql.Date.valueOf(value.toString());
            }*/
            values.add(value);
        }
        sql.delete(sql.length() - 4, sql.length());
        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object value : values) {
                if (value instanceof java.sql.Date) {
                    statement.setDate(index++, (java.sql.Date) value);
                } else {
                    statement.setString(index++, value.toString());
                }
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Report report = new Report();
                report.setReportId(resultSet.getString("report_id"));
                report.setReportTypeId(resultSet.getString("report_type_id"));
                report.setAdminId(resultSet.getString("admin_id"));
                report.setContent(resultSet.getString("content"));
                report.setReportDate(resultSet.getDate("report_date"));
                report.setRecyclingId(resultSet.getString("recycling_id"));
                results.add(report);
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при выполнении поиска", e.getMessage());
        }
        return results;
    }




}
