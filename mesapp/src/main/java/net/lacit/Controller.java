package net.lacit;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import com.sun.xml.internal.ws.api.ResourceLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.ls.LSOutput;

import javax.print.DocFlavor;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Controller {
    private final String INPUT_SCHEMA_PATH = (getClass().getResource("/inPutSchema.json").getPath());
    private final String OUTPUT_SCHEMA_PATH = (getClass().getResource("/outPutSchema.json").getPath());
    //private final String MESSAGE_PATH = String.valueOf(getClass().getResource("/message.json"));

    private String ip;
    private int port;
    private String topic;
    private File selectedFile;
    private JSONObject message;


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


    //загрузка конфига
    @FXML
    private void loadConfigButton() throws FileNotFoundException {
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

    //загрузка и проверка сообщения
    @FXML
    private void loadMessageButton() {
        message = loadMessageFromServer();
        validateMessage(INPUT_SCHEMA_PATH);
        validateOutPutMessage(OUTPUT_SCHEMA_PATH);
    }

    //загрузить сообщение в другой токен
    @FXML
    public void loadMessageToServer() {
    }

    //загрузка сообщение с сервера
    @FXML
    public JSONObject loadMessageFromServer() {
        JSONObject jsonObject = null;
        try (Socket clientSocket = new Socket(InetAddress.getLocalHost(), 8080)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            jsonObject = new JSONObject(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    //загрузка параметров из конфигурационного файла
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
            ipValue.setText(String.valueOf(getIp()));
            portValue.setText(Integer.toString(getPort()));
            topicValue.setText(String.valueOf(getTopic()));
        }
    }

    //вывод ошибки
    public void showError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    //загрузка Json схемы/сообщения
    public JSONObject loadJSONFile(String schemaPath) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new JSONTokener((new FileInputStream(new File(schemaPath)))));
        } catch (FileNotFoundException e) {
            showError("Selected file not found");
        }
        return jsonObject;
    }

    //Проверка валидации входящено сообщения
    private void validateMessage(String schemaPath) {
        try {
            Schema schemaValidator = SchemaLoader.load(loadJSONFile(schemaPath));
            schemaValidator.validate(message);
            message.put("validation", true);
        } catch (ValidationException e) {
            message.put("validation", false);
        }
    }

    //создание выходящего сообщения и запись в него
    private void writeMessage(String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(String.valueOf(message));
        } catch (IOException e) {
            showError("Ошибка записи файла");
        }
    }

    //валидация выходящего сообщения
    private void validateOutPutMessage(String schemaPath) {
        try {
            Schema schemaValidator = SchemaLoader.load(loadJSONFile(schemaPath));
            schemaValidator.validate(message);
        } catch (ValidationException e) {
            message.put("validation", false);
        }
        writeMessage("outPutMessage.json");
    }
}
