/**
 * @author Isswarraj Gopee
 */

package Model;

import Constant.ApplicationConstant;

import java.util.ArrayList;

public abstract class UserModel {
    private String userName;
    private int age;
    private String status;

    private String photo;
    private char gender;
    private String state;

    private ArrayList<ConnectionModel> connections;

    public UserModel(){}

    public UserModel(String userName, int age, String status, String photo, char gender, String state){
        this.userName = userName;
        this.age = age;
        this.status = status;
        this.photo = photo;
        this.gender = gender;
        this.state = state;
        connections = new ArrayList<>();
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

    public ArrayList<ConnectionModel> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<ConnectionModel> connections) {
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

    public void removeConnection(String connectionName){
        for (int i = 0; i < this.connections.size(); i++){
            if (this.connections.get(i).getConnectionName().equals(connectionName)){
                connections.remove(this.connections.get(i));
                break;
            }
            else if (i == (this.connections.size() - 1)){
                System.out.println("Error! " + userName + " is not a connection!");
            }
        }
    }

    public abstract void addConnection(UserModel user, String relationType);

}
