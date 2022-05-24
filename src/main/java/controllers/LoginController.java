package controllers;

import exceptions.UsernameAlreadyExistsException;
import javafx.fxml.FXML;
import services.UserService;

import java.awt.*;

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

}
