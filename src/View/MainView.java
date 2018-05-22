package View;

import Controller.UserController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public class MainView {
    public void pressButton(ActionEvent event) throws Exception{
        System.out.println("Button clicked!");
        Parent addFriend = FXMLLoader.load(getClass().getResource("AddFriend.fxml"));
        Stage mainWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
        mainWindow.setScene(new Scene(addFriend));
    }
}
