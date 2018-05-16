/**
 * @author Isswarraj Gopee
 */

package Model;

import Constant.ApplicationConstant;

public class AdultUserModel extends UserModel {
    public AdultUserModel(String userName, int age, String status, String photo, char gender, String state) {
        super(userName, age, status,photo,gender,state);
    }

    public void addConnection(UserModel user, String connectionType) {
        if (user.getAge() > 16) {
            this.getConnections().add(new ConnectionModel(user.getUserName(), connectionType));
            user.getConnections().add(new ConnectionModel(this.getUserName(), connectionType));
        } else if (user.getAge() < 16) {
            System.out.println("Cannot add dependent as connection!");
        }
    }

}
