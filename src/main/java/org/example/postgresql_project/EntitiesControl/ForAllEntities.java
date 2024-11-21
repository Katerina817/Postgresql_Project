package org.example.postgresql_project.EntitiesControl;

import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.*;
import org.example.postgresql_project.ErrorClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ForAllEntities {
    private final Connection connection;
    public ForAllEntities(Connection connection){
        this.connection=connection;
    }

    //метод для получения списка строк TableName
    public List<?> getAllRows(String table) {
        String sql = new String("SELECT * FROM "+table);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (table.equals("admin")) {
                List<Admin> rows=new ArrayList<>();
                while (resultSet.next()) {
                    rows.add(Admin.builder()
                        .adminId(resultSet.getString(1))
                        .login(resultSet.getString(2))
                        .name(resultSet.getString(3))
                        .surname(resultSet.getString(4))
                        .password(resultSet.getString(5))
                        .email(resultSet.getString(6))
                        .age(resultSet.getInt(7))
                        .birthYear(resultSet.getInt(8))
                        .build());
                }
                return rows;
            }
            else if(table.equals("recycling")){
                List<Recycling> rows=new ArrayList<>();
                while (resultSet.next()) {
                    rows.add(Recycling.builder()
                            .recyclingId(resultSet.getString(1))
                            .recyclingStatusId(resultSet.getString(2))
                            .ruleId(resultSet.getString(3))
                            .trashInfoId(resultSet.getString(4))
                            .recyclingDate(resultSet.getDate(5))
                            .build());
                }
                return rows;
            }
            else if(table.equals("recycling_rule")){
                List<RecyclingRule> rows=new ArrayList<>();
                while (resultSet.next()) {
                    rows.add(RecyclingRule.builder()
                            .ruleId(resultSet.getString(1))
                            .content(resultSet.getString(2))
                            .build());
                }
                return rows;
            }
            else if(table.equals("recycling_status")){
                List<RecyclingStatus> rows=new ArrayList<>();
                while (resultSet.next()) {
                    rows.add(RecyclingStatus.builder()
                            .recyclingStatusId(resultSet.getString(1))
                            .recyclingStatusName(resultSet.getString(2))
                            .currentProcessDescription(resultSet.getString(3))
                            .build());
                }
                return rows;
            }
            else if(table.equals("report")){
                List<Report> rows=new ArrayList<>();
                while (resultSet.next()) {
                    rows.add(Report.builder()
                            .reportId(resultSet.getString(1))
                            .reportTypeId(resultSet.getString(2))
                            .adminId(resultSet.getString(3))
                            .content(resultSet.getString(4))
                            .reportDate(resultSet.getDate(5))
                            .recyclingId(resultSet.getString(6))
                            .build());
                }
                return rows;
            }
            else if(table.equals("report_type")){
                List<ReportType> rows=new ArrayList<>();
                while (resultSet.next()) {
                    rows.add(ReportType.builder()
                            .reportTypeId(resultSet.getString(1))
                            .reportTypeName(resultSet.getString(2))
                            .build());
                }
                return rows;
            }
            else if(table.equals("trash_info")){
                List<TrashInfo> rows=new ArrayList<>();
                while (resultSet.next()) {
                    rows.add(TrashInfo.builder()
                            .trashInfoId(resultSet.getString(1))
                            .userId(resultSet.getString(2))
                            .trashTypeId(resultSet.getString(3))
                            .trashQuantity(resultSet.getInt(4))
                            .build());
                }
                return rows;
            }
            else if(table.equals("trash_type")){
                List<TrashType> rows=new ArrayList<>();
                while (resultSet.next()) {
                    rows.add(TrashType.builder()
                            .trashTypeId(resultSet.getString(1))
                            .trashTypeName(resultSet.getString(2))
                            .build());
                }
                return rows;            }
            else if(table.equals("users")){
                List<User> rows=new ArrayList<>();
                while (resultSet.next()) {
                    rows.add(User.builder()
                            .userId(resultSet.getString(1))
                            .login(resultSet.getString(2))
                            .name(resultSet.getString(3))
                            .surname(resultSet.getString(4))
                            .password(resultSet.getString(5))
                            .email(resultSet.getString(6))
                            .build());
                }
                return rows;            }

        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка", "Ошибка при выполнении поиска", e.getMessage());
        }
        return null;
    }
}
