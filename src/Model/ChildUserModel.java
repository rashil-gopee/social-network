package Model;

import Constant.ApplicationConstant;

public class ChildUserModel extends UserModel {
    public ChildUserModel(String userName, int age, String status, String photo, char gender, String state, String[] parents) {
        super(userName, age, status,photo,gender,state);
        this.getConnections().add(new ConnectionModel(parents[0], ApplicationConstant.PARENT));
        this.getConnections().add(new ConnectionModel(parents[1], ApplicationConstant.PARENT));
    }

    public void addConnection(UserModel friend, String connectionType) {
        if (friend.getAge() <= 16) {
            if (((this.getAge() > friend.getAge()) && (this.getAge() - friend.getAge() <= 3)) || ((friend.getAge() > this.getAge()) && (friend.getAge() - this.getAge() <= 3))) {
                if (this.getAge() > 2 && friend.getAge() > 2) {
                    this.getConnections().add(new ConnectionModel(friend.getUserName(), ApplicationConstant.FRIEND));
                    friend.getConnections().add(new ConnectionModel(this.getUserName(), ApplicationConstant.FRIEND));
                } else {
                    System.out.println("Error! The age difference between you and " + friend.getUserName() + " is more than 3 years!");
                }
            } else {
                System.out.println("Dependents under 2 years old cannot have friends");
            }
        } else {
            System.out.println("Cannot add adult as friend");
        }
    }
}
