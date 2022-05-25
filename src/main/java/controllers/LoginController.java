package controllers;

import exceptions.UsernameAlreadyExistsException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.UserService;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Window;
import java.awt.*;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField idUserLogin;
    @FXML
    private TextField idParolaLogin;
    @FXML
    private Button idLogin;
    @FXML
    private Label idMessageDisplay;
    @FXML
    public void handleLogin(){
        try {
            UserService.connectToDatabase("root", "amplify_admin69");
            if (UserService.isUserInDatabase(idUserLogin.getText(), idParolaLogin.getText())) {

            } else {
                idMessageDisplay.setText("Username or password is incorrect!");
            }
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    public void LogintoMainMenu () throws Exception   {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainMenu.fxml"));
        Stage window  = (Stage) idLogin.getScene().getWindow();
        window.setScene(new Scene(root,750,500));
    }
}
