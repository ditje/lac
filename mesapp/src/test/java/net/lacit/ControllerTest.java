package net.lacit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ControllerTest {

    @Test
    public void loadParametersFromConfigFile() throws IOException {
//        Controller controller = new Controller();
//        selectedFile = (new File(".\\src\\test\\resources\\testFileWithProperties.conf"));
//        controller.loadParametersFromConfigFile(getSelectedFile());
//        System.out.println(getIp() + getTopic() + getPort());
    }

    @Test
    public void testLoadJSONFile() {
        Controller controller = new Controller();
        JSONObject jsonObject = controller.loadJSONFile(".\\src\\test\\resources\\testLoadingMessage.json");
        Assert.assertNotNull(jsonObject);
    }

    @Test
    public void testLoadJSONFileNull() throws IOException {
        Controller controller = new Controller();
        Assert.assertNull(controller.loadJSONFile("unknown path"));
    }
}
