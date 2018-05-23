package View;

import Constant.ApplicationConstant;
import Controller.Driver;
import Model.AdultUserModel;
import Model.ChildUserModel;
import Model.UserModel;
import Model.YoungChildUserModel;
import Utils.Helper;

import Exception.NotToBeClassmatesException;
import Exception.NotToBeFriendsException;
import Exception.NotToBeColleaguesException;
import Exception.NotTobeCoupledException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonMenuController {
    @FXML
    ComboBox connectionTypeComboBox;

    @FXML
    TextField txt_addConnectionName;

    public UserModel userModel;

    void initialize() {}
    void initData(UserModel userModel) {
        this.userModel = userModel;
        connectionTypeComboBox.getItems().removeAll(connectionTypeComboBox.getItems());
        connectionTypeComboBox.getItems().addAll(ApplicationConstant.CLASSMATE, ApplicationConstant.FRIEND, ApplicationConstant.SPOUSE, ApplicationConstant.COLLEAGUE, ApplicationConstant.SIBLING);
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
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindow.setScene(new Scene(parent));
    }

    public void addConnection(Event event) {
        Driver driver = new Driver();
        String connectionName = txt_addConnectionName.getText();
        String connectionType = connectionTypeComboBox.getValue().toString();

        UserModel connection = driver.getUser(connectionName);

        try {
            if (connection != null) {
                if (connectionType.equals(ApplicationConstant.SIBLING)) {
                    if (userModel instanceof YoungChildUserModel)
                        ((YoungChildUserModel) userModel).addSibling(connection);
                    else
                        throw new Exception("Only Young Child can add sibling.");
                } else if (connectionType.equals(ApplicationConstant.CLASSMATE)) {
                    if (userModel instanceof AdultUserModel)
                        ((AdultUserModel) userModel).addClassMate(connection);
                    else if (userModel instanceof ChildUserModel)
                        ((ChildUserModel) userModel).addClassMate(connection);
                    else
                        throw new NotToBeClassmatesException("Young child cannot add classmate.");
                }
                else if (connectionType.equals(ApplicationConstant.COLLEAGUE)){
                    if (userModel instanceof AdultUserModel)
                        ((AdultUserModel) userModel).addColleague(connection);
                    else
                        throw new NotToBeColleaguesException("Only adults can add colleague.");
                }
                else if (connectionType.equals(ApplicationConstant.SPOUSE)){
                    if (userModel instanceof AdultUserModel)
                        ((AdultUserModel) userModel).addSpouse(connection);
                    else
                        throw new NotTobeCoupledException("Only adults can add spouse.");
                }
                else if (connectionType.equals(ApplicationConstant.FRIEND)) {
                    if (userModel instanceof AdultUserModel)
                        ((AdultUserModel) userModel).addFriend(connection);
                    else if (userModel instanceof ChildUserModel)
                        ((ChildUserModel) userModel).addFriend(connection);
                    else
                        throw new NotToBeFriendsException("Young child cannot add friend.");
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Person does not exist!");
                alert.setContentText(connectionName + " is not a valid person.");
                alert.showAndWait();
            }
        }
        catch (Exception e){
            Helper.handleException(e);
        }
    }

}
