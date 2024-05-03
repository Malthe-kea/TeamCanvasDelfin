package database;

import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.UserDBRowNames;
import domain_model.DelfinUtil;
import user_domain.SuperUser;
import user_domain.Treasurer;
import user_domain.User;

import java.util.ArrayList;
import java.util.Arrays;

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


    public ArrayList<User> getListOfUsers() {
        ArrayList<String[]> allRows = getRows();
        return getListOfUsers(allRows);
    }



    public User searchAndCreateUser(DBRowNames catToFindBy, String searchValue) {
        int indexToSearchBy = getIndexOfRowName(catToFindBy);
        return searchAndCreateUser(searchValue, indexToSearchBy, getRows());
    }

    @Override
    public User createUserFromSingleRow(String[] singleRow) {
        int userId = Integer.parseInt(singleRow[0]);
        int permissionLevelOfPulledRow = Integer.parseInt(singleRow[1]);
        String firstName = singleRow[3];
        String lastName = singleRow[4];

        switch (permissionLevelOfPulledRow) {
            case 1 -> {
                return new SuperUser(userId, firstName, lastName);
            }
            case 2 -> {
                return new Treasurer(userId, firstName, lastName);
            }
            case 3 -> {
                TrainerDB trainerDB = new TrainerDB();
                return trainerDB.getUserFromID(userId);
            }
            default -> {
                MemberDB memberDB = new MemberDB();
                return memberDB.getUserFromID(userId);
            }
        }
    }

    @Override
    public ArrayList<String> getRowNamesFromEnumConfig() {
        ArrayList<String> rowNamesToCreate = new ArrayList<>();
        for(UserDBRowNames userDBRowNames: UserDBRowNames.values()) {
            rowNamesToCreate.add(userDBRowNames.getStringVariant());
        }
        return rowNamesToCreate;
    }

    //Returns true if edit was successfull
    public boolean editUserInDB(User user) {
        ArrayList<User> allUsers = getListOfUsers();
        String[] newRow = new String[5];
        newRow[0] = String.valueOf(user.getUserID());
        newRow[3] = user.getFirstName();
        newRow[4] = user.getLastName();



        switch(DelfinUtil.checkUserInstance(user)) {
            case SUPER -> {
                newRow[1] = "1";
            }
            case TREASURER -> {
                newRow[1] = "2";
            }
            case TRAINER -> {
                newRow[1] = "3";
            }
            default -> {
                newRow[1] = "4";
            }
        }

        ArrayList<String[]> allRows = getRows();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getUserID() == user.getUserID()) {
                newRow[2] = getRows().get(i)[2];
                allRows.set(i,newRow);
                break;
            }
        }

        for(String[] row : allRows) {
            System.out.println(Arrays.toString(row));
        }

        if(!insertListToDB(allRows)) {
            return false;
        }

        switch(DelfinUtil.checkUserInstance(user)) {
            case MEMBER_OR_COMPETITIVE -> {
                MemberDB memberDB = new MemberDB();
                if(memberDB.editUserInDB(user)) {
                    return true;
                }

            }
            case TRAINER -> {
                TrainerDB trainerDB = new TrainerDB();
                if(trainerDB.editUserInDB(user)) {
                    return true;
                }
            }
            case SUPER, TREASURER -> {
                return true;
            }
        }
        return false;
    }
}
