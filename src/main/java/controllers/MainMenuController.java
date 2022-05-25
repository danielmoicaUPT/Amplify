package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import services.UserService;

public class MainMenuController {
    @FXML
    private Button idSearch;
    @FXML
    private Button idUpload ;
    @FXML
    private Button idMyPlaylist ;
    @FXML
    private Button idAccInfo;
    @FXML
    private Button idLogout;
    @FXML
    private Label idUsername;

    @FXML
    public void MainMenutoSearch () throws Exception   {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("BrowseSongs.fxml"));
        Stage window  = (Stage) idSearch.getScene().getWindow();
        window.setScene(new Scene(root,750,500));
    }
    public void MainMenutoUpload () throws Exception   {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UploadSong.fxml"));
        Stage window  = (Stage) idUpload.getScene().getWindow();
        window.setScene(new Scene(root,750,500));
    }
    public void MainMenutoPlaylist () throws Exception   {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MyPlaylists.fxml"));
        Stage window  = (Stage) idMyPlaylist.getScene().getWindow();
        window.setScene(new Scene(root,750,500));
    }
    public void MainMenutoAccInfo () throws Exception   {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("EditAccountInfo.fxml"));
        Stage window  = (Stage) idAccInfo.getScene().getWindow();
        window.setScene(new Scene(root,750,500));
    }
    public void Logout(){
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
            Stage window = (Stage) idLogout.getScene().getWindow();
            window.setScene(new Scene(root, 750, 500));
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }


}
