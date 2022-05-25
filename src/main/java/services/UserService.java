package services;

import exceptions.UsernameAlreadyExistsException;
import exceptions.IncorrectPasswordException;
import exceptions.UserDoesntExistException;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.security.*;
import java.nio.charset.StandardCharsets;

public class UserService {
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;

    public static void connectToDatabase(String user, String password) {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/amplify_database?autoReconnect=true&useSSL=false",
                    user, password);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public static void disconnectFromDatabase() {
        try {
            connection.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public static void insertUser(String username, String password, String subscription) throws UsernameAlreadyExistsException {
        try {
            String query = " insert into users (username, password,subscription)"
                    + " values (?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, encodePassword(username, password));
            preparedStatement.setString(3, subscription);
            preparedStatement.execute();
        } catch (SQLIntegrityConstraintViolationException exc) {
            throw new UsernameAlreadyExistsException(username);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public static void insertUser(String username, String password, Blob profile_picture, String subscription) throws UsernameAlreadyExistsException {
        try {
            String query = " insert into users (username, password, profile_picture,subscription)"
                    + " values (?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, encodePassword(username, password));
            preparedStatement.setBlob(3, profile_picture);
            preparedStatement.setString(4, subscription);
            preparedStatement.execute();
        } catch (Exception exc) {
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

    public String getUser(String username, String password) {
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "select * from users where username='" + username + "'";

            ResultSet resultSet = statement.executeQuery(query);
            resultSet.absolute(1);
            return resultSet.getString("username");
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }

    public static Boolean isUserInDatabase(String username, String password) throws UserDoesntExistException, IncorrectPasswordException {
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "select * from users where username='" + username + "'";

            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next() == false) {
                throw new UserDoesntExistException();
            } else {

                statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                query = "select * from users where username='" + username + "' and password ='"
                        + encodePassword(username, password) + "'";

                resultSet = statement.executeQuery(query);
                if (resultSet.next() == false) {
                    throw new IncorrectPasswordException();
                } else return true;
            }
        } catch (java.sql.SQLException exc) {
            exc.printStackTrace();
        }
        return true;
    }

    void purgeDirectory(File dir) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory())
                purgeDirectory(file);
            file.delete();
        }
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


}
/*
    public static void main(String[] args){

        if((UserService.encodePassword("amplify","123456")).equals(UserService.encodePassword("amplify","123456"))){
            System.out.println("true");
        }
    }

}
*/