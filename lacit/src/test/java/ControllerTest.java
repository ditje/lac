package java;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.java.Controller;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class ControllerTest {
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

    @Test
    public void loadParameters() throws IOException {
        Controller controller = new Controller();
        selectedFile = (new File(".\\src\\test\\resources\\testFileWithProperties.conf"));
        controller.loadParametersFromConfigFile(getSelectedFile());
        System.out.println(getIp() + getTopic() + getPort());
    }
}