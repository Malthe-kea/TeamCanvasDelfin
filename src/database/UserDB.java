package database;

import user_domain.SuperUser;
import user_domain.User;

import java.io.File;
import java.util.ArrayList;

public class UserDB extends Database implements UserReturn {
    public UserDB() {
        super(UserReturn.getFolderPath()+"UserDB.csv");

    }

    @Override
    public User getUserFromID(int id) {
        ArrayList<String[]> rows = getRows();

        for (String[] singleRow : rows) {
            try {
                int idOfPulledRow = Integer.parseInt(singleRow[0]);
                int permissionLevelOfPulledRow = Integer.parseInt(singleRow[1]);
                if(idOfPulledRow == id) {
                    switch (permissionLevelOfPulledRow) {
                        case 1 -> {
                            return new SuperUser(idOfPulledRow, singleRow[3], singleRow[4]);
                        }
                        case 2 -> {
                            //Method for retreiving from MemberDB
                        }
                        case 3 -> {
                            //Method for retreiving from SportMemberDB
                        }
                    }
                }
            } catch (NumberFormatException nfe) {
                System.out.println("A id or permissionlevel was not integer parseable. Please enter the database UserDB.csv and fix this issue.");
            }
        }
        return null;
    }

    @Override
    public User getUserFromFullname(String name) {
        return null;
    }

    @Override
    public ArrayList<User> getListOfUsers() {
        return null;
    }
}
