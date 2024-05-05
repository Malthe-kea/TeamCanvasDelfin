package database.userDB;

import database.Database;
import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.MemberDBRowNames;
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
        return searchAndCreateUser(MemberDBRowNames.USER_ID, id + "");
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
        ArrayList<String[]> allRows = getRows();
        //Inserts the rows from this specific database class into the UserReturn default method for getting users.
        return getListOfUsers(allRows);
    }

    //Calls the default method in User Return, inserting the rows and index of column to be searched through.
    public User searchAndCreateUser(DBRowNames catToFindBy, String searchValue) {
        //Gets the index number of the column that was wished to be searched for.
        int indexToSearchBy = getIndexOfRowName(catToFindBy);
        //Calls the default method in User Return, inserting the rows and index of column to be searched through.
        return searchAndCreateUser(searchValue, indexToSearchBy, getRows());
    }

    //Method for creating a member Instance from a single row. A Single row represents one members data.
    public User createUserFromSingleRow(String[] singleRow) {
        //Gets the member id from the row.
        int userid = Integer.parseInt(singleRow[0]);
        //Gets first and last name from the member.
        String firstName = singleRow[1];
        String lastName = singleRow[2];
        //Gets and converts the competitive status of the member (if normal member or competittive member) to boolean.
        boolean isCompetitive = Boolean.parseBoolean(singleRow[4]);
        //Gets and converts the activity status (passive/active) to boolean.
        boolean isActiveMember = Boolean.parseBoolean(singleRow[3]);
        //Gets and converts the age of the member.
        int age = Integer.parseInt(singleRow[5]);
        //Gets and converts the arrears status (is not in debt/is in debt) to boolean.
        boolean isArrears = Boolean.parseBoolean(singleRow[6]);
        //Checks if the user is a Competitive member.
        //Both Member and competitiveMember are created in MemberDB, as their constructor parameters are identical.
        if (isCompetitive) {
            //Returns a new Competitive Member instance.
            return new CompetitiveMember(userid, firstName, lastName, isActiveMember, true, age, isArrears);
        } else {
            //Returns a new Member instance.
            return new Member(userid, firstName, lastName, isActiveMember, false, age, isArrears);
        }
    }

    //Method for getting the string formats of the column names, which is stores inside the enum class of each
    //database row name enums. See package rowNameEnum.
    @Override
    public ArrayList<String> getRowNamesFromEnumConfig() {
        //Creates new arraylist for row Name String to be inserted into.
        ArrayList<String> rowNamesToCreate = new ArrayList<>();
        //Loops through each Enum value in the MemberDB Row Name Enum class.
        for (MemberDBRowNames memberDBRowNames : MemberDBRowNames.values()) {
            //Adds the string variant of the enum, which correlates to the column name in the MemberDB csv file,
            //to the arraylist.
            rowNamesToCreate.add(memberDBRowNames.getStringVariant());
        }
        return rowNamesToCreate;
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
            ArrayList<String[]> allRows = getRows();
            //Creates new String[] array with length corresponding to amount of columns in DB.
            String[] newRow = new String[8];
            //Gets the id, first name, last name as a String;
            newRow[0] = String.valueOf(member.getUserID());
            newRow[1] = member.getFirstName();
            newRow[2] = member.getLastName();
            //Gets the activity status, competitive status, age, arrears status and yearly membership fee as Strings.
            newRow[3] = String.valueOf(member.isActiveMember());
            newRow[4] = String.valueOf(member.isCompetitive());
            newRow[5] = String.valueOf(member.getAge());
            newRow[6] = String.valueOf(member.isArrears());
            newRow[7] = String.valueOf(member.getYearlyMembershipFee());

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
            //We insert the edited row into the database.
            return insertListToDB(allRows);
        }
        return false;
    }
}
