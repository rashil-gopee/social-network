package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public class MainController {
    public void pressButton(ActionEvent event) throws Exception{
        System.out.println("Button clicked!");
        Parent addFriend = FXMLLoader.load(getClass().getResource("AddPerson.fxml"));
        Stage mainWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
        mainWindow.setScene(new Scene(addFriend));
    }
}
