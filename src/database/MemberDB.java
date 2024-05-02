package database;

import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.MemberDBRowNames;
import user_domain.CompetitiveMember;
import user_domain.Member;
import user_domain.User;

import java.util.ArrayList;

public class MemberDB extends Database implements UserReturn {
    public MemberDB() {
        super(UserReturn.getFolderPath()+"MemberDB.csv");
    }


    @Override
    public User getUserFromID(int id) {
        return searchAndCreateUser(MemberDBRowNames.USER_ID,id+"");
    }

    @Override
    public User getUserFromLastName(String name) {
        return searchAndCreateUser(MemberDBRowNames.LAST_NAME,name);
    }

    public ArrayList<User> getListOfUsers() {
        ArrayList<String[]> allRows = getRows();
        return getListOfUsers(allRows);
    }

    public User searchAndCreateUser(DBRowNames catToFindBy, String searchValue) {
        int indexToSearchBy = getIndexOfRowName(catToFindBy.getStringVariant());
        return searchAndCreateUser(catToFindBy, searchValue, indexToSearchBy, getRows());
    }

    public User createUserFromSingleRow(String[] singleRow) {
        int userid = Integer.parseInt(singleRow[0]);
        String firstName = singleRow[1];
        String lastName = singleRow[2];
        boolean isCompetitive = Boolean.parseBoolean(singleRow[4]);
        boolean isActiveMember = Boolean.parseBoolean(singleRow[3]);
        int age = Integer.parseInt(singleRow[5]);
        boolean isArrears = Boolean.parseBoolean(singleRow[6]);
        if(isCompetitive) {
            return new CompetitiveMember(userid,firstName,lastName,isActiveMember,true,age,isArrears);
        } else {
            return new Member(userid,firstName,lastName,isActiveMember,false,age,isArrears);
        }
    }

    @Override
    public ArrayList<String> createRowNamesInDB() {
        ArrayList<String> rowNamesToCreate = new ArrayList<>();
        for(MemberDBRowNames memberDBRowNames: MemberDBRowNames.values()) {
            rowNamesToCreate.add(memberDBRowNames.getStringVariant());
        }
        return rowNamesToCreate;
    }
}
