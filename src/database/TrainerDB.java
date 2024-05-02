package database;

import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.MemberDBRowNames;
import database.rowNameEnum.TrainerDBRowNames;
import user_domain.Trainer;
import user_domain.User;

import java.util.ArrayList;

public class TrainerDB extends Database implements UserReturn {

    public TrainerDB() {
        super(UserReturn.getFolderPath()+"TrainerDB.csv");
    }

    @Override
    public User getUserFromID(int id) {
        return searchAndCreateUser(TrainerDBRowNames.USER_ID, id + "");
    }

    @Override
    public User getUserFromLastName(String name) {
        return searchAndCreateUser(TrainerDBRowNames.LAST_NAME, name);
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
        int userid = Integer.parseInt(singleRow[0]);
        String firstName = singleRow[1];
        String lastName = singleRow[2];
        boolean isSeniorTrainer = Boolean.parseBoolean(singleRow[3]);
        return new Trainer(userid,firstName,lastName,isSeniorTrainer);
    }

    @Override
    public ArrayList<String> createRowNamesInDB() {
        ArrayList<String> rowNamesToCreate = new ArrayList<>();
        for(TrainerDBRowNames trainerDBRowNames: TrainerDBRowNames.values()) {
            rowNamesToCreate.add(trainerDBRowNames.getStringVariant());
        }
        return rowNamesToCreate;
    }


}
