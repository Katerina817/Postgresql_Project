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
        if(admin.getLogin()==null || admin.getPassword()==null){
            throw new InvalidLengthException("Необходимо заполнить поля: login и password");
        }
        if (admin.getLogin().length()>20 || (admin.getName()!=null && admin.getName().length()>30) || (admin.getSurname()!=null && admin.getSurname().length()>30) || admin.getPassword().length()>34 || (admin.getEmail()!=null && admin.getEmail().length()>100)){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "login: 20\n"
                    + "name: 30\n"
                    + "surname: 30\n"
                    + "password: 34\n"
                    + "email: 100\n"
                    + "tel_number: 14");
        }
    }
    public void CheckReportType(@NonNull ReportType reportType) throws InvalidLengthException{
        if(reportType.getReportTypeName()==null){
            throw new InvalidLengthException("Необходимо заполнить поле: report_type_name");
        }
        if (reportType.getReportTypeName().length()>40){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "report_type_name: 40");
        }
    }
    public void CheckTrashType(@NonNull TrashType trashType) throws InvalidLengthException{
        if(trashType.getTrashTypeName()==null){
            throw new InvalidLengthException("Необходимо заполнить поле: tarsh_type_name");
        }
        if (trashType.getTrashTypeName().length()>60){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "trash_type_name: 60");
        }
    }
    public void CheckUser(@NonNull User user) throws InvalidLengthException{
        /*if (admin.getLogin()!=null){
            if(admin.getLogin().length()>20){
                throw new InvalidLengthException("Неверная длина логина: "+admin.getLogin().length());
            }
        }*/
        if(user.getLogin()==null || user.getPassword()==null){
            throw new InvalidLengthException("Необходимо заполнить поля: login и password");
        }
        if (user.getLogin().length()>20 || (user.getName()!=null && user.getName().length()>30) || (user.getSurname()!=null && user.getSurname().length()>30) || user.getPassword().length()>34|| (user.getEmail()!=null && user.getEmail().length()>100)){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "login: 20\n"
                    + "name: 30\n"
                    + "surname: 30\n"
                    + "password: 34\n"
                    + "email: 100");
        }
    }
    public void CheckRecyclingStatus(@NonNull RecyclingStatus recyclingStatus) throws InvalidLengthException{
        if(recyclingStatus.getRecyclingStatusName()==null || recyclingStatus.getCurrentProcessDescription()==null){
            throw new InvalidLengthException("Необходимо заполнить поля: recycling_status_name и current_process_description");
        }
        if (recyclingStatus.getRecyclingStatusName().length()>40|| recyclingStatus.getCurrentProcessDescription().length()>400){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "recycling_status_name: 40\n"
                    + "current_process_description: 400");
        }
    }
    public void CheckRecyclingRule(@NonNull RecyclingRule recyclingRule) throws InvalidLengthException{
        if(recyclingRule.getContent()==null){
            throw new InvalidLengthException("Необходимо заполнить поля:content");
        }
        if (recyclingRule.getContent().length()>500){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "content: 500");
        }
    }
    public void CheckTrashInfo(@NonNull TrashInfo trashInfo) throws InvalidLengthException{
        if(trashInfo.getUserId()==null || trashInfo.getTrashTypeId()==null || trashInfo.getTrashQuantity()==null){
            throw new InvalidLengthException("Необходимо заполнить поля:user_id, trash_quantity и trash_type_id");
        }
        if (trashInfo.getUserId().length()>36 || trashInfo.getTrashTypeId().length()>36){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "user_id: 36\n"
                    + "trash_type_id: 36");
        }
    }
    public void CheckRecycling(@NonNull Recycling recycling) throws InvalidLengthException{
        if(recycling.getRecyclingStatusId()==null || recycling.getRuleId()==null || recycling.getTrashInfoId()==null || recycling.getRecyclingDate()==null){
            throw new InvalidLengthException("Необходимо заполнить поля:user_id, trash_quantity, recycling_date и trash_type_id");
        }
        if (recycling.getRecyclingStatusId().length()>36 || recycling.getRuleId().length()>36 || recycling.getTrashInfoId().length()>36){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "recycling_status_id: 36\n"
                    + "rule_id: 36\n"
                    + "trash_info_id: 36");
        }
    }
    public void CheckReport(@NonNull Report report) throws InvalidLengthException{
        if(report.getReportTypeId()==null || report.getAdminId()==null || report.getContent()==null || report.getReportDate()==null || report.getRecyclingId()==null){
            throw new InvalidLengthException("Необходимо заполнить поля:report_type_id, admin_id, content, recycling_id и report_date");
        }
        if (report.getReportTypeId().length()>36 || report.getAdminId().length()>36 || report.getContent().length()>500 || report.getRecyclingId().length()>36){
            throw new InvalidLengthException("Неверная длина строки.\n" + "Cверьтесь с максимальной допустимой длиной:\n"
                    + "report_type_id: 36\n"
                    + "admin_id: 36\n"
                    + "content: 500\n"
                    + "recycling_id: 36");
        }
    }
}
