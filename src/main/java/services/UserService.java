package services;

import java.sql.*;
import java.awt.*;
import java.security.*;
import java.nio.charset.StandardCharsets;


public class UserService extends Canvas{
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    public void connectToDatabase(String user, String password){
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/amplify_database?autoReconnect=true&useSSL=false",
                    user,password);
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    public void disconnectFromDatabase(){
        try{
            connection.close();
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }

    public void insertUser(String username, String password){
        try{
            String query=" insert into users (username, password)"
                    + " values (?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString (1, username);
            preparedStatement.setString (2, encodePassword(username,password));
            preparedStatement.execute();
        }catch(SQLIntegrityConstraintViolationException exc){
            System.out.println("User already exists!");
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    public void insertUser(String username, String password,Blob profile_picture){
        try {
            String query = " insert into users (username, password, profile_picture)"
                    + " values (?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, encodePassword(username,password));
            preparedStatement.setBlob(3, profile_picture);
            preparedStatement.execute();
        }catch(SQLIntegrityConstraintViolationException exc){
            System.out.println("User already exists!");
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    public String returnUser(String username, String password){
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,       ResultSet.CONCUR_UPDATABLE);
            String query = "select * from users where username='"+username+"'";

            ResultSet resultSet = statement.executeQuery(query);
            resultSet.absolute(1);
            return resultSet.getString("username");
        }catch(Exception exc){
            exc.printStackTrace();
        }
        return null;
    }
    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    /*
    public static void main(String[] args){
        UserService UserService_manager=new UserService();
        UserService_manager.connectToDatabase("root","amplify_admin69");
        System.out.println(UserService_manager.returnUser("RaMi_Admin","admin_amplify69"));
    }
    */
}
