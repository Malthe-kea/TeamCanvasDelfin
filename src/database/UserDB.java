package database;

import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.MemberDBRowNames;
import database.rowNameEnum.UserDBRowNames;
import user_domain.SuperUser;
import user_domain.Treasurer;
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


    public ArrayList<User> getListOfUsers() {
        ArrayList<String[]> allRows = getRows();
        return getListOfUsers(allRows);
    }



    public User searchAndCreateUser(DBRowNames catToFindBy, String searchValue) {
        int indexToSearchBy = getIndexOfRowName(catToFindBy.getStringVariant());
        return searchAndCreateUser(catToFindBy, searchValue, indexToSearchBy, getRows());
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
    public ArrayList<String> createRowNamesInDB() {
        ArrayList<String> rowNamesToCreate = new ArrayList<>();
        for(UserDBRowNames userDBRowNames: UserDBRowNames.values()) {
            rowNamesToCreate.add(userDBRowNames.getStringVariant());
        }
        return rowNamesToCreate;
    }
}
