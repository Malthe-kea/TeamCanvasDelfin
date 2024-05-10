package database.userDB;

import database.Database;
import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.MemberDBRowNames;
import database.rowNameEnum.UserDBRowNames;
import domain_model.DelfinUtil;
import user_domain.CompetitiveMember;
import user_domain.Member;
import user_domain.User;

import java.util.ArrayList;

public class MemberDB extends Database implements UserReturn {
    public MemberDB() {
        //Sets the filepath of the specific csv file to be used when calling this DB class.
        super(UserReturn.getFolderPath() + "MemberDB.csv");
    }

    //Method for retreiving a Member instance based on data from database, where the search criteria is that the
    //Member retrieved needs to match the inserted id of the member.
    @Override
    public User getUserFromID(int id) {
        return searchAndCreateUser(MemberDBRowNames.USER_ID, String.valueOf(id));
    }

    //Method for retreiving a Member instance based on data from Member, where the search criteria is that the
    //Member retrieved needs to match the inserted last name of the member.
    @Override
    public User getUserFromLastName(String name) {
        return searchAndCreateUser(MemberDBRowNames.LAST_NAME, name);
    }

    //Retrieves list of member instances from all users based on database.
    public ArrayList<User> getListOfUsers() {
        //Gets list of rows from the database class getRows method.
        //Inserts the rows from this specific database class into the UserReturn default method for getting users.
        return UserReturn.super.getListOfUsers(super.getRows());
    }

    //Calls the default method in User Return, inserting the rows and index of column to be searched through.
    public User searchAndCreateUser(DBRowNames catToFindBy, String searchValue) {
        //Gets the index number of the column that was wished to be searched for.
        int indexToSearchBy = super.getIndexOfRowName(catToFindBy);
        //Calls the default method in User Return, inserting the rows and index of column to be searched through.
        return UserReturn.super.searchAndCreateUser(searchValue, indexToSearchBy, super.getRows());
    }

    //Method for creating a member Instance from a single row. A Single row represents one members data.
    public User createUserFromSingleRow(String[] singleRow) {
        //Gets the member id from the row.
        int userid = Integer.parseInt(singleRow[getIndexOfRowName(MemberDBRowNames.USER_ID)]);
        //Gets first and last name from the member.
        String firstName = singleRow[getIndexOfRowName(MemberDBRowNames.FIRST_NAME)];
        String lastName = singleRow[getIndexOfRowName(MemberDBRowNames.LAST_NAME)];
        //Gets and converts the competitive status of the member (if normal member or competittive member) to boolean.
        boolean isCompetitive = Boolean.parseBoolean(singleRow[getIndexOfRowName(MemberDBRowNames.IS_COMPETITIVE)]);
        //Gets and converts the activity status (passive/active) to boolean.
        boolean isActiveMember = Boolean.parseBoolean(singleRow[getIndexOfRowName(MemberDBRowNames.IS_ACTIVE_MEMBER)]);
        //Gets and converts the age of the member.
        String dateOfBirth = singleRow[getIndexOfRowName(MemberDBRowNames.DATE_OF_BIRTH)];
        //Gets and converts the arrears status (is not in debt/is in debt) to boolean.
        boolean isArrears = Boolean.parseBoolean(singleRow[getIndexOfRowName(MemberDBRowNames.IS_ARREARS)]);
        //Checks if the user is a Competitive member.
        //Both Member and competitiveMember are created in MemberDB, as their constructor parameters are identical.
        if (isCompetitive) {
            //Returns a new Competitive Member instance.
            CompetitiveMemberDB competitiveMemberDB = new CompetitiveMemberDB();
            //Passes the already known Member values to the CompetitiveMemberDB for instancing a CompetitiveMember.
            competitiveMemberDB.setMemberAttributes(userid, firstName, lastName, isActiveMember, dateOfBirth, isArrears);
            return competitiveMemberDB.getUserFromID(userid);
        } else {
            //Returns a new Member instance.
            return new Member(userid, firstName, lastName, isActiveMember, false, dateOfBirth, isArrears);
        }
    }

    //Method for getting the string formats of the column names, which is stores inside the enum class of each
    //database row name enums. See package rowNameEnum.
    @Override
    public DBRowNames[] getEnumRowNames() {
        return MemberDBRowNames.values();
    }

