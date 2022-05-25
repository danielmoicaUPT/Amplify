package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.media.*;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.net.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.time.*;

public class PlayerController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBarr;

    private Media media;
    private MediaPlayer mediaPlayer;

    private File directory;
    private File[] files;

    private ArrayList<File> songs;
    private int songNumber;
    private int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};

    private Timer timer;
    private TimerTask task;
    private boolean running;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        songs = new ArrayList<File>();

        directory = new File("music");

        files = directory.listFiles();

        if(files != null) {
            for (File file : files) {
                songs.add(file);
                System.out.println(file);
            }
        }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            for(int i = 0; i<speeds.length; i++){
            speedBox.getItems().add(Integer.toString(speeds[i])+"%");
        }
            speedBox.setOnAction(this::changeSpeed);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });
        songProgressBarr.setStyle("-fx-accent: #00ff00;");
    }



    public void playMedia(){
        beginTimer();
        changeSpeed(null);
        mediaPlayer.play();
    }
    public void pauseMedia(){
        cancelTimer();
        mediaPlayer.pause();
    }
    public void resetMedia(){
        //songProgressBar.setProgress(0);
        mediaPlayer.seek(Duration.seconds(0));
    }
    public void previousMedia(){
        if(songNumber > 0) {
            songNumber--;
            mediaPlayer.stop();
            if(running){
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            playMedia();
        }
        else {
            songNumber = songs.size() - 1;
            mediaPlayer.stop();
            if(running){
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            playMedia();
        }
    }
    public void nextMedia(){
        if(songNumber < songs.size() - 1) {
            songNumber++;
            mediaPlayer.stop();
            if(running){
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            playMedia();
        }
        else {
            songNumber = 0;
            mediaPlayer.stop();
            if(running){
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            playMedia();
        }
    }
    @FXML
    private void changeSpeed(ActionEvent actionEvent) {
        if (speedBox.getValue() == null) {
            mediaPlayer.setRate(1);
        } else {
            //mediaPlayer.setRate(Integer.parseInt(speedBox.getValue())* 0.01);
            mediaPlayer.setRate(Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() - 1)) * 0.01);
        }
    }
    public void beginTimer(){
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songProgressBarr.setProgress(current/end);

                if(current/end == 1){
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task,1000,1000);
    }
    public void cancelTimer(){
        running = false;
        timer.cancel();
    }
}
