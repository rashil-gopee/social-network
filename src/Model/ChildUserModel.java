package Model;

import Constant.ApplicationConstant;
import Interface.iNotYoungChild;

import Exception.NoParentException;
import Exception.NotToBeFriendsException;
import Exception.NotToBeClassmatesException;

import java.util.Set;

public class ChildUserModel extends UserModel implements iNotYoungChild {
    public ChildUserModel (String userName, int age, String status, String photo, char gender, String state, Set<String> parents) throws Exception {
        super(userName, age, status, photo, gender, state);
        if (parents.size() != 2) {
            throw new NoParentException("Child should have only 2 parents.");
        }
        for (String parent : parents){
            this.getConnections().add(new ConnectionModel(parent, ApplicationConstant.PARENT));
        }
    }

    public void addClassMate(UserModel userModel) throws Exception{
        if (userModel.getAge() <= 16) {
            this.addConnection(userModel, ApplicationConstant.CLASSMATE);
        }
        else{
            throw new NotToBeClassmatesException("A child cannot be classmate with an adult.");
        }
    }

    public void addFriend(UserModel userModel) throws Exception{
        if (userModel.getAge() <= 16) {
            if (((this.getAge() > userModel.getAge()) && (this.getAge() - userModel.getAge() <= 3)) || ((userModel.getAge() > this.getAge()) && (userModel.getAge() - this.getAge() <= 3))) {
                this.addConnection(userModel, ApplicationConstant.FRIEND);
            }
            else{
                throw new NotToBeFriendsException("Cannot add person as friend. Age difference is greater than 3.");
            }
        }
        else{
            throw new NotToBeFriendsException("A child cannot be friend with an adult.");
        }
    }
}
