package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyPlaylistsController {
    @FXML
    private javafx.scene.control.Button idBack;
    @FXML
    public void Back(){
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainMenu.fxml"));
            Stage window = (Stage) idBack.getScene().getWindow();
            window.setScene(new Scene(root, 750, 500));
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
}
