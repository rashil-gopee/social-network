package View;

import Constant.ApplicationConstant;
import Model.AdultUserModel;
import Model.ConnectionModel;
import Model.UserModel;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.imageio.stream.FileImageInputStream;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class ViewProfileController {
    public UserModel userModel;

    @FXML
    ImageView photoImageView;

    @FXML
    TextField nameTextField;

    @FXML
    TextField statusTextField;

    @FXML
    TextField ageTextField;

    @FXML
    TextField genderTextField;

    @FXML
    TextField stateTextField;

    @FXML
    AnchorPane parentsAnchorPane;

    @FXML
    AnchorPane childrenAnchorPane;

    @FXML
    TextField parent1TextField;

    @FXML
    TextField parent2TextField;

    @FXML
    TableView childrenTableView;

    @FXML
    TableColumn childNameTableColumn;

    void initialize() {
    }

    void initData(UserModel userModel) {
        this.userModel = userModel;
        File file = new File(userModel.getPhoto());
        System.out.println(userModel.getPhoto());
        Image image;
        try {
            image = new Image(getClass().getResourceAsStream("/Resources/" + userModel.getPhoto()));
            if (!(image == null || image.isError())) {
                photoImageView.setImage(image);
            } else {
                image = new Image(getClass().getResourceAsStream("/Resources/noimage.png"));
                photoImageView.setImage(image);
            }
        }
        catch (Exception e){
            image = new Image(getClass().getResourceAsStream("/Resources/noimage.png"));
            photoImageView.setImage(image);
        }

        if (userModel.getUserName() != null)
            nameTextField.setText(userModel.getUserName());
        if (userModel.getStatus() != null)
            statusTextField.setText(userModel.getStatus());

        ageTextField.setText(new Integer(userModel.getAge()).toString());
        if (userModel.getGender() == 'M' || userModel.getGender() == 'M')
            genderTextField.setText("Male");
        else
            genderTextField.setText("Female");
        if (userModel.getState() != null)
            stateTextField.setText(userModel.getState());

        if (userModel instanceof AdultUserModel){
            ArrayList<ConnectionModel> children = new ArrayList<>();
            for (ConnectionModel connectionModel:userModel.getConnections()){
                if (connectionModel.getConnectionType().equals(ApplicationConstant.CHILD))
                    children.add(connectionModel);
            }
            if (children.size() > 0){
                childrenAnchorPane.setVisible(true);
                childrenTableView.getItems().setAll(children);
            }
        }
        else{
            ArrayList<String> parents = new ArrayList<>();
            parentsAnchorPane.setVisible(true);
            for (ConnectionModel connectionModel:userModel.getConnections()){
                if (connectionModel.getConnectionType().equals(ApplicationConstant.PARENT))
                    parents.add(connectionModel.getConnectionName());
                if (parents.size() == 2) {
                    parent1TextField.setText(parents.get(0));
                    parent2TextField.setText(parents.get(1));
                    break;
                }
            }
        }

    }

    public void goBack(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PersonMenu.fxml"));
        Stage mainWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
        mainWindow.setScene(new Scene(loader.load()));
        PersonMenuController personMenuController = loader.<PersonMenuController>getController();
        personMenuController.initData(userModel);
    }

    public void goToMainMenu(ActionEvent event) throws Exception{
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindow.setScene(new Scene(parent));
    }

}
