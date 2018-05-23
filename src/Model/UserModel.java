/**
 * @author Isswarraj Gopee
 */

package Model;

import Constant.ApplicationConstant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Exception.NoSuchAgeException;

public abstract class UserModel {
    private String userName;
    private int age;
    private String status;

    private String photo;
    private char gender;
    private String state;

    private Set<ConnectionModel> connections;

    public UserModel(String userName, int age, String status, String photo, char gender, String state) throws Exception{
        this.userName = userName;
        if (age < 0)
            throw new NoSuchAgeException("Age cannot be negative.");
        else if (age > 150)
            throw new NoSuchAgeException("Age cannot be greater than 150.");
        this.age = age;
        this.status = status;
        this.photo = photo;
        this.gender = gender;
        this.state = state;
        connections = new HashSet<ConnectionModel>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<ConnectionModel> getConnections() {
        return connections;
    }

    public void setConnections(Set<ConnectionModel> connections) {
        this.connections = connections;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean verifyConnection(String friendName){
        for (ConnectionModel connection : this.getConnections()){
            if (connection.getConnectionName().equals(friendName))
                return true;
        }
        return false;
    }

    public void addConnection(UserModel user, String connectionType) {
        this.getConnections().add(new ConnectionModel(user.getUserName(), connectionType));
        user.getConnections().add(new ConnectionModel(this.getUserName(), connectionType));
    }

    public abstract void removeConnection (String connectionName) throws Exception;

}
