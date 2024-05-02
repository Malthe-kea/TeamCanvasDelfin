package database;

import user_domain.User;

import java.io.File;
import java.util.ArrayList;

public interface UserReturn {

    User getUserFromID(int id);

    User getUserFromLastName(String name);

    default ArrayList<User> getListOfUsers(ArrayList<String[]> allRows) {
        ArrayList<User> userList = new ArrayList<>();
        for (String[] singleRow : allRows) {
            userList.add(createUserFromSingleRow(singleRow));
        }
        return userList;
    }

    default User searchAndCreateUser(String searchValue, int indexToSearchBy, ArrayList<String[]> rows) {
        if (indexToSearchBy == -1) {
            return null;
        }
        for (String[] singleRow : rows) {
            try {
                if (singleRow[indexToSearchBy].equalsIgnoreCase(searchValue)) {
                    return createUserFromSingleRow(singleRow);
                }
            } catch (NumberFormatException nfe) {
                System.out.println("A id was not integer parseable. Please enter the database UserDB.csv and fix this issue.");
            }
        }
        return null;

    }

    User createUserFromSingleRow(String[] singleRow);

    boolean editUserInDB(User user);

    static String getFolderPath() {
        return "UserDB" + File.separator;
    }
}
