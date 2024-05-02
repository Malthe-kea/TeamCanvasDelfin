package database;

import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.MemberDBRowNames;
import user_domain.CompetitiveMember;
import user_domain.Member;
import user_domain.User;

import java.util.ArrayList;

public class MemberDB extends Database implements UserReturn {
    public MemberDB() {
        super(UserReturn.getFolderPath() + "MemberDB.csv");
    }


    @Override
    public User getUserFromID(int id) {
        return searchAndCreateUser(MemberDBRowNames.USER_ID, id + "");
    }

    @Override
    public User getUserFromLastName(String name) {
        return searchAndCreateUser(MemberDBRowNames.LAST_NAME, name);
    }

    public ArrayList<User> getListOfUsers() {
        ArrayList<String[]> allRows = getRows();
        return getListOfUsers(allRows);
    }

    public User searchAndCreateUser(DBRowNames catToFindBy, String searchValue) {
        int indexToSearchBy = getIndexOfRowName(catToFindBy);
        return searchAndCreateUser(searchValue, indexToSearchBy, getRows());
    }

    public User createUserFromSingleRow(String[] singleRow) {
        int userid = Integer.parseInt(singleRow[0]);
        String firstName = singleRow[1];
        String lastName = singleRow[2];
        boolean isCompetitive = Boolean.parseBoolean(singleRow[4]);
        boolean isActiveMember = Boolean.parseBoolean(singleRow[3]);
        int age = Integer.parseInt(singleRow[5]);
        boolean isArrears = Boolean.parseBoolean(singleRow[6]);
        if (isCompetitive) {
            return new CompetitiveMember(userid, firstName, lastName, isActiveMember, true, age, isArrears);
        } else {
            return new Member(userid, firstName, lastName, isActiveMember, false, age, isArrears);
        }
    }

    @Override
    public ArrayList<String> getRowNamesFromEnumConfig() {
        ArrayList<String> rowNamesToCreate = new ArrayList<>();
        for (MemberDBRowNames memberDBRowNames : MemberDBRowNames.values()) {
            rowNamesToCreate.add(memberDBRowNames.getStringVariant());
        }
        return rowNamesToCreate;
    }

    @Override
    public boolean editUserInDB(User user) {
        if (user instanceof Member member) {
            ArrayList<User> allUsers = getListOfUsers();
            String[] newRow = new String[8];
            newRow[0] = String.valueOf(member.getUserID());
            newRow[1] = member.getFirstName();
            newRow[2] = member.getLastName();
            newRow[3] = String.valueOf(member.isActiveMember());
            newRow[4] = String.valueOf(member.isCompetitive());
            newRow[5] = String.valueOf(member.getAge());
            newRow[6] = String.valueOf(member.isArrears());
            newRow[7] = String.valueOf(member.getYearlyMembershipFee());

            ArrayList<String[]> allRows = getRows();
            for (int i = 0; i < allUsers.size(); i++) {
                if (allUsers.get(i).getUserID() == member.getUserID()) {
                    allRows.set(i, newRow);
                    break;
                }
            }

            return insertListToDB(allRows);
        }
        return false;
    }
}
