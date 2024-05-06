package database.userDB;

import database.Database;
import database.competition_style_DB.CompetitionDB;
import database.competition_style_DB.StyleDB;
import database.rowNameEnum.CompetitionDBRowNames;
import database.rowNameEnum.CompetitiveMemberDBRowNames;
import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.UserDBRowNames;
import user_domain.CompetitiveMember;
import user_domain.User;
import user_domain.competition.Competition;
import user_domain.competition.Style;

import java.util.ArrayList;

public class CompetitiveMemberDB extends Database implements UserReturn {
    private int userIDFromMemberDB;
    private String firstNameFromMemberDB;
    private String lastNameFromMemberDB;
    private boolean isActiveMemberFromMemberDB;
    private final boolean isCompetitiveFromMemberDB;
    private int ageFromMemberDB;
    private boolean isArrearsFromMemberDB;



    //userid, firstName, lastName, isActiveMember, true, age, isArrears

    public CompetitiveMemberDB() {
        super(UserReturn.getFolderPath()+"CompetitiveMemberDB.csv");
        //As this DB only gets called when the member is a Competitive member, this boolean is always true. Therefor final
        isCompetitiveFromMemberDB = true;
        userIDFromMemberDB = -1;
        firstNameFromMemberDB = null;
        lastNameFromMemberDB = null;
        isActiveMemberFromMemberDB = false;
        ageFromMemberDB = -1;
        isArrearsFromMemberDB = false;
    }

    public void setMemberAttributes(int userIDFromMemberDB, String firstNameFromMemberDB, String lastNameFromMemberDB,
                                    boolean isActiveMemberFromMemberDB, int ageFromMemberDB, boolean isArrearsFromMemberDB) {
        this.userIDFromMemberDB = userIDFromMemberDB;
        this.firstNameFromMemberDB = firstNameFromMemberDB;
        this.lastNameFromMemberDB = lastNameFromMemberDB;
        this.isActiveMemberFromMemberDB = isActiveMemberFromMemberDB;
        this.ageFromMemberDB = ageFromMemberDB;
        this.isArrearsFromMemberDB = isArrearsFromMemberDB;
    }

    @Override
    public DBRowNames[] getEnumRowNames() {
        return CompetitiveMemberDBRowNames.values();
    }

    @Override
    public User getUserFromID(int id) {
        return searchAndCreateUser(CompetitiveMemberDBRowNames.USER_ID, String.valueOf(id));
    }

    @Override
    public User getUserFromLastName(String name) {
        return searchAndCreateUser(CompetitiveMemberDBRowNames.LAST_NAME, name);
    }

    public ArrayList<User> getListOfUsers() {
        return UserReturn.super.getListOfUsers(super.getRows());
    }

    public User searchAndCreateUser(CompetitiveMemberDBRowNames catToFindBy, String searchValue) {
        //Gets the index number of the column that was wished to be searched for.
        int indexToSearchBy = super.getIndexOfRowName(catToFindBy);
        //Calls the default method in User Return, inserting the rows and index of column to be searched through.
        return UserReturn.super.searchAndCreateUser(searchValue, indexToSearchBy, super.getRows());
    }

    @Override
    public User createUserFromSingleRow(String[] singleRow) {
        if(userIDFromMemberDB != -1) {
            String compEntriesAsString = singleRow[getIndexOfRowName(CompetitiveMemberDBRowNames.COMPETITION_LIST)];
            compEntriesAsString = compEntriesAsString.substring(1,compEntriesAsString.length()-1);
            String[] compEntries = compEntriesAsString.split(",");
            ArrayList<Competition> compList = new ArrayList<>();

            CompetitionDB compDB = new CompetitionDB();

            for (String idAsString : compEntries) {
                compList.add(compDB.getCompetitionFromID(Integer.parseInt(idAsString), userIDFromMemberDB));
            }

            return new CompetitiveMember(userIDFromMemberDB, firstNameFromMemberDB, lastNameFromMemberDB, isActiveMemberFromMemberDB,
                    isCompetitiveFromMemberDB, ageFromMemberDB, isArrearsFromMemberDB, compList);
        }



        return null;
    }

