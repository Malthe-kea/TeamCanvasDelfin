package database;

import user_domain.Member;
import user_domain.SuperUser;
import user_domain.User;

import java.util.ArrayList;

public class MemberDB extends Database implements UserReturn {
    public MemberDB() {
        super(UserReturn.getFolderPath()+"MemberDB.csv");
    }


    @Override
    public User getUserFromID(int id) {
        ArrayList<String[]> rows = getRows();
        for (String[] singleRow : rows) {
            try {
                int idOfPulledRow = Integer.parseInt(singleRow[0]);
                if(idOfPulledRow == id) {
                    boolean isCompetitive = Boolean.parseBoolean(singleRow[4]);
                    if(isCompetitive) {
                        //Method for sportMemberDB
                    } else {
                        String firstName = singleRow[1];
                        String lastName = singleRow[2];
                        boolean isActiveMember = Boolean.parseBoolean(singleRow[3]);
                        int age = Integer.parseInt(singleRow[5]);
                        boolean isArrears = Boolean.parseBoolean(singleRow[6]);
                        return new Member(idOfPulledRow,firstName,lastName,isActiveMember,isCompetitive,age,isArrears);
                    }

                }
            } catch (NumberFormatException nfe) {
                System.out.println("A id was not integer parseable. Please enter the database UserDB.csv and fix this issue.");
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
