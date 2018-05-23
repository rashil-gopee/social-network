package View;

import Controller.Driver;
import Model.UserModel;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.util.Optional;

public class MainMenuController {
    public void pressButton(ActionEvent event) throws Exception{
        System.out.println("Button clicked!");
        Parent addFriend = FXMLLoader.load(getClass().getResource("AddPerson.fxml"));
        Stage mainWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
        mainWindow.setScene(new Scene(addFriend));
    }

    public void selectPerson(ActionEvent event) throws Exception{
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Select Person");
//        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Enter person's name:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Driver driver =  new Driver();
            UserModel userModel = driver.getUser(result.get());
            if (userModel != null) {
                System.out.println("Your name: " + result.get());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PersonMenu.fxml"));

                Stage mainWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
                mainWindow.setScene(new Scene(loader.load()));

                PersonMenuController personMenuController = loader.<PersonMenuController>getController();
                personMenuController.initData(userModel);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Person does not exist!");
                alert.setContentText(result.get() + " is not a valid person.");
                alert.showAndWait();
            }
        }

// The Java 8 way to get the response value (with lambda expression).
//        result.ifPresent(name -> System.out.println("Your name: " + name));
    }

    public void exit(Event event){
        Stage mainWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
        mainWindow.hide();
        System.exit(1);
    }
}
