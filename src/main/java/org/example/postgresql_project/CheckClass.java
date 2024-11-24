package org.example.postgresql_project;

import lombok.NonNull;
import org.example.postgresql_project.Entities.*;

public class CheckClass{
    public void CheckAdmin(@NonNull Admin admin) throws InvalidLengthException{
        /*if (admin.getLogin()!=null){
            if(admin.getLogin().length()>20){
                throw new InvalidLengthException("Неверная длина логина: "+admin.getLogin().length());
            }
        }*/
        if(admin.getLogin()==null || admin.getPassword()==null || admin.getAge()==null || admin.getBirthYear()==null){
            throw new InvalidLengthException("Необходимо заполнить поля: логин, пароль, возраст и год рождения");
        }
        if (admin.getLogin().length()>20 || (admin.getName()!=null && admin.getName().length()>30) || (admin.getSurname()!=null && admin.getSurname().length()>30) || admin.getPassword().length()>34 || (admin.getEmail()!=null && admin.getEmail().length()>100)){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "Логин: 20\n"
                    + "Имя: 30\n"
                    + "Фамилия: 30\n"
                    + "Пароль: 34\n"
                    + "Email: 100\n"
            );
        }
    }
    public void validateAdminForUpdate(String columnName, String value) throws InvalidLengthException {
        if (columnName.equals("login") && value.length() > 20 ||
                columnName.equals("name") && value.length() > 30 ||
                columnName.equals("surname") && value.length() > 30 ||
                columnName.equals("password") && value.length() > 34 ||
                columnName.equals("email") && value.length() > 100) {
            throw new InvalidLengthException("Недопустимая длина для поля " + columnName);
        }
    }
    public void CheckReportType(@NonNull ReportType reportType) throws InvalidLengthException{
        if(reportType.getReportTypeName()==null){
            throw new InvalidLengthException("Необходимо заполнить поле: наименование типа отчета");
        }
        if (reportType.getReportTypeName().length()>40){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "Наименование типа отчета: 40");
        }
    }
    public void validateReportTypeForUpdate(String columnName, String value) throws InvalidLengthException {
        if (columnName.equals("report_type_name") && value.length() > 40) {
            throw new InvalidLengthException("Недопустимая длина для поля " + columnName);
        }
    }
    public void CheckTrashType(@NonNull TrashType trashType) throws InvalidLengthException{
        if(trashType.getTrashTypeName()==null){
            throw new InvalidLengthException("Необходимо заполнить поле: наименование типа мусора");
        }
        if (trashType.getTrashTypeName().length()>60){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "Наименование типа мусора: 60");
        }
    }
    public void validateTrashTypeForUpdate(String columnName, String value) throws InvalidLengthException {
        if (columnName.equals("trash_type_name") && value.length() > 60) {
            throw new InvalidLengthException("Недопустимая длина для поля " + columnName);
        }
    }
    public void CheckUser(@NonNull User user) throws InvalidLengthException{
        /*if (admin.getLogin()!=null){
            if(admin.getLogin().length()>20){
                throw new InvalidLengthException("Неверная длина логина: "+admin.getLogin().length());
            }
        }*/
        if(user.getLogin()==null || user.getPassword()==null){
            throw new InvalidLengthException("Необходимо заполнить поля: логин и пароль");
        }
        if (user.getLogin().length()>20 || (user.getName()!=null && user.getName().length()>30) || (user.getSurname()!=null && user.getSurname().length()>30) || user.getPassword().length()>34|| (user.getEmail()!=null && user.getEmail().length()>100)){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "Логин: 20\n"
                    + "Имя: 30\n"
                    + "Фамилия: 30\n"
                    + "Пароль: 34\n"
                    + "Email: 100");
        }
    }
    public void CheckRecyclingStatus(@NonNull RecyclingStatus recyclingStatus) throws InvalidLengthException{
        if(recyclingStatus.getRecyclingStatusName()==null || recyclingStatus.getCurrentProcessDescription()==null){
            throw new InvalidLengthException("Необходимо заполнить поля: наименование статуса переработки и описание текущего процесса");
        }
        if (recyclingStatus.getRecyclingStatusName().length()>40|| recyclingStatus.getCurrentProcessDescription().length()>400){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "Наименование статуса переработки: 40\n"
                    + "Описание текущего процесса: 400");
        }
    }
    public void validateRecyclingStatusForUpdate(String columnName, String value) throws InvalidLengthException {
        if (columnName.equals("recycling_status_name") && value.length() > 40 ||
                columnName.equals("current_process_description") && value.length() > 400) {
            throw new InvalidLengthException("Недопустимая длина для поля " + columnName);
        }
    }
    public void CheckRecyclingRule(@NonNull RecyclingRule recyclingRule) throws InvalidLengthException{
        if(recyclingRule.getContent()==null){
            throw new InvalidLengthException("Необходимо заполнить поля: содержание");
        }
        if (recyclingRule.getContent().length()>500){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "Содержание: 500");
        }
    }
    public void validateRecyclingRuleForUpdate(String columnName, String value) throws InvalidLengthException {
        if (columnName.equals("content") && value.length() > 500) {
            throw new InvalidLengthException("Недопустимая длина для поля " + columnName);
        }
    }
    public void CheckTrashInfo(@NonNull TrashInfo trashInfo) throws InvalidLengthException{
        if(trashInfo.getUserId()==null || trashInfo.getTrashTypeId()==null || trashInfo.getTrashQuantity()==null){
            throw new InvalidLengthException("Необходимо заполнить поля: ID пользователя, ID типа мусора и количество мусора");
        }
        if (trashInfo.getUserId().length()>36 || trashInfo.getTrashTypeId().length()>36){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "ID пользователя: 36\n"
                    + "ID типа мусора: 36");
        }
    }
    public void validateTrashInfoForUpdate(String columnName, String value) throws InvalidLengthException {
        if (columnName.equals("user_id") && value.length() > 36 ||
            columnName.equals("trash_type_id") && value.length() > 36) {
            throw new InvalidLengthException("Недопустимая длина для поля " + columnName);
        }
    }
    public void CheckRecycling(@NonNull Recycling recycling) throws InvalidLengthException{
        if(recycling.getRecyclingStatusId()==null || recycling.getRuleId()==null || recycling.getTrashInfoId()==null || recycling.getRecyclingDate()==null){
            throw new InvalidLengthException("Необходимо заполнить поля: ID статуса переработки, ID правила переработки, ID информации о мусоре и дата переработки");
        }
        if (recycling.getRecyclingStatusId().length()>36 || recycling.getRuleId().length()>36 || recycling.getTrashInfoId().length()>36){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "ID статуса переработки: 36\n"
                    + "ID правила переработки: 36\n"
                    + "ID информации о мусоре: 36");
        }
    }
    public void validateRecyclingForUpdate(String columnName, String value) throws InvalidLengthException {
        if (columnName.equals("recycling_status_id") && value.length() > 36 ||
                columnName.equals("rule_id") && value.length() > 36 ||
                columnName.equals("trash_info_id") && value.length() > 36) {
            throw new InvalidLengthException("Недопустимая длина для поля " + columnName);
        }
    }
    public void CheckReport(@NonNull Report report) throws InvalidLengthException{
        if(report.getReportTypeId()==null || report.getAdminId()==null || report.getContent()==null || report.getReportDate()==null || report.getRecyclingId()==null){
            throw new InvalidLengthException("Необходимо заполнить поля: ID типа отчета, ID админа, содержание, ID переработки и дата создания отчета");
        }
        if (report.getReportTypeId().length()>36 || report.getAdminId().length()>36 || report.getContent().length()>500 || report.getRecyclingId().length()>36){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "ID типа отчета: 36\n"
                    + "ID админа: 36\n"
                    + "Содержание: 500\n"
                    + "ID переработки: 36");
        }
    }
    public void validateReportForUpdate(String columnName, String value) throws InvalidLengthException {
        if (columnName.equals("report_type_id") && value.length() > 36 ||
                columnName.equals("admin_id") && value.length() > 36 ||
                columnName.equals("content") && value.length() > 500||
                columnName.equals("recycling_id") && value.length() > 36) {
            throw new InvalidLengthException("Недопустимая длина для поля " + columnName);
        }
    }
}
