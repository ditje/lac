package sample;

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

    String ip;
    int port;
    String topic;
    File selectedFile;

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
    void initialize(){
    }

    public void onClickMethod() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите конфигурационный файл");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Config file", "*.conf", ".cfg", ".ini"));
        selectedFile = fileChooser.showOpenDialog(stage);
        ipValue.setText("");
        portValue.setText("");
        topicValue.setText("");
        loadParametersFromConfigFile(selectedFile);
    }

    public void loadParametersFromConfigFile(File selectedFile){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(String.valueOf(selectedFile))));
            if ((properties.getProperty("ip") == null) || (properties.getProperty("port") == null) || (properties.getProperty("topic") == null)) {
                statusConfig.setText("Не все параметры указаны");
            } else {
                statusConfig.setText("Конфиг загружен");
                ipValue.setText(ip = properties.getProperty("ip"));
                portValue.setText(Integer.toString(port = Integer.parseInt(properties.getProperty("port"))));
                topicValue.setText(topic = properties.getProperty("topic"));
            }
        }catch (IOException e) {
            ipValue.setText(ip);
            portValue.setText(Integer.toString(port));
            topicValue.setText(topic);
        }
    };

}
