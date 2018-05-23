/**
 * @author Isswarraj Gopee
 */

package Model;

import Constant.ApplicationConstant;
import Interface.iNotYoungChild;

import Exception.NoParentException;
import Exception.NotToBeFriendsException;
import Exception.NotToBeClassmatesException;
import Exception.TooYoungException;

import java.util.Set;

public class ChildUserModel extends UserModel implements iNotYoungChild {
    public ChildUserModel(String userName, int age, String status, String photo, char gender, String state, Set<String> parents) throws Exception {
        super(userName, age, status, photo, gender, state);
        if (parents.size() != 2) {
            throw new NoParentException("Child should have 2 parents.");
        }
        for (String parent : parents) {
            this.getConnections().add(new ConnectionModel(parent, ApplicationConstant.PARENT));
        }
    }

    public void addClassMate(UserModel userModel) throws Exception {
        if (userModel instanceof ChildUserModel) {
            this.addConnection(userModel, ApplicationConstant.CLASSMATE);
        } else {
            throw new NotToBeClassmatesException("Child can be classmate with child only.");
        }
    }

    public void addFriend(UserModel userModel) throws Exception {
        if (userModel instanceof ChildUserModel) {
            if (((this.getAge() > userModel.getAge()) && (this.getAge() - userModel.getAge() <= 3)) || ((userModel.getAge() > this.getAge()) && (userModel.getAge() - this.getAge() <= 3))) {
                this.addConnection(userModel, ApplicationConstant.FRIEND);
            } else {
                throw new NotToBeFriendsException("Cannot add person as friend. Age difference is greater than 3.");
            }
        } else if (userModel instanceof YoungChildUserModel)
            throw new TooYoungException("Cannot be friend with young child.");
        else {
            throw new NotToBeFriendsException("Child can be friend with child only");
        }
    }

    public void removeConnection(String connectionName) throws Exception {
        for (ConnectionModel connection : this.getConnections()) {
            if (connection.getConnectionName().equals(connectionName)) {
                if (!connection.getConnectionType().equals(ApplicationConstant.PARENT)) {
                    this.getConnections().remove(connection);
                    return;
                } else
                    throw new NoParentException("Cannot delete parent as a connection.");
            }
        }
    }
}
