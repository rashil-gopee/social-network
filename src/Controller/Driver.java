package Controller;

import Constant.ApplicationConstant;
import Model.*;
import Exception.NoParentException;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Driver {
    public static Set<UserModel> USERS = new HashSet<>();

    public Boolean init() {
        File peopleFile = new File("people.txt");
        File dbFile = new File("mininet.db");

        if (peopleFile.exists() && !dbFile.exists()) {
            loadUsersFromFile();
            loadRelations();
            createDb();
            saveUsersToDb();
            return true;
        } else if (dbFile.exists()) {
            loadUsersFromDb();
            loadRelations();
            printUsers();
            return true;
        }
        return false;
    }

    private void loadUsersFromDb() {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mininet.db");

            statement = connection.createStatement();
            String sql = "SELECT * FROM people;";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String photo = resultSet.getString("photo");
                String status = resultSet.getString("status");
                char gender = resultSet.getString("gender").charAt(0);
                int age = resultSet.getInt("age");
                String state = resultSet.getString("state");

                UserModel userModel;
                if (age >= 16) {
                    userModel = new AdultUserModel(name, age, status, photo, gender, state);
                    USERS.add(userModel);
                } else {
                    Set<String> parents = new HashSet<>();
                    parents = getParentsFromFile(name);
                    if (parents.size() == 2 && verifyCoupleFromFile(parents)) {
                        if (age > 2) {
                            userModel = new ChildUserModel(name, age, status, photo, gender, state, parents);
                            USERS.add(userModel);
                        } else if (age >= 0) {
                            userModel = new YoungChildUserModel(name, age, status, photo, gender, state, parents);
                            USERS.add(userModel);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private void loadUsersFromFile() {
        Scanner input = null;
        try {
            File file = new File("people.txt");
            input = new Scanner(file);

            while (input.hasNext()) {
                String line = input.nextLine();
                StringTokenizer st = new StringTokenizer(line, ",");
                String name = st.nextToken().trim();
                String photo = st.nextToken().trim().replaceAll("^\"|\"$", "");
                String status = st.nextToken().trim().replaceAll("^\"|\"$", "");
                char gender = st.nextToken().trim().charAt(0);
                int age = Integer.parseInt(st.nextToken().trim());
                String state = st.nextToken().trim();

                UserModel userModel = null;
                if (age >= 16) {
                    userModel = new AdultUserModel(name, age, status, photo, gender, state);
                    USERS.add(userModel);
                } else {
                    Set<String> parents = new HashSet<>();
                    parents = getParentsFromFile(name);
                    if (parents.size() == 2 && verifyCoupleFromFile(parents)) {
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

    private Set<String> getParentsFromFile(String childName) {
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

    private Boolean verifyCoupleFromFile(Set<String> names) {
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

    private void loadRelations() {
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
                        Set<String> parents = getParentsFromFile(relativeName);
                        if (parents.size() == 2 && verifyCoupleFromFile(parents))
                            userModel.getConnections().add(new ConnectionModel(relativeName, ApplicationConstant.SIBLING));
                    } else if (type.equals(ApplicationConstant.PARENT)) {
                        if (userModel instanceof AdultUserModel)
                            userModel.getConnections().add(new ConnectionModel(relativeName, ApplicationConstant.CHILD));
                    }
                    else if (type.equals("couple")){
                        userModel.getConnections().add(new ConnectionModel(relativeName, ApplicationConstant.SPOUSE));
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

    private void createDb() {
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

            statement.executeUpdate(createPeopleTblSql);

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
        for (UserModel userModel : USERS) {
            saveUser(userModel);
        }
        System.out.println("Records created successfully");
    }

    public void updateUserProfileToDb(String originalUsername, UserModel userModel) {
        if (!originalUsername.equals(userModel.getUserName())) {
            for (ConnectionModel connectionModel : userModel.getConnections()) {
                for (UserModel relative : USERS) {
                    if (relative.getUserName().equals(connectionModel.getConnectionName())) {
                        for (ConnectionModel relativeConnection : relative.getConnections()) {
                            if (relativeConnection.getConnectionName().equals(originalUsername)) {
                                relativeConnection.setConnectionName(userModel.getUserName());
                            }
                        }
                    }
                }
            }
        }

        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mininet.db");

            statement = connection.createStatement();
            String sql = "UPDATE people SET name = '" + userModel.getUserName() + "'," +
                    "photo = '" + userModel.getPhoto() + "'," +
                    "status = '" + userModel.getPhoto() + "'," +
                    "gender = '" + userModel.getGender() + "'," +
                    "age = '" + userModel.getAge() + "'," +
                    "state = '" + userModel.getState() + "'" +
                    "WHERE name='" + originalUsername + "';";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void addUser(UserModel userModel) {
        saveUser(userModel);
        USERS.add(userModel);
    }

    public void saveUser(UserModel userModel) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mininet.db");

            statement = connection.createStatement();
            String sql = "INSERT INTO people " +
                    "VALUES ('" + userModel.getUserName() + "','" +
                    userModel.getPhoto() + "','" +
                    userModel.getStatus() + "','" +
                    userModel.getGender() + "'," +
                    userModel.getAge() + ",'" +
                    userModel.getState() + "');";

            System.out.println(sql);

            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Record created successfully");
    }

    public ArrayList<String> getShorterstRelationPathway(UserModel userModel, String targetName) {
        ArrayList<String> relationPathway = new ArrayList<>();

        ArrayList<String> traversedPeople = new ArrayList<>();

        for (ConnectionModel connectionModel : userModel.getConnections()) {

        }

        return relationPathway;
    }

    public Boolean verifyIfCouple(String name1, String name2) {
        for (UserModel userModel : USERS) {
            if (userModel.getUserName().equals(name1) || userModel.getUserName().equals(name2)) {
                String personToVerify;
                if (userModel.getUserName().equals(name1))
                    personToVerify = name1;
                else
                    personToVerify = name2;
                for (ConnectionModel connectionModel : userModel.getConnections()) {
                    if (connectionModel.getConnectionType().equals(personToVerify) && connectionModel.getConnectionType().equals(ApplicationConstant.SPOUSE))
                        return true;
                }
            }
        }
        return false;
    }

    public UserModel getUser(String userName) {
        for (UserModel user : USERS) {
            if (user.getUserName().equals(userName))
                return user;
        }
        return null;
    }

    public void printUsers() {
        for (UserModel user : USERS) {
            System.out.println("Name: " + user.getUserName());
            System.out.println("Photo: " + user.getPhoto());
            System.out.println("Status: " + user.getStatus());
            System.out.println("Gender: " + user.getGender());
            System.out.println("Age: " + user.getAge());
            System.out.println("State: " + user.getState());

            System.out.println("Connections:");
            for (ConnectionModel connection : user.getConnections()) {
                System.out.println("\t" + connection.getConnectionName() + "\t" + connection.getConnectionType());
            }
        }
    }

    public Boolean verifyIfIsParent(UserModel userModel) {
        for (ConnectionModel connectionModel : userModel.getConnections()) {
            if (connectionModel.getConnectionType().equals(ApplicationConstant.CHILD))
                return true;
        }
        return false;
    }

    public void deleteUserFromDb(String userName) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mininet.db");

            statement = connection.createStatement();
            String sql = "DELETE FROM people WHERE name='" + userName + "';";

            System.out.println(sql);

            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Record deleted successfully");
    }

    public void deleteUser(UserModel userModel) throws Exception {
        if (!verifyIfIsParent(userModel)) {
            for (ConnectionModel connectionModel : userModel.getConnections()) {
                UserModel relative = getUser(connectionModel.getConnectionName());
                for (ConnectionModel connection : relative.getConnections()) {
                    if (connection.getConnectionName().equals(userModel.getUserName()))
                        relative.getConnections().remove(connection);
                }
            }
            deleteUserFromDb(userModel.getUserName());
            USERS.remove(userModel);
        } else
            throw new NoParentException("Cannot delete a parent.");
    }

//    public ConnectionModel getConnectionBetween (String person1, String person2){
//        ConnectionModel connectionModel = new ConnectionModel();
//    }
    

}
