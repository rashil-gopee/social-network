package Model;

import Constant.ApplicationConstant;
import Interface.iNotYoungChild;

import java.util.Set;

public class ChildUserModel extends UserModel implements iNotYoungChild {
    public ChildUserModel(String userName, int age, String status, String photo, char gender, String state, Set<String> parents) {
        super(userName, age, status, photo, gender, state);
        for (String parent : parents){
            this.getConnections().add(new ConnectionModel(parent, ApplicationConstant.PARENT));
        }
    }

    public void addClassMate(UserModel userModel){
        if (userModel.getAge() <= 16)
            this.addConnection(userModel, ApplicationConstant.CLASSMATE);
    }

    public void addFriend(UserModel userModel){
        if (userModel.getAge() <= 16)
            this.addConnection(userModel, ApplicationConstant.FRIEND);
    }


}
