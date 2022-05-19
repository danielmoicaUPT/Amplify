package services;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.io.FileOutputStream;
import java.awt.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SongService {
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
    public ResultSet getSongsByName(String name){
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String query = "select * from songs where name='"+name+"'";

            ResultSet resultSet = statement.executeQuery(query);
            return resultSet;
        }catch(Exception exc){
            exc.printStackTrace();
        }
        return null;
    }
    public ResultSet getSongsByGenre(String genre){
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String query = "select * from songs where genre='"+genre+"'";

            ResultSet resultSet = statement.executeQuery(query);
            return resultSet;
        }catch(Exception exc){
            exc.printStackTrace();
        }
        return null;
    }
    public void convertByteArrayToMP3(String name, byte[] bytes){
        try {
            File f = new File("C:\\Users\\Utilizator\\IdeaProjects\\Amplify\\src\\main\\resources\\temp\\"+ name +".mp3");
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bytes);
            fos.flush();
            fos.close();
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    public void convertMP3ToByteArray(){
        //TO BE IMPLEMENTED
    }
    /*
    private void playSong(String songName){
        Media hit = new Media(new File(songName).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }*/
    public static void main(String[] args){
        try {
            SongService player = new SongService();
            player.connectToDatabase("root", "amplify_admin69");
            ResultSet set = player.getSongsByName("123");
            set.absolute(1);
            System.out.println(set.getString(1));
            player.convertByteArrayToMP3(set.getString(1),set.getBytes(2));
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }


}
