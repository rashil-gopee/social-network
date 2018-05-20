package Controller;

import Constant.ApplicationConstant;
import Model.*;

import java.io.File;
import java.util.*;

public class UserController {

    public static Set<UserModel> USERS = new HashSet<>();

    public Set<UserModel> loadUsersFromFile() {
        Scanner input = null;
        try {
            File file = new File("File/people.csv");
            input = new Scanner(file);

            while (input.hasNext()) {
                String line = input.nextLine();
                StringTokenizer st = new StringTokenizer(line, ",");
                String name = st.nextToken();
                String photo = st.nextToken();
                String status = st.nextToken();
                char gender = st.nextToken().charAt(0);
                int age = Integer.parseInt(st.nextToken());
                String state = st.nextToken();

                UserModel userModel;
                if (age >= 16) {
                    userModel = new AdultUserModel(name, age, status, photo, gender, state);
                    USERS.add(userModel);
                } else {
                    Set<String> parents = new HashSet<>();
                    parents = getParents(name);
                    if (parents.size() == 2 && isCouple(parents)){
                        if (age > 2){
                            userModel = new ChildUserModel(name, age, status, photo, gender, state,parents);
                        }
                        else{
                            userModel = new YoungChildUserModel(name, age, status, photo, gender, state,parents);
                        }
                        USERS.add(userModel);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in the fileScanner !!!");
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                System.out.println("Error while closing the fileScanner !!!");
                e.printStackTrace();
            }
        }
        return USERS;
    }

    public Set<String> getParents(String childName) {
        Scanner input = null;
        Set<String> parents = new HashSet<>();
        try {
            File file = new File("File/relations.csv");
            input = new Scanner(file);

            while (input.hasNext()) {
                String line = input.nextLine();
                StringTokenizer st = new StringTokenizer(line, ",");
                String name1 = st.nextToken();
                String name2 = st.nextToken();
                String type = st.nextToken();

                if (type.equals(ApplicationConstant.PARENT)) {
                    if (name1.equals(childName))
                        parents.add(name2);
                    else if (name2.equals(childName))
                        parents.add(name1);
                    if (parents.size() == 2)
                        return parents;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in the fileScanner !!!");
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                System.out.println("Error while closing the fileScanner !!!");
                e.printStackTrace();
            }
        }
        return parents;
    }

    public Boolean isCouple(Set<String> names){
        ArrayList<String> usernames = new ArrayList<>(names);
        java.util.Collections.sort(usernames);

        Scanner input = null;
        try {
            File file = new File("File/relations.csv");
            input = new Scanner(file);

            while (input.hasNext()) {
                String line = input.nextLine();
                StringTokenizer st = new StringTokenizer(line, ",");
                String name1 = st.nextToken();
                if (!name1.equals(usernames.get(0)))
                    continue;
                String name2 = st.nextToken();
                String type = st.nextToken();

                return  (name2.equals(usernames.get(1)) && type.equals("couple"));
            }
        } catch (Exception e) {
            System.out.println("Error in the fileScanner !!!");
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                System.out.println("Error while closing the fileScanner !!!");
                e.printStackTrace();
            }
        }
        return false;
    }

    public void loadRelations(){

    }

//    public Set<String> getParents(String childName){
//        Set<UserModel> users;
//        Set<String> parents = new HashSet<>();
//        for (UserModel user: users) {
//            for (ConnectionModel connectionModel : user.getConnections()){
//                if (connectionModel.getConnectionName().equals(childName) && connectionModel.getConnectionType().equals(ApplicationConstant.PARENT)){
//                    parents.add(user.getUserName());
//                    parents.add(connectionModel.getConnectionName());
//                    return parents;
//                }
//            }
//        }
//        return parents;
//    }

    public void saveUsersToDb() {

    }

    public void updateUserProfile() {

    }

}
