import Controller.Driver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public class Mininet extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Driver driver = new Driver();
        if (driver.init()) {
            Parent root = FXMLLoader.load(getClass().getResource("View/MainView.fxml"));
            primaryStage.setTitle("Mininet - Social Network");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No datasource found!");
            alert.setContentText("Neither people.txt nor mininet.db file was found!\nPlease create a people.txt file and launch the application again.");

            alert.showAndWait();
            System.exit(1);
        }
    }

    public void pressButton(ActionEvent event) throws Exception{
        System.out.println("Button clicked!");
        Parent addFriend = FXMLLoader.load(getClass().getResource("View/AddFriend.fxml"));
        Stage mainWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
        mainWindow.setScene(new Scene(addFriend));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
