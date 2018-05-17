/**
 * @author Isswarraj Gopee
 */

package Model;

import Constant.ApplicationConstant;

import Interface.iNotYoungChild;

import java.util.Set;

public class AdultUserModel extends UserModel implements iNotYoungChild {
    public AdultUserModel(String userName, int age, String status, String photo, char gender, String state) {
        super(userName, age, status,photo,gender,state);
    }

    public void addColleague(UserModel userModel){
        if (userModel.getAge() > 16)
            this.addConnection(userModel, ApplicationConstant.COLLEAGUE);
    }

    public void addClassMate(UserModel userModel){
        if (userModel.getAge() > 16)
        this.addConnection(userModel, ApplicationConstant.CLASSMATE);
    }

    public void addFriend(UserModel userModel){
        if (userModel.getAge() > 16)
        this.addConnection(userModel, ApplicationConstant.FRIEND);
    }

}
