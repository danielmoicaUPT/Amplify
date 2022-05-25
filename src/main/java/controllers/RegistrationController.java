package controllers;
import exceptions.UsernameAlreadyExistsException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
    private javafx.scene.control.Button idCreeaza;
    @FXML
    public void handleRegistration(){
        try{
            UserService.connectToDatabase("root","amplify_admin69");
            UserService.insertUser(idUsername.getText(),idParola.getText(),"Premium");
        }catch(UsernameAlreadyExistsException exc){
            messageDisplay.append("Username already exists");
        }
    }
    public void RegistrationtoLogin () throws Exception   {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        Stage window  = (Stage) idCreeaza.getScene().getWindow();
        window.setScene(new Scene(root,750,500));
    }
}
