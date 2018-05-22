/**
 * @author Isswarraj Gopee
 */

package Model;

import Constant.ApplicationConstant;

import Interface.iNotYoungChild;


public class AdultUserModel extends UserModel implements iNotYoungChild {
    public AdultUserModel(String userName, int age, String status, String photo, char gender, String state) {
        super(userName, age, status,photo,gender,state);
    }

    public void addColleague(UserModel userModel){
        if (userModel.getAge() > 16) {
            this.addConnection(userModel, ApplicationConstant.COLLEAGUE);
            userModel.addConnection(this, ApplicationConstant.COLLEAGUE);
        }
    }

    public void addClassMate(UserModel userModel){
        if (userModel.getAge() > 16) {
            this.addConnection(userModel, ApplicationConstant.CLASSMATE);
            userModel.addConnection(this, ApplicationConstant.CLASSMATE);
        }
    }

    public void addFriend(UserModel userModel){
        if (userModel.getAge() > 16) {
            this.addConnection(userModel, ApplicationConstant.FRIEND);
            userModel.addConnection(this, ApplicationConstant.FRIEND);
        }
    }

    public void addSpouse(UserModel userModel){
        if (userModel.getAge() > 16) {
            this.addConnection(userModel, ApplicationConstant.SPOUSE);
            userModel.addConnection(this, ApplicationConstant.SPOUSE);
        }
    }

    public void addChild(UserModel userModel){
        if (userModel.getAge() > 16) {
            this.addConnection(userModel, ApplicationConstant.CHILD);
        }
    }

    public Boolean verifySpouseName (String spouseName){
        for (ConnectionModel connectionModel : this.getConnections()){
            if (connectionModel.getConnectionName().equals(spouseName))
                return true;
        }
        return false;
    }

}
