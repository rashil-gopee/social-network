package Controller;

import Constant.ApplicationConstant;
import Model.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

public class UserController {

    public static Set<UserModel> USERS = new HashSet<>();

    public void init(){
            File peopleFile = new File("people.txt");
        File dbFile = new File("mininet.db");

        if (peopleFile.exists() && !dbFile.exists()) {
            loadUsersFromFile();
            loadRelations();
            createDb();
            saveUsersToDb();
        }
    }

    public void loadUsersFromFile() {
        Scanner input = null;
        try {
            File file = new File("people.txt");
            input = new Scanner(file);

            while (input.hasNext()) {
                String line = input.nextLine();
                StringTokenizer st = new StringTokenizer(line, ",");
                String name = st.nextToken().trim();
                System.out.println("name: " + name);
                String photo = st.nextToken().trim();
                System.out.println("photo: " + photo);
                String status = st.nextToken().trim();
                System.out.println("status: " + status);
                char gender = st.nextToken().trim().charAt(0);
                System.out.println("gender: " + gender);
                int age = Integer.parseInt(st.nextToken().trim());
                System.out.println("age: " + age);
                String state = st.nextToken().trim();
                System.out.println("state: " + state);

                UserModel userModel;
                if (age >= 16) {
                    userModel = new AdultUserModel(name, age, status, photo, gender, state);
                    USERS.add(userModel);
                } else {
                    Set<String> parents = new HashSet<>();
                    parents = getParents(name);
                    if (parents.size() == 2 && isCouple(parents)) {
                        if (age > 2) {
                            userModel = new ChildUserModel(name, age, status, photo, gender, state, parents);
                        } else {
                            userModel = new YoungChildUserModel(name, age, status, photo, gender, state, parents);
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
    }

    public Set<String> getParents(String childName) {
        Scanner input = null;
        Set<String> parents = new HashSet<>();
        try {
            File file = new File("relations.txt");
            input = new Scanner(file);

            while (input.hasNext()) {
                String line = input.nextLine();
                StringTokenizer st = new StringTokenizer(line, ",");
                String name1 = st.nextToken().trim();
                String name2 = st.nextToken().trim();
                String type = st.nextToken().trim();

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

    public Boolean isCouple(Set<String> names) {
        ArrayList<String> usernames = new ArrayList<>(names);
        java.util.Collections.sort(usernames);

        Scanner input = null;
        try {
            File file = new File("relations.txt");
            input = new Scanner(file);

            while (input.hasNext()) {
                String line = input.nextLine();
                StringTokenizer st = new StringTokenizer(line, ",");
                String name1 = st.nextToken().trim();
                if (!name1.equals(usernames.get(0)))
                    continue;
                String name2 = st.nextToken().trim();
                String type = st.nextToken().trim();

                return (name2.equals(usernames.get(1)) && type.equals("couple"));
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

    public void loadRelations() {
        Scanner input = null;
        try {
            File file = new File("relations.txt");

            for (UserModel userModel : USERS) {
                input = new Scanner(file);

                while (input.hasNext()) {
                    String line = input.nextLine();
                    StringTokenizer st = new StringTokenizer(line, ",");
                    String name1 = st.nextToken().trim();
                    String name2 = st.nextToken().trim();
                    if (!(name1.equals(userModel.getUserName()) || name2.equals(userModel.getUserName())))
                        continue;
                    String type = st.nextToken().trim();

                    String relativeName;
                    if (name1.equals(userModel.getUserName()))
                        relativeName = name2;
                    else
                        relativeName = name1;

                    if (type.equals(ApplicationConstant.FRIEND) || type.equals(ApplicationConstant.CLASSMATE) || type.equals(ApplicationConstant.COLLEAGUE) || type.equals(ApplicationConstant.SPOUSE))
                        userModel.getConnections().add(new ConnectionModel(relativeName, type));
                    else if (type.equals(ApplicationConstant.SIBLING)) {
                        Set<String> parents = getParents(relativeName);
                        if (parents.size() == 2 && isCouple(parents))
                            userModel.getConnections().add(new ConnectionModel(relativeName, ApplicationConstant.SIBLING));
                    } else if (type.equals(ApplicationConstant.PARENT)) {
                        if (userModel instanceof AdultUserModel)
                            userModel.getConnections().add(new ConnectionModel(relativeName, ApplicationConstant.CHILD));
//                        else
//                            userModel.getConnections().add(new ConnectionModel(relativeName, ApplicationConstant.PARENT));
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
    }

    public void createDb() {
            Connection connection = null;
            Statement statement = null;

            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:mininet.db");

                statement = connection.createStatement();
                String createPeopleTblSql = "CREATE TABLE people " +
                        "(name TEXT PRIMARY KEY NOT NULL, " +
                        "photo TEXT, " +
                        "status TEXT, " +
                        "gender TEXT NOT NULL , " +
                        "age INT NOT NULL , " +
                        "state TEXT NOT NULL);";

//                String createRelationsTblSql = "CREATE TABLE people " +
//                        "(name PRIMARY KEY VARCHAR(50) NOT NULL, " +
//                        "age INT NOT NULL, " +
//                        "photo VARCHAR(50), " +
//                        "status VARCHAR(50), " +
//                        "gender CHAR(1) NOT NULL , " +
//                        "age INTEGER NOT NULL , " +
//                        "state VARCHAR(3) NOT NULL;";

                statement.executeUpdate(createPeopleTblSql);
                //statement.executeUpdate(createRelationsTblSql);

                statement.close();
                connection.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("Opened database successfully");

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
        Connection connection = null;
        Statement statement = null;

        for (UserModel userModel : USERS) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:mininet.db");

                statement = connection.createStatement();
                String sql = "INSERT INTO people" +
                        "VALUES  ('" + userModel.getUserName() + "','" +
                        userModel.getPhoto() + "','" +
                        userModel.getStatus() + "','" +
                        userModel.getGender() + "'," +
                        userModel.getAge() + ",'" +
                        userModel.getState() + "') ";

                statement.executeUpdate(sql);
                statement.close();
                connection.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
        System.out.println("Records created successfully");
    }

    public void updateUserProfileToDb(String originalUsername, UserModel userModel) {
        if (!originalUsername.equals(userModel.getUserName())){
            for (ConnectionModel connectionModel: userModel.getConnections()){
                for (UserModel relative: USERS){
                    if (relative.getUserName().equals(connectionModel.getConnectionName())){
                        for (ConnectionModel relativeConnection: relative.getConnections()){
                            if (relativeConnection.getConnectionName().equals(originalUsername)){
                                relativeConnection.setConnectionName(userModel.getUserName());
                            }
                        }
                    }
                }
            }
        }



    }

    public ArrayList<String> getShorterstRelationPathway(UserModel userModel, String targetName){
        ArrayList<String> relationPathway = new ArrayList<>();

        ArrayList<String> traversedPeople = new ArrayList<>();

        for (ConnectionModel connectionModel: userModel.getConnections()){

        }

        return relationPathway;


    }

}