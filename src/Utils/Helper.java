package Utils;

import javafx.scene.control.Alert;

public class Helper {
    public static void handleException(Exception e){
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error!");
        alert.setHeaderText("");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        return;
    }
}
