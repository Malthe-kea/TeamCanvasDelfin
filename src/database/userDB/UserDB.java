package database.userDB;

import database.Database;
import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.UserDBRowNames;
import domain_model.DelfinUtil;
import user_domain.SuperUser;
import user_domain.Treasurer;
import user_domain.User;

import java.util.ArrayList;

public class UserDB extends Database implements UserReturn {
    public UserDB() {
        //Sets the filepath of the specific csv file to be used when calling this DB class.
        super(UserReturn.getFolderPath() + "UserDB.csv");

    }

    //Method for retreiving a User instance based on data from database, where the search criteria is that the
    //User retrieved needs to match the inserted id of the user.
    @Override
    public User getUserFromID(int id) {
        return searchAndCreateUser(UserDBRowNames.USER_ID, String.valueOf(id));
    }

    //Method for retreiving a User instance based on data from database, where the search criteria is that the
    //User retrieved needs to match the inserted last name of the user.
    @Override
    public User getUserFromLastName(String name) {
        return searchAndCreateUser(UserDBRowNames.LAST_NAME, name);
    }

    //Retrieves list of User instances from all users based on database.
    public ArrayList<User> getListOfUsers() {
        //Gets list of rows from the database class getRows method.
        ArrayList<String[]> allRows = getRows();
        //Inserts the rows from this specific database class into the UserReturn default method for getting users.
        return getListOfUsers(allRows);
    }


    //Method for searching through database and creating a new User instance based on found user.
    public User searchAndCreateUser(DBRowNames catToFindBy, String searchValue) {
        //Gets the index number of the column that was wished to be searched for.
        int indexToSearchBy = getIndexOfRowName(catToFindBy);
        //Calls the default method in User Return, inserting the rows and index of column to be searched through.
        return searchAndCreateUser(searchValue, indexToSearchBy, getRows());
    }

    //Method for creating a user Instance from a single row. A Single row represents one users data.
    @Override
    public User createUserFromSingleRow(String[] singleRow) {
        //Gets the user id from the row.
        int userId = Integer.parseInt(singleRow[0]);
        //Gets the permission level of the user.
        int permissionLevelOfPulledRow = Integer.parseInt(singleRow[1]);
        //Gets first and last name from the user.
        String firstName = singleRow[3];
        String lastName = singleRow[4];

        //Checks which permissionlevel the user has.
        //1: SuperUser
        //2: Treasurer
        //3: Trainer
        //4: Member (Both normal member and competitive)
        switch (permissionLevelOfPulledRow) {
            case 1 -> {
                //Returns new superuser, as attributes are identical to normal abstract User class.
                return new SuperUser(userId, firstName, lastName);
            }
            case 2 -> {
                //Returns new Treasurer, as attributes are identical to normal abstract User class.
                return new Treasurer(userId, firstName, lastName);
            }
            case 3 -> {
                //Calls the trainer database, and gets the Trainer created from the Trainer database, and returns.
                //Is necessary to ensure correct instance is made and all attributes of the trainer is inserted correctly.
                TrainerDB trainerDB = new TrainerDB();
                return trainerDB.getUserFromID(userId);
            }
            default -> {
                //Calls the Member database, and gets the Member created from the Member database, and returns.
                //Is necessary to ensure correct instance is made and all attributes of the Membeer is inserted correctly.
                MemberDB memberDB = new MemberDB();
                return memberDB.getUserFromID(userId);
            }
        }
    }

    //Method for getting the string formats of the column names, which is stores inside the enum class of each
    //database row name enums. See package rowNameEnum.
    @Override
    public ArrayList<String> getRowNamesFromEnumConfig() {
        //Creates new arraylist for row Name String to be inserted into.
        ArrayList<String> rowNamesToCreate = new ArrayList<>();

        //Loops through each Enum value in the UserDB Row Name Enum class.
        for(UserDBRowNames userDBRowNames: UserDBRowNames.values()) {
            //Adds the string variant of the enum, which correlates to the column name in the UserDB csv file,
            //to the arraylist.
            rowNamesToCreate.add(userDBRowNames.getStringVariant());
        }
        return rowNamesToCreate;
    }

    //Method for editing a user in the UserDB csv file. Returns true if edit was successfull.
    //The method takes in a user instance. This user instance is the EDITED version of the user instance,
    // which has been edited and processed inside the SuperUserProcessor.
    public boolean editUserInDB(User user) {
        //Gets list of all users in UserDB csv file.
        ArrayList<User> allUsers = getListOfUsers();
        //Creates new String[] array with length corresponding to amount of columns in DB.
        String[] newRow = new String[5];
        //Retrieves the user id as String aswell as first and lastname.
        newRow[0] = String.valueOf(user.getUserID());
        newRow[3] = user.getFirstName();
        newRow[4] = user.getLastName();


        //This switch case checks the instance of the user, and sets the correct permissionLevel
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

        //This code retrieves the password set in the database, as passwords are not saved inside the member
        //Instance itself.
        //Gets rows in UserDB.
        ArrayList<String[]> allRows = getRows();
        //Loops through the user instances.
        for (int i = 0; i < allUsers.size(); i++) {
            //If the userid in allusers equals the user id of the wished edited user, it means we now know the
            //index place of the wished user in the database, and we can now retrieve the password.
            if(allUsers.get(i).getUserID() == user.getUserID()) {
                //Retrieves the password from the database and indserts it into our row.
                newRow[2] = getRows().get(i)[2];
                //We now replace our old user inside our row with the edited data.
                allRows.set(i,newRow);
                break;
            }
        }

        //We call the function that inserts a list of formatted rows into the db. If it returns false,
        //It was not successfull in inserting data into the database.
        if(!insertListToDB(allRows)) {
            return false;
        }

        //Switch case that checks the instance of the user.
        //If the user is a Member, Competitive or trainer, we
        //Call the editUserInDB function of the respective database class,
        //so the csv file of each of these also are edited.
        switch(DelfinUtil.checkUserInstance(user)) {
            case MEMBER, COMPETITIVE -> {
                MemberDB memberDB = new MemberDB();
                return memberDB.editUserInDB(user);

            }
            case TRAINER -> {
                TrainerDB trainerDB = new TrainerDB();
                return trainerDB.editUserInDB(user);
            }
            case SUPER, TREASURER -> {
                return true;
            }
            case NOTFOUND -> {
                return false;
            }
        }
        return false;
    }
}
