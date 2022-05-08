package services;

import java.sql.*;
import java.awt.*;
import java.io.File;

public class UserService extends Canvas{
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    public void connectToDatabase(String user, String password){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/amplify_database",
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
            preparedStatement.setString (2, password);
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
            preparedStatement.setString(2, password);
            preparedStatement.setBlob(3, profile_picture);
            preparedStatement.execute();
        }catch(SQLIntegrityConstraintViolationException exc){
            System.out.println("User already exists!");
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    /*
    public static void main(String[] args){
        UserService UserService_manager=new UserService();
        UserService_manager.connectToDatabase("root","amplify_admin69");
        UserService_manager.insertUser("Johnutz","1234");
        //UserService_manager.disconnectFromDatabase();
    }
    */
}
