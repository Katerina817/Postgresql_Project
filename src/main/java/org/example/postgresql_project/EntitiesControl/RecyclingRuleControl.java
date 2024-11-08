package org.example.postgresql_project.EntitiesControl;

import lombok.NonNull;
import org.example.postgresql_project.CheckClass;
import org.example.postgresql_project.Entities.RecyclingRule;
import org.example.postgresql_project.ErrorClass;
import org.example.postgresql_project.InvalidLengthException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RecyclingRuleControl {
    private Connection connection;
    private CheckClass recyclingRule_check= new CheckClass();
    public RecyclingRuleControl(Connection connection){
        this.connection=connection;
    }

    //Метод для добавления записи в таблицу recycling_rule
    public boolean insertRecyclingRule(@NonNull RecyclingRule recyclingRule) throws SQLException {
        try{
            recyclingRule_check.CheckRecyclingRule(recyclingRule);
        } catch (InvalidLengthException e) {
            new ErrorClass().startError("Ошибка","Неверная длина",e.getMessage());
            return false;
        }
        recyclingRule.setRuleId(UUID.randomUUID().toString());
        String sql = "INSERT INTO recycling_rule (rule_id, content) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, recyclingRule.getRuleId());
            statement.setString(2, recyclingRule.getContent());
            statement.executeUpdate();
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при вставке правила переработки",e.getMessage());
            return false;
        }
        return true;
    }

    //Метод для удаления записи из таблицы recycling_rule
    public boolean deleteRecyclingRuleByColumnName(String column_name,String value) {
        String selectSql = "SELECT rule_id FROM recycling_rule WHERE " + column_name + " = ?";
        String sql = "DELETE FROM recycling_rule WHERE "+column_name+" = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            selectStatement.setString(1, value);
            statement.setString(1, value);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                String ruleId = resultSet.getString("rule_id");
                RecyclingControl recyclingControl = new RecyclingControl(connection);
                recyclingControl.deleteRecyclingByColumnName("rule_id", ruleId);
            }
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new ErrorClass().startError("Ошибка","Ошибка при удалении записи о правиле переработки",e.getMessage());
            return false;
        }
    }
}