    @Override
    public boolean editUserInDB(User user) {
        //Checks of the user instance is indeed an instance of Member.
        if (user instanceof CompetitiveMember compMember) {
            userIDFromMemberDB = compMember.getUserID();
            firstNameFromMemberDB = compMember.getFirstName();
            lastNameFromMemberDB = compMember.getLastName();
            isActiveMemberFromMemberDB = compMember.isActiveMember();
            ageFromMemberDB = compMember.getAge();
            isArrearsFromMemberDB = compMember.isArrears();

            //Gets list of all member instances from DB.
            ArrayList<User> allUsers = getListOfUsers();
            //Gets list of all members as formatted DB String from DB.
            ArrayList<String[]> allRows = super.getRows();
            //Creates new String[] array with length corresponding to amount of columns in DB.
            String[] newRow = new String[CompetitiveMemberDBRowNames.values().length];
            //Gets the id, first name, last name as a String;
            newRow[getIndexOfRowName(CompetitiveMemberDBRowNames.USER_ID)] = String.valueOf(compMember.getUserID());
            newRow[getIndexOfRowName(CompetitiveMemberDBRowNames.FIRST_NAME)] = compMember.getFirstName();
            newRow[getIndexOfRowName(CompetitiveMemberDBRowNames.LAST_NAME)] = compMember.getLastName();
            //Gets the activity status, competitive status, age, arrears status and yearly membership fee as Strings.
            ArrayList<Competition> compList = compMember.getCompetitionList();
            ArrayList<Integer> compIDList = new ArrayList<>();
            compList.forEach(comp -> compIDList.add(comp.getID()));



            newRow[getIndexOfRowName(CompetitiveMemberDBRowNames.COMPETITION_LIST)] = compIDList.toString();

            //Loops through list of member instances.
            for (int i = 0; i < allUsers.size(); i++) {
                //If the userid in allusers equals the member id of the wished edited member,
                // it means we now know the index place of the wished member in the database,
                // and we can now replace this index.
                if (allUsers.get(i).getUserID() == compMember.getUserID()) {
                    //We replace the old member with the new edited member with equal id.
                    allRows.set(i, newRow);
                    break;
                }
            }
            //We insert the edited row into the database.
            if(!insertListToDB(allRows)) {
                return false;
            }

            if(!compList.isEmpty()) {
                ArrayList<Boolean> compErrorReturns = new ArrayList<>();
                CompetitionDB compDB = new CompetitionDB();
                for (Competition singleComp : compList) {
                    compErrorReturns.add(compDB.editCompInDB(singleComp));
                }

                return !compErrorReturns.contains(false);
            }
            return true;
        }
        return false;
    }

    public int getIDForNewUser() {
        return getIDForNewEntry(UserDBRowNames.USER_ID, super.getRows());
    }


    public boolean addUserInDB(User user) {
        if(!(user instanceof CompetitiveMember compMember && user.getUserID() == getIDForNewUser())) {
            return false;
        } else {
            ArrayList<String[]> allRows = getRows();

            String[] newRow = new String[CompetitiveMemberDBRowNames.values().length];
            newRow[getIndexOfRowName(CompetitiveMemberDBRowNames.USER_ID)] = String.valueOf(compMember.getUserID());
            newRow[getIndexOfRowName(CompetitiveMemberDBRowNames.FIRST_NAME)] = compMember.getFirstName();
            newRow[getIndexOfRowName(CompetitiveMemberDBRowNames.LAST_NAME)] = compMember.getLastName();

            ArrayList<Competition> compList = compMember.getCompetitionList();
            ArrayList<Integer> compIDList = new ArrayList<>();
            compList.forEach(comp -> compIDList.add(comp.getID()));

            newRow[getIndexOfRowName(CompetitiveMemberDBRowNames.COMPETITION_LIST)] = compIDList.toString();


            allRows.add(newRow);

            if(!insertListToDB(allRows)) {
                return false;
            }

            if(!compList.isEmpty()) {
                CompetitionDB compDB = new CompetitionDB();
                for (Competition singleComp : compList) {
                    compDB.addCompetitionToDB(singleComp);
                }
            }
            return true;
        }
    }

    @Override
    public boolean removeUserFromDB(User user) {
        if(user instanceof CompetitiveMember compMember) {
            ArrayList<String[]> allRows = getRows();

            for(String[] singleRow : allRows) {
                int userIDFromDB = Integer.parseInt(singleRow[getIndexOfRowName(CompetitiveMemberDBRowNames.USER_ID)]);
                if(userIDFromDB == compMember.getUserID()) {
                    allRows.remove(singleRow);
                    break;
                }
            }

            ArrayList<Competition> compList = compMember.getCompetitionList();

            if(!insertListToDB(allRows)) {
                return false;
            }
            StyleDB styleDB = new StyleDB();
            for(Competition comp : compList) {
                for(Style style : comp.getStyleList()) {
                    if(!styleDB.removeStyleFromDB(style)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
