package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class AddFriend {
    @FXML
    private TextField txt_name;
    public void savePerson(ActionEvent event) throws Exception{
        System.out.println("Button clicked!");

        System.out.println(txt_name);

        Parent addFriend = FXMLLoader.load(getClass().getResource("AddPerson.fxml"));
        Stage mainWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
        mainWindow.setScene(new Scene(addFriend));
    }
}
