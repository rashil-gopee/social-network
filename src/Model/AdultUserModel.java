/**
 * @author Isswarraj Gopee
 */

package Model;

import Constant.ApplicationConstant;

import Interface.iNotYoungChild;
import Exception.NotToBeFriendsException;
import Exception.NotToBeClassmatesException;
import Exception.NotTobeCoupledException;
import Exception.NotToBeColleaguesException;

public class AdultUserModel extends UserModel implements iNotYoungChild {
    public AdultUserModel(String userName, int age, String status, String photo, char gender, String state) throws Exception{
        super(userName, age, status,photo,gender,state);
    }

    public void addColleague(UserModel userModel) throws Exception{
        if (userModel instanceof AdultUserModel) {
            this.addConnection(userModel, ApplicationConstant.COLLEAGUE);
            userModel.addConnection(this, ApplicationConstant.COLLEAGUE);
        }
        else
            throw new NotToBeColleaguesException("Adult cannot has child as colleague.");
    }

    public void addClassMate(UserModel userModel) throws Exception{
        if (userModel instanceof AdultUserModel) {
            this.addConnection(userModel, ApplicationConstant.CLASSMATE);
            userModel.addConnection(this, ApplicationConstant.CLASSMATE);
        }
        else
            throw new NotToBeClassmatesException("Adult cannot be classmate with a child.");
    }

    public void addFriend(UserModel userModel) throws Exception{
        if (userModel instanceof AdultUserModel) {
            this.addConnection(userModel, ApplicationConstant.FRIEND);
            userModel.addConnection(this, ApplicationConstant.FRIEND);
        }
        else
            throw new NotToBeFriendsException("Adult cannot be friend with a child.");
    }

    public void addSpouse(UserModel userModel) throws Exception{
        if (userModel instanceof AdultUserModel) {
            if (!this.verifyIfSingle())
                throw new NotTobeCoupledException(this.getUserName() + " already has a spouse.");
            if (!((AdultUserModel)userModel).verifyIfSingle())
                throw new NotTobeCoupledException(userModel.getUserName() + " already has a spouse.");
            else {
                this.addConnection(userModel, ApplicationConstant.SPOUSE);
                userModel.addConnection(this, ApplicationConstant.SPOUSE);
            }
        }
        else
            throw new NotTobeCoupledException("Cannot have a child as spouse.");
    }

    public void addChild(UserModel userModel){
        if (userModel instanceof AdultUserModel) {
            this.addConnection(userModel, ApplicationConstant.CHILD);
        }
    }

    public Boolean verifySpouseName(String spouseName){
        for (ConnectionModel connectionModel : this.getConnections()){
            if (connectionModel.getConnectionName().equals(spouseName))
                return true;
        }
        return false;
    }

    public Boolean verifyIfSingle(){
        for (ConnectionModel connectionModel : this.getConnections()){
            if (connectionModel.getConnectionType().equals(ApplicationConstant.SPOUSE))
                return false;
        }
        return true;
    }

    public void removeConnection (String connectionName) throws Exception{
        for (ConnectionModel connection : this.getConnections()){
            if (connection.getConnectionName().equals(connectionName))
            {
                this.getConnections().remove(connection);
                return;
            }
        }
    }

}
