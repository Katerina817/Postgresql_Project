package org.example.postgresql_project.EntitiesControl;

import lombok.NonNull;
import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.RecyclingRule;
import org.example.postgresql_project.Entities.ReportType;
import org.example.postgresql_project.ErrorClass;
import org.example.postgresql_project.InvalidLengthException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReportTypeControl {
    private Connection connection;
    private CheckClass reportType_check= new CheckClass();
    public ReportTypeControl(Connection connection){
        this.connection=connection;
    }

    //Метод для добавления записи в таблицу report_type
    public boolean insertReportType(@NonNull ReportType reportType) throws SQLException {
        try{
            reportType_check.CheckReportType(reportType);
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка","Неверная длина",e.getMessage());
            return false;
        }
        try {
            if (!isNameUnique(reportType.getReportTypeName())) {
                new ErrorClass().startError("Ошибка","Название уже существует","Пожалуйста, выберите другое название.");
                return false;
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при проверке уникальности", e.getMessage());
            return false;
        }
        reportType.setReportTypeId(UUID.randomUUID().toString());
        String sql = "INSERT INTO report_type (report_type_id, report_type_name) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reportType.getReportTypeId());
            statement.setString(2, reportType.getReportTypeName());
            statement.executeUpdate();
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вставке типа отчета",e.getMessage());
            return false;
        }
        return true;
    }
    //метод для проверки уникальности названия
    private boolean isNameUnique(String report_type_name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM report_type WHERE report_type_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, report_type_name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
            return true;
        }
    }


    //Метод для удаления записи из таблицы report_type
    public boolean deleteReportTypeByColumnName(String column_name,String value) {
        String selectSql = "SELECT report_type_id FROM report_type WHERE " + column_name + " = ?";
        String sql = "DELETE FROM report_type WHERE "+column_name+" = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            selectStatement.setString(1, value);
            statement.setString(1, value);

            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                String reportTypeId = resultSet.getString("report_type_id");
                ReportControl reportControl = new ReportControl(connection);
                reportControl.deleteReportByColumnName("report_type_id", reportTypeId);
            }
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при удалении записи о типе отчета",e.getMessage());
            return false;
        }
    }

    // Метод для обновления записи в таблице report_type по report_type_id
    public boolean updateReportTypeField(String reportTypeId, String columnName, Object newValue) {
        if(columnName.equals("report_type_id")){
            new ErrorClass().startError("Ошибка", "Нельзя изменить значение ID");
            return false;
        }
        try {
            reportType_check.validateReportTypeForUpdate(columnName, newValue.toString());
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка", "Неверная длина строки", e.getMessage());
            return false;
        }
        if (columnName.equals("report_type_name")) {
            try {
                if (!isNameUnique(newValue.toString())) {
                    new ErrorClass().startError("Ошибка", "Название уже существует", "Пожалуйста, выберите другое название.");
                    return false;
                }
            } catch (SQLException e) {
                new ErrorClass().startError("Ошибка", "Ошибка при проверке уникальности названия", e.getMessage());
                return false;
            }
        }
        String sql = "UPDATE report_type SET " + columnName + " = ? WHERE report_type_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (columnName.equals("report_type_name")) {
                statement.setString(1, newValue.toString());
            } else {
                new ErrorClass().startError("Ошибка", "Недопустимый тип данных", "Неподдерживаемое значение для данного столбца.");
                return false;
            }
            statement.setString(2, reportTypeId);
            int rowsAffected = statement.executeUpdate();
            new ErrorClass().startSuccess("Успех", "Запись успешно обновлена");
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при обновлении записи типа отчета", e.getMessage());
            return false;
        }
    }



    //метод для поиска ReportType по нескольким параметрам
    public List<ReportType> searchReportTypeByParameters(Map<String, Object> params) {
        List<ReportType> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM report_type WHERE ");
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
                statement.setString(index++, value.toString());
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ReportType reportType = new ReportType();
                reportType.setReportTypeId(resultSet.getString("report_type_id"));
                reportType.setReportTypeName(resultSet.getString("report_type_name"));
                results.add(reportType);
            }
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при выполнении поиска", e.getMessage());
        }
        return results;
    }
}
