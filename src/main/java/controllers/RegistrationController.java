package controllers;
import exceptions.UsernameAlreadyExistsException;
import javafx.fxml.FXML;
import services.UserService;
import java.awt.*;

public class RegistrationController {

    @FXML
    private TextField idParola;
    @FXML
    private TextField idConfirmParola;
    @FXML
    private TextField idUsername;
    @FXML
    private TextArea messageDisplay;
    @FXML
    public void handleRegistration(){
        try{
            UserService.connectToDatabase("root","amplify_admin69");
            UserService.insertUser(idUsername.getText(),idParola.getText(),"Premium");
        }catch(UsernameAlreadyExistsException exc){
            messageDisplay.append("Username already exists");
        }
    }
}
