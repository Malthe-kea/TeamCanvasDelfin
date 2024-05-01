package database;

import database.rowNameEnum.UserDBRowNames;
import user_domain.SuperUser;
import user_domain.User;

import java.util.ArrayList;

public class UserDB extends Database implements UserReturn {
    public UserDB() {
        super(UserReturn.getFolderPath() + "UserDB.csv");

    }

    @Override
    public User getUserFromID(int id) {
        return searchAndCreateUser(UserDBRowNames.USER_ID, id + "");
    }

    @Override
    public User getUserFromLastName(String name) {
        return searchAndCreateUser(UserDBRowNames.LAST_NAME, name);
    }

    @Override
    public ArrayList<User> getListOfUsers() {
        ArrayList<String[]> allRows = getRows();
        ArrayList<User> userList = new ArrayList<>();
        for (String[] singleRow : allRows) {
            userList.add(createUserFromSingleRow(singleRow));
        }
        return userList;
    }

    private User searchAndCreateUser(UserDBRowNames catToFindBy, String searchValue) {
        int indexToSearchBy = getIndexOfRowName(catToFindBy.stringVariant);
        if (indexToSearchBy == -1) {
            return null;
        }
        ArrayList<String[]> rows = getRows();
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

    private User createUserFromSingleRow(String[] singleRow) {
        int userId = Integer.parseInt(singleRow[0]);
        int permissionLevelOfPulledRow = Integer.parseInt(singleRow[1]);
        String firstName = singleRow[3];
        String lastName = singleRow[4];

        switch (permissionLevelOfPulledRow) {
            case 1 -> {
                return new SuperUser(userId, firstName, lastName);
            }
            case 2 -> {
                //Method for retreiving from TrainerDB
            }
            default -> {
                //Method for retreiving from Member and SportMemberDB
                MemberDB memberDB = new MemberDB();
                return memberDB.getUserFromID(userId);
            }
        }
        return null;
    }
}
