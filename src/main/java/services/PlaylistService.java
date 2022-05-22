package services;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class PlaylistService {
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
    public void createPlaylist(String name,String creator){
        try{
            String query=" insert into playlists (name, creator)"
                    + " values (?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString (1, name);
            preparedStatement.setString (2, creator);
            preparedStatement.execute();
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    public ResultSet getPlaylists(String creator){
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String query = "select * from playlists where creator='"+creator+"'";

            ResultSet resultSet = statement.executeQuery(query);
            return resultSet;
        }catch(Exception exc){
            exc.printStackTrace();
        }
        return null;
    }
    public void addSongToPlaylist(String creator, String name, String song_id){
        try{
            ResultSet playlists=this.getPlaylists(creator);
            while(playlists.next()){
                if(playlists.getString("name")==name){
                    String oldIDString=playlists.getString(4);
                    if(oldIDString==null){
                        playlists.updateString(4,song_id);
                    }else {
                        String newIDString=oldIDString+";"+song_id;
                        playlists.updateString(4,oldIDString);
                    }
                }
            }
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    public String[] getSongIDs(String name, String creator){
        try{
            ResultSet playlists=this.getPlaylists(creator);
            while(playlists.next()){
                if(playlists.getString("name")==name){
                    String songsString=playlists.getString(4);
                    System.out.println(songsString);
                    String[] songs=songsString.split("\\;");
                    return songs;
                }
            }
        }catch(Exception exc){
            exc.printStackTrace();
        }
        return null;
    }
    public void disconnectFromDatabase(){
        try{
            connection.close();
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }
    /*
    public static void main(String[] args){

    }
    */
}
