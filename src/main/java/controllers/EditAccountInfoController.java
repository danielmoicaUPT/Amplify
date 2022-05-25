package controllers;

import exceptions.IncorrectPasswordException;
import exceptions.UsernameAlreadyExistsException;
import javafx.fxml.FXML;

import java.awt.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import services.UserService;

import javafx.scene.control.TextField;
public class EditAccountInfoController {
    @FXML
    private TextField idNewUsername;
    @FXML
    private TextField idPassword;
    @FXML
    private javafx.scene.control.Button idChangeUser;
    @FXML
    private javafx.scene.control.Button idChangePicture;
    @FXML
    private javafx.scene.control.Button idChangeSub;
    @FXML
    private javafx.scene.control.Label idDisplay;
    @FXML
    private javafx.scene.control.Button idBack;
    @FXML
    public void ChangeUsername(){
        try {
            UserService.updateUser(UserService.getUsername(), idPassword.getText(), idNewUsername.getText());
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainMenu.fxml"));
            Stage window = (Stage) idChangeUser.getScene().getWindow();
            window.setScene(new Scene(root, 750, 500));
        }catch(UsernameAlreadyExistsException exc) {
            idDisplay.setText("Username already exists!");
            idNewUsername.setText("");
        }catch(IncorrectPasswordException exc){
            idDisplay.setText("Wrong password!");
            idPassword.setText("");
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
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
