package database.userDB;

import user_domain.User;

import java.io.File;
import java.util.ArrayList;

public interface UserReturn {

    //implementation for getting user instance based on search criteria of id.
    User getUserFromID(int id);

    //implementation for getting user instance based on search criteria of Last name.
    User getUserFromLastName(String name);



    //Default method for getting list of all users in the database, setup as User Instances.
    default ArrayList<User> getListOfUsers(ArrayList<String[]> allRows) {
        //Creates arraylist to be returned
        ArrayList<User> userList = new ArrayList<>();
        //Loops through each row og database.
        for (String[] singleRow : allRows) {
            //Calls the method for creating a user instance based on a single row from the DB.
            userList.add(createUserFromSingleRow(singleRow));
        }
        return userList;
    }


    //Method for looping through each row and finding the row where the value matches the searched value.
    //It takes indexToSearchBy as input, which dictactes which column in the database is being searched through.
    default User searchAndCreateUser(String searchValue, int indexToSearchBy, ArrayList<String[]> rows) {
        //If indexToSearchBy equals -1, it means the column name was never found. Thus no need to iterate further.
        if (indexToSearchBy == -1) {
            return null;
        }

        //loops through each row
        for (String[] singleRow : rows) {
            try {
                //Checks if the column in each row (column dictated by the index inserted) equals the search value.
                if (singleRow[indexToSearchBy].equalsIgnoreCase(searchValue)) {
                    //Returns the User instance from the implemented method below of the row, as a valid user
                    //was found from the search criteria. Below method ensures correct user instance is created.
                    return createUserFromSingleRow(singleRow);
                }
            } catch (NumberFormatException nfe) {
                System.out.println("A id was not integer parseable. Please enter the database UserDB.csv and fix this issue.");
            }
        }
        return null;

    }

    //Method for creating a user instance from a single row retrieved from DB. Needs to be implemented in each DB class,
    //as each class handles each instance of user.
    User createUserFromSingleRow(String[] singleRow);

    //Method for editing user in DB. Needs to be implemented in each DB class.
    boolean editUserInDB(User user);

    //Method for returning the folder in which all userdatabases is.
    static String getFolderPath() {
        return "UserDB" + File.separator;
    }
}
