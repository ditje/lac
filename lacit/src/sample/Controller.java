package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.sound.midi.Soundbank;
import javax.swing.*;

public class Controller  {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loadConfigFile;

    @FXML
    private Label ipValue;

    @FXML
    private Label portValue;

    @FXML
    private Label topicValue;

    @FXML
    private Label statusConfig;

    @FXML
    private AnchorPane anchorPane;

    String ip;
    int port;
    String topic;

    @FXML
    void initialize() {
        loadConfigFile.setOnAction(actionEvent -> {
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите конфигурационный файл");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Config file", "*.conf", ".cfg", ".ini"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(new File(String.valueOf(selectedFile))));
                if ((properties.getProperty("ip") == null) || (properties.getProperty("port") == null) || (properties.getProperty("topic") == null)) {
                    statusConfig.setText("Не все параметры указаны");
                } else {
                    ip = properties.getProperty("ip");
                    port = Integer.parseInt(properties.getProperty("port"));
                    topic = properties.getProperty("topic");
                    statusConfig.setText("Конфиг загружен");
                    ipValue.setText(ip);
                    portValue.setText(Integer.toString(port));
                    topicValue.setText(topic);
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
