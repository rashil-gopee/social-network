package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


public class AddPersonController {
    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_photo;

    @FXML
    private TextField txt_status;

    @FXML
    private RadioButton radio_male;

    @FXML
    private RadioButton radio_female;

    @FXML
    private TextField txt_age;

    @FXML
    private TextField txt_state;

    @FXML
    private ToggleGroup genderToggle;

    @FXML
    public void savePerson(ActionEvent event) throws Exception {
        System.out.println("Button clicked!");

        String name = txt_name.getText();
        String photo = txt_photo.getText();
        String status = txt_photo.getText();
        char gender = (char) 0;
        if (genderToggle.getSelectedToggle() == radio_male)
            gender = 'M';
        else if (genderToggle.getSelectedToggle() == radio_male)
            gender = 'F';
        String state = txt_state.getText();
        int age = Integer.valueOf(txt_age.getText());

        System.out.println("Name: " + name);
        System.out.println("Name: " + photo);
        System.out.println("Name: " + status);
        System.out.println("Name: " + gender);
        System.out.println("Name: " + age);
        System.out.println("Name: " + state);

        Parent parent = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        Stage mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindow.setScene(new Scene(parent));
    }

}
