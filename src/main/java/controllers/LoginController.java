package controllers;

import exceptions.IncorrectPasswordException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.UserService;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Window;
import javafx.scene.control.TextField;
import exceptions.UserDoesntExistException;
import javafx.scene.control.Label;


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
    private Button idRegister;
    @FXML
    public void LogintoMainMenu ()  {
        try {
            UserService.connectToDatabase("root", "amplify_admin69");
            if(UserService.isUserInDatabase(idUserLogin.getText(),idParolaLogin.getText())) {
                UserService.setUsername(idUserLogin.getText());
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainMenu.fxml"));
                Stage window = (Stage) idLogin.getScene().getWindow();
                window.setScene(new Scene(root, 750, 500));
            }

        }catch(IncorrectPasswordException exc) {
            idMessageDisplay.setText("Password for this user is incorrect.");
            idParolaLogin.setText("");
        }catch (UserDoesntExistException exc){
            idMessageDisplay.setText("Username doesn't exist.");
            idUserLogin.setText("");
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }
    public void LogintoRegister() {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("CreateAccount.fxml"));
            Stage window = (Stage) idRegister.getScene().getWindow();
            window.setScene(new Scene(root, 750, 500));
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }

}