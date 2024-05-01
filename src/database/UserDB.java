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
                if(idOfPulledRow == id) {
                    int permissionLevelOfPulledRow = Integer.parseInt(singleRow[1]);
                    String firstName = singleRow[3];
                    String lastName = singleRow[4];

                    switch (permissionLevelOfPulledRow) {
                        case 1 -> {
                            return new SuperUser(idOfPulledRow, firstName, lastName);
                        }
                        case 2 -> {
                            //Method for retre
                            // iving from TrainerDB
                        }
                        case 3 -> {
                            //Method for retreiving from Member and SportMemberDB
                            MemberDB memberDB = new MemberDB();
                            return memberDB.getUserFromID(idOfPulledRow);
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
