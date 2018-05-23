/**
 * @author Isswarraj Gopee
 */

package Model;

import Constant.ApplicationConstant;

import java.util.HashSet;
import java.util.Set;

import Exception.NoParentException;

public class YoungChildUserModel extends UserModel {
    public YoungChildUserModel(String userName, int age, String status, String photo, char gender, String state, Set<String> parents) throws Exception{
        super(userName, age, status, photo, gender, state);
        if (parents.size() != 2) {
            throw new NoParentException("Young child should have 2 parents.");
        }
        for (String parent : parents){
            this.getConnections().add(new ConnectionModel(parent, ApplicationConstant.PARENT));
        }
    }

    public void removeConnection(String connectionName) throws Exception{
        for (ConnectionModel connection : this.getConnections()) {
            if (connection.getConnectionName().equals(connectionName)) {
                if (!connection.getConnectionType().equals(ApplicationConstant.PARENT)) {
                    this.getConnections().remove(connection);
                    return;
                }
                else
                    throw new NoParentException("Cannot delete parent as a connection.");
            }
        }
    }

    public void addSibling(UserModel userModel) throws Exception{
        if (userModel instanceof YoungChildUserModel) {
            if (((YoungChildUserModel) userModel).verifyParents(this.getParentsNames())) {
                addConnection(userModel, ApplicationConstant.SIBLING);
            }
        }
        else
            throw new Exception("Only Young Child can be added as sibling.");
    }

    public boolean verifyParents(Set<String> parents) {
        return (this.getParentsNames().containsAll(parents));
    }

    public Set<String> getParentsNames() {
        Set<String> parents = new HashSet<>();
        for (ConnectionModel connection : this.getConnections()) {
            if (connection.getConnectionType().equals(ApplicationConstant.PARENT)) {
                parents.add(connection.getConnectionName());
                if (parents.size() == 2)
                    break;
            }
        }
        return parents;
    }

}
