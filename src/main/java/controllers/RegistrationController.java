package controllers;
import exceptions.PasswordsMisatchException;
import exceptions.UsernameAlreadyExistsException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.UserService;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.security.spec.ECField;

public class RegistrationController {

    @FXML
    private TextField idParola;
    @FXML
    private TextField idConfirmParola;
    @FXML
    private TextField idUsername;
    @FXML
    private Label idMessageDisplay;
    @FXML
    private Button idCreeaza;
    @FXML
    private Button idBack;
    @FXML
    public void HandleRegistration () {
        try{
            System.out.println(idConfirmParola.getText());
            System.out.println(idParola.getText());
            if(idParola.getText().equals(idConfirmParola.getText())) {
                System.out.println(idConfirmParola.getText());
                UserService.connectToDatabase("root", "amplify_admin69");
                UserService.insertUser(idUsername.getText(), idParola.getText(), "Premium");
                UserService.disconnectFromDatabase();
            }else{
                throw new PasswordsMisatchException();
            }
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
            Stage window  = (Stage) idCreeaza.getScene().getWindow();
            window.setScene(new Scene(root,750,500));
        }catch(UsernameAlreadyExistsException exc){
            idMessageDisplay.setText("Username already exists");
            idUsername.setText("");
        }catch(PasswordsMisatchException exc) {
            idMessageDisplay.setText("Passwords do not correspond");
            idConfirmParola.setText("");
            idParola.setText("");
        }catch (Exception exc){
            exc.printStackTrace();
        }

    }
    public void Back(){
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
            Stage window = (Stage) idBack.getScene().getWindow();
            window.setScene(new Scene(root, 750, 500));
        }catch(Exception exc){
            exc.printStackTrace();
        }

    }
}
