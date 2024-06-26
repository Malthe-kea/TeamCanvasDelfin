package database.userDB;

import database.Database;
import database.competition_style_DB.CompetitionDB;
import database.competition_style_DB.StyleDB;
import database.rowNameEnum.*;
import user_domain.CompetitiveMember;
import user_domain.Member;
import user_domain.User;
import user_domain.competition.Competition;
import user_domain.competition.Style;

import java.util.ArrayList;
import java.util.List;

public class CompetitiveMemberDB extends Database implements UserReturn {
    private int userIDFromMemberDB;
    private String firstNameFromMemberDB;
    private String lastNameFromMemberDB;
    private boolean isActiveMemberFromMemberDB;
    private final boolean isCompetitiveFromMemberDB;
    private String dateOfBirthFromMemberDB;
    private boolean isArrearsFromMemberDB;


    //userid, firstName, lastName, isActiveMember, true, age, isArrears

    public CompetitiveMemberDB() {
        super(UserReturn.getFolderPath() + "CompetitiveMemberDB.csv");
        //As this DB only gets called when the member is a Competitive member, this boolean is always true. Therefor final
        isCompetitiveFromMemberDB = true;
        userIDFromMemberDB = -1;
        firstNameFromMemberDB = null;
        lastNameFromMemberDB = null;
        isActiveMemberFromMemberDB = false;
        dateOfBirthFromMemberDB = "";
        isArrearsFromMemberDB = false;
    }

    public void setMemberAttributes(int userIDFromMemberDB, String firstNameFromMemberDB, String lastNameFromMemberDB,
                                    boolean isActiveMemberFromMemberDB, String dateOfBirthFromMemberDB, boolean isArrearsFromMemberDB) {
        this.userIDFromMemberDB = userIDFromMemberDB;
        this.firstNameFromMemberDB = firstNameFromMemberDB;
        this.lastNameFromMemberDB = lastNameFromMemberDB;
        this.isActiveMemberFromMemberDB = isActiveMemberFromMemberDB;
        this.dateOfBirthFromMemberDB = dateOfBirthFromMemberDB;
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
        int userID = Integer.parseInt(singleRow[getIndexOfRowName(CompetitiveMemberDBRowNames.USER_ID)]);
        String firstName = "";
        String lastName = "";
        boolean isActiveMember = false;
        String dateOfBirth = "";
        boolean isArrears = false;
        MemberDB memberDB = new MemberDB();
        ArrayList<String[]> rows = memberDB.getRows();

        int count = 0;
        for (String[] memberDBSingleRow : rows) {
            int memberDBUserID = Integer.parseInt(memberDBSingleRow[memberDB.getIndexOfRowName(MemberDBRowNames.USER_ID)]);
            if (userID == memberDBUserID) {
                firstName = memberDBSingleRow[memberDB.getIndexOfRowName(MemberDBRowNames.FIRST_NAME)];
                lastName = memberDBSingleRow[memberDB.getIndexOfRowName(MemberDBRowNames.LAST_NAME)];
                isActiveMember = Boolean.parseBoolean(memberDBSingleRow[memberDB.getIndexOfRowName(MemberDBRowNames.IS_ACTIVE_MEMBER)]);
                dateOfBirth = memberDBSingleRow[memberDB.getIndexOfRowName(MemberDBRowNames.DATE_OF_BIRTH)];
                isArrears = Boolean.parseBoolean(memberDBSingleRow[memberDB.getIndexOfRowName(MemberDBRowNames.IS_ARREARS)]);
                break;
            }
            count++;
        }
        if(count == rows.size()){
            return null;
        }

        String compEntriesAsString = singleRow[getIndexOfRowName(CompetitiveMemberDBRowNames.COMPETITION_LIST)];
        compEntriesAsString = compEntriesAsString.substring(1, compEntriesAsString.length() - 1);
        compEntriesAsString = compEntriesAsString.replace(" ", "");
        String[] compEntries = compEntriesAsString.split(",");
        ArrayList<Competition> compList = new ArrayList<>();


        if (!(compEntries.length == 1 && compEntries[0].isBlank())) {
            CompetitionDB compDB = new CompetitionDB();
            for (String idAsString : compEntries) {
                compList.add(compDB.getCompetitionFromID(Integer.parseInt(idAsString), userID));
            }
        }


        return new CompetitiveMember(userID, firstName, lastName, isActiveMember,
                true, dateOfBirth, isArrears, compList);


    }

    @Override
    public boolean editUserInDB(User user) {
        //Checks of the user instance is indeed an instance of Member.
        if (user instanceof CompetitiveMember compMember) {
            userIDFromMemberDB = compMember.getUserID();
            firstNameFromMemberDB = compMember.getFirstName();
            lastNameFromMemberDB = compMember.getLastName();
            isActiveMemberFromMemberDB = compMember.isActiveMember();
            dateOfBirthFromMemberDB = compMember.getDateOfBirth();
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
            if (!insertListToDB(allRows)) {
                return false;
            }

            if (!compList.isEmpty()) {
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
        if (!(user instanceof CompetitiveMember compMember)) {
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

            if (!insertListToDB(allRows)) {
                return false;
            }

            if (!compList.isEmpty()) {
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
        if (user instanceof CompetitiveMember compMember) {
            ArrayList<String[]> allRows = getRows();

            for (String[] singleRow : allRows) {
                int userIDFromDB = Integer.parseInt(singleRow[getIndexOfRowName(CompetitiveMemberDBRowNames.USER_ID)]);
                if (userIDFromDB == compMember.getUserID()) {
                    allRows.remove(singleRow);
                    break;
                }
            }

            ArrayList<Competition> compList = compMember.getCompetitionList();

            if (!insertListToDB(allRows)) {
                return false;
            }
            StyleDB styleDB = new StyleDB();
            for (Competition comp : compList) {
                for (Style style : comp.getStyleList()) {
                    if (!styleDB.removeStyleFromDB(style)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }


    public void addCompetitionToCompetitiveMember(int indexOfMember, int idOfCompetition) {
        ArrayList<String[]> compMemberList = getRows();
        String[] row = compMemberList.get(indexOfMember);
        int indexOfCompetitionList = getIndexOfRowName(CompetitiveMemberDBRowNames.COMPETITION_LIST);
        String compEntriesAsString = row[indexOfCompetitionList];
        compEntriesAsString = compEntriesAsString.substring(1, compEntriesAsString.length() - 1);
        compEntriesAsString = compEntriesAsString.replace(" ", "");
        String[] compEntries = compEntriesAsString.split(",");
        ArrayList<String> compEntriesList = new ArrayList<>(List.of(compEntries));
        compEntriesList.add(String.valueOf(idOfCompetition));


        if (compEntriesList.get(0).isBlank()) {
            compEntriesList.remove(0);
        }
        row[indexOfCompetitionList] = compEntriesList.toString();
        System.out.println(row[indexOfCompetitionList]);

        compMemberList.set(indexOfMember, row);
        insertListToDB(compMemberList);

    }
}