    //Method for editing a member in the MemberDB csv file. Returns true if edit was successfull.
    //The method takes in a user instance. This user instance is the EDITED version of the user instance,
    // which has been edited and processed inside the SuperUserProcessor.
    @Override
    public boolean editUserInDB(User user) {
        //Checks of the user instance is indeed an instance of Member.
        if (user instanceof Member member) {
            //Gets list of all member instances from DB.
            ArrayList<User> allUsers = getListOfUsers();
            //Gets list of all members as formatted DB String from DB.
            ArrayList<String[]> allRows = super.getRows();
            //Creates new String[] array with length corresponding to amount of columns in DB.
            String[] newRow = new String[MemberDBRowNames.values().length];
            //Gets the id, first name, last name as a String;
            newRow[getIndexOfRowName(MemberDBRowNames.USER_ID)] = String.valueOf(member.getUserID());
            newRow[getIndexOfRowName(MemberDBRowNames.FIRST_NAME)] = member.getFirstName();
            newRow[getIndexOfRowName(MemberDBRowNames.LAST_NAME)] = member.getLastName();
            //Gets the activity status, competitive status, age, arrears status and yearly membership fee as Strings.
            newRow[getIndexOfRowName(MemberDBRowNames.IS_ACTIVE_MEMBER)] = String.valueOf(member.isActiveMember());
            newRow[getIndexOfRowName(MemberDBRowNames.IS_COMPETITIVE)] = String.valueOf(member.isCompetitive());
            newRow[getIndexOfRowName(MemberDBRowNames.DATE_OF_BIRTH)] = member.getDateOfBirth();
            newRow[getIndexOfRowName(MemberDBRowNames.IS_ARREARS)] = String.valueOf(member.isArrears());
            newRow[getIndexOfRowName(MemberDBRowNames.YEARLY_MEMBERSHIP_FEE)] = String.valueOf(member.getYearlyMembershipFee());

            //Loops through list of member instances.
            for (int i = 0; i < allUsers.size(); i++) {
                //If the userid in allusers equals the member id of the wished edited member,
                // it means we now know the index place of the wished member in the database,
                // and we can now replace this index.
                if (allUsers.get(i).getUserID() == member.getUserID()) {
                    //We replace the old member with the new edited member with equal id.
                    allRows.set(i, newRow);
                    break;
                }
            }

            //We call the function that inserts a list of formatted rows into the db. If it returns false,
            //It was not successfull in inserting data into the database.
            if(!insertListToDB(allRows)) {
                return false;
            }

            if(user instanceof CompetitiveMember compMember) {
                CompetitiveMemberDB compMemberDB = new CompetitiveMemberDB();
                return compMemberDB.editUserInDB(compMember);
            }
        }
        return true;
    }

    public int getIDForNewUser() {
        return getIDForNewEntry(UserDBRowNames.USER_ID, super.getRows());
    }

    public boolean addUserInDB(User user) {
        if(!(user instanceof Member member)) {
            return false;
        } else {
            ArrayList<String[]> allRows = getRows();

            String[] newRow = new String[MemberDBRowNames.values().length];
            newRow[getIndexOfRowName(MemberDBRowNames.USER_ID)] = String.valueOf(member.getUserID());
            newRow[getIndexOfRowName(MemberDBRowNames.FIRST_NAME)] = member.getFirstName();
            newRow[getIndexOfRowName(MemberDBRowNames.LAST_NAME)] = member.getLastName();
            newRow[getIndexOfRowName(MemberDBRowNames.IS_ACTIVE_MEMBER)] = String.valueOf(member.isActiveMember());
            newRow[getIndexOfRowName(MemberDBRowNames.IS_COMPETITIVE)] = String.valueOf(member.isCompetitive());
            newRow[getIndexOfRowName(MemberDBRowNames.DATE_OF_BIRTH)] = member.getDateOfBirth();
            newRow[getIndexOfRowName(MemberDBRowNames.IS_ARREARS)] = String.valueOf(member.isArrears());
            newRow[getIndexOfRowName(MemberDBRowNames.YEARLY_MEMBERSHIP_FEE)] = String.valueOf(member.getYearlyMembershipFee());

            allRows.add(newRow);

            if(!insertListToDB(allRows)) {
                return false;
            }

            if(member instanceof CompetitiveMember compMember) {
                CompetitiveMemberDB compMemberDB = new CompetitiveMemberDB();
                return compMemberDB.addUserInDB(compMember);

            } else {
                return true;
            }

        }
    }

    @Override
    public boolean removeUserFromDB(User user) {
        if(user instanceof Member member) {
            ArrayList<String[]> allRows = getRows();

            for(String[] singleRow : allRows) {
                int userIDFromDB = Integer.parseInt(singleRow[getIndexOfRowName(MemberDBRowNames.USER_ID)]);
                if(userIDFromDB == member.getUserID()) {
                    allRows.remove(singleRow);
                    break;
                }
            }

            if(!insertListToDB(allRows)) {
                return false;
            }

            if(member instanceof CompetitiveMember) {
                CompetitiveMemberDB compDB = new CompetitiveMemberDB();
                return compDB.removeUserFromDB(member);
            } else {
                return true;
            }
        }

        return false;
    }
}
