package View;

import Model.UserModel;
import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.stream.FileImageInputStream;

import java.io.File;

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

    void initialize() {
    }

    void initData(UserModel userModel) {
        this.userModel = userModel;
        File file = new File(userModel.getPhoto());
        System.out.println(userModel.getPhoto());
        Image image = new Image(getClass().getResourceAsStream("/Resources/" + userModel.getPhoto()));
        if (!(image == null || image.isError())) {
            photoImageView.setImage(image);
        } else {
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
    }
}
