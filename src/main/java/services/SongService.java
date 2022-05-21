package services;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import java.awt.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sql.rowset.serial.SerialBlob;

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

            File f = new File(".\\src\\main\\resources\\temp\\"+ name +".mp3");
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bytes);
            fos.flush();
            fos.close();
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    public byte[] convertMP3ToByteArray(String name) {

        File file = new File(".\\src\\main\\resources\\temp\\" + name + ".mp3");
        byte[] bytes = new byte[(int) file.length()];

        FileInputStream fis = null;
        try {

            fis = new FileInputStream(file);

            //read file into bytes[]
            fis.read(bytes);
            if (fis != null) {
                fis.close();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return bytes;
    }
    public void insertSong(String name, String author,Blob mp3,String genre){
        try {
            String query = " insert into songs (name, author, mp3,genre)"
                    + " values (?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, author);
            preparedStatement.setBlob(3, mp3);
            preparedStatement.setString(4,genre);
            preparedStatement.execute();
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    void purgeDirectory(File dir) {
        for (File file: dir.listFiles()) {
            if (file.isDirectory())
                purgeDirectory(file);
            file.delete();
        }
    }
    /*
    private void playSong(String songName){
        Media hit = new Media(new File(songName).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }*/
    /*
    public static void main(String[] args){
        try {
            SongService player = new SongService();
            player.connectToDatabase("root", "amplify_admin69");
            //ResultSet set = player.getSongsByName("123");
            //set.absolute(1);
            //System.out.println(set.getString(1));
            //player.convertByteArrayToMP3(set.getString(1),set.getBytes(2));
            byte[] song_bytes=player.convertMP3ToByteArray("Kafana");
            Blob song_blob=new SerialBlob(song_bytes);
            player.insertSong("Kafana","Dubioza Kolektiv",song_blob,"Alternative");
            player.disconnectFromDatabase();
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    */
}
