package View;

import Controller.Driver;
import Model.UserModel;
import Utils.Helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class PersonMenuController {
    public UserModel userModel;

    void initialize() {}
    void initData(UserModel userModel) {
        this.userModel = userModel;
    }

    public void viewProfile(ActionEvent event){
//        System.out.println("Test");
        System.out.println("Passed user:" + userModel.getUserName());
    }

    public void deletePerson(ActionEvent event){
        Driver driver = new Driver();
        try{
            driver.deleteUser(userModel);
            driver.printUsers();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("");
            alert.setContentText("Person was successfully deleted.");
            alert.showAndWait();
            goToMainMenu(event);
        }
        catch (Exception e){
            Helper.handleException(e);
        }
    }

    public void goToMainMenu(ActionEvent event) throws Exception{
        Parent parent = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        Stage mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindow.setScene(new Scene(parent));
    }

}
