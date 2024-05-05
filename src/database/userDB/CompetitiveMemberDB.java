package database.userDB;

import database.Database;
import user_domain.User;

import java.util.ArrayList;

public class CompetitiveMemberDB extends Database implements UserReturn {

    public CompetitiveMemberDB() {
        super(UserReturn.getFolderPath()+"CompetitiveMemberDB.csv");
    }

    @Override
    ArrayList<String> getRowNamesFromEnumConfig() {
        return null;
    }

    @Override
    public User getUserFromID(int id) {
        return null;
    }

    @Override
    public User getUserFromLastName(String name) {
        return null;
    }

    @Override
    public ArrayList<User> getListOfUsers(ArrayList<String[]> allRows) {
        return UserReturn.super.getListOfUsers(allRows);
    }

    @Override
    public User searchAndCreateUser(String searchValue, int indexToSearchBy, ArrayList<String[]> rows) {
        return UserReturn.super.searchAndCreateUser(searchValue, indexToSearchBy, rows);
    }

    @Override
    public User createUserFromSingleRow(String[] singleRow) {
        return null;
    }

    @Override
    public boolean editUserInDB(User user) {
        return false;
    }
}
