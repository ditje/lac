package main.java;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Controller {

    private String ip;
    private int port;
    private String topic;
    private File selectedFile;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

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

    @FXML
    void initialize() {
    }

    public void onClickMethod() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите конфигурационный файл");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Config file", "*.conf", ".cfg", ".ini"));
        setSelectedFile(fileChooser.showOpenDialog(stage));
        ipValue.setText("");
        portValue.setText("");
        topicValue.setText("");
        loadParametersFromConfigFile(getSelectedFile());
    }

    public void loadParametersFromConfigFile(File selectedFile) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(String.valueOf(selectedFile))));
            if ((properties.getProperty("ip") == null) || (properties.getProperty("port") == null) || (properties.getProperty("topic") == null)) {
                statusConfig.setText("Не все параметры указаны");
            } else {
                statusConfig.setText("Конфиг загружен");
                setIp(properties.getProperty("ip"));
                setPort(Integer.parseInt(properties.getProperty("port")));
                setTopic(properties.getProperty("topic"));
                ipValue.setText(getIp());
                portValue.setText(String.valueOf(getPort()));
                topicValue.setText(getTopic());
            }
        } catch (IOException e) {
            System.out.println("catch");
           /*ipValue.setText(String.valueOf(getIp()));
            portValue.setText(Integer.toString(getPort()));
            topicValue.setText(String.valueOf(getTopic()));*/
        }
    }

    public void validationMessages(){

    }

}
