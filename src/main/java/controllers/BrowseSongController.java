package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.awt.*;

public class BrowseSongController {
    @FXML
    private TextField idSearchTitle;
    @FXML
    private TextField idSearchGenre;
    @FXML
    private javafx.scene.control.Button idSearch;
    @FXML
    private javafx.scene.control.Button idPlay;
    @FXML
    private javafx.scene.control.Button idAddPlaylist;
    @FXML
    public void toPlayer() {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Player.fxml"));
            Stage window = (Stage) idPlay.getScene().getWindow();
            window.setScene(new Scene(root, 750, 500));
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
}
