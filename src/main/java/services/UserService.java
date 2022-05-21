package services;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.awt.*;


public class UserService{
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
    public byte[] convertPNGToByteArray(String name) {

        File file = new File(".\\src\\main\\resources\\temp\\" + name + ".png");
        byte[] bytes = new byte[(int) file.length()];

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(bytes);
            if (fis != null) {
                fis.close();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return bytes;
    }
    public String getUser(String username, String password){
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
    void purgeDirectory(File dir) {
        for (File file: dir.listFiles()) {
            if (file.isDirectory())
                purgeDirectory(file);
            file.delete();
        }
    }
    /*
    public static void main(String[] args){
        UserService UserService_manager=new UserService();
        UserService_manager.connectToDatabase("root","amplify_admin69");
        System.out.println(UserService_manager.getUser("RaMi_Admin","admin_amplify69"));
    }
    */
}
