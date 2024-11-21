package org.example.postgresql_project;

import javafx.scene.control.Alert;

public class ErrorClass {
    private Alert alert;
    public ErrorClass(){
        this.alert = new Alert(Alert.AlertType.ERROR);
    }
    public void startError(String title,String headerText,String contentText){
        this.alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    public void startError(String title,String headerText){
        this.alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    public void startSuccess(String title, String headerText, String contentText) {
        this.alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void startSuccess(String title, String headerText) {
        this.alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}
