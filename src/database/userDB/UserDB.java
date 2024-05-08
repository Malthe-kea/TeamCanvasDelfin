package database.userDB;

import database.Database;
import database.SortRowByIDComparator;
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
        //Inserts the rows from this specific database class into the UserReturn default method for getting users.
        return getListOfUsers(super.getRows());
    }


    //Method for searching through database and creating a new User instance based on found user.
    public User searchAndCreateUser(DBRowNames catToFindBy, String searchValue) {
        //Gets the index number of the column that was wished to be searched for.
        int indexToSearchBy = super.getIndexOfRowName(catToFindBy);
        //Calls the default method in User Return, inserting the rows and index of column to be searched through.
        return UserReturn.super.searchAndCreateUser(searchValue, indexToSearchBy, super.getRows());
    }

    //Method for creating a user Instance from a single row. A Single row represents one users data.
    @Override
    public User createUserFromSingleRow(String[] singleRow) {
        //Gets the user id from the row.
        int userId = Integer.parseInt(singleRow[getIndexOfRowName(UserDBRowNames.USER_ID)]);
        //Gets the permission level of the user.
        int permissionLevelOfPulledRow = Integer.parseInt(singleRow[getIndexOfRowName(UserDBRowNames.PERMISSION_LEVEL)]);
        //Gets first and last name from the user.
        String firstName = singleRow[getIndexOfRowName(UserDBRowNames.FIRST_NAME)];
        String lastName = singleRow[getIndexOfRowName(UserDBRowNames.LAST_NAME)];

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
    public DBRowNames[] getEnumRowNames() {
        return UserDBRowNames.values();
    }

    //Method for editing a user in the UserDB csv file. Returns true if edit was successfull.
    //The method takes in a user instance. This user instance is the EDITED version of the user instance,
    // which has been edited and processed inside the SuperUserProcessor.
    public boolean editUserInDB(User user) {
        //Gets list of all users in UserDB csv file.
        ArrayList<User> allUsers = getListOfUsers();
        //Creates new String[] array with length corresponding to amount of columns in DB.
        String[] newRow = new String[UserDBRowNames.values().length];
        //Retrieves the user id as String aswell as first and lastname.
        newRow[getIndexOfRowName(UserDBRowNames.USER_ID)] = String.valueOf(user.getUserID());
        newRow[getIndexOfRowName(UserDBRowNames.FIRST_NAME)] = user.getFirstName();
        newRow[getIndexOfRowName(UserDBRowNames.LAST_NAME)] = user.getLastName();


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
        ArrayList<String[]> allRows = super.getRows();
        //Loops through the user instances.
        for (int i = 0; i < allUsers.size(); i++) {
            //If the userid in allusers equals the user id of the wished edited user, it means we now know the
            //index place of the wished user in the database, and we can now retrieve the password.
            if(allUsers.get(i).getUserID() == user.getUserID()) {
                //Retrieves the password from the database and indserts it into our row.
                newRow[2] = allRows.get(i)[getIndexOfRowName(UserDBRowNames.PASSWORD)];
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

    //Method for getting the next ID for a new user to be inserted into the database.
    public int getIDForNewUser() {
        return getIDForNewEntry(UserDBRowNames.USER_ID, super.getRows());
    }


    public boolean addUserInDB(User user, String password) {
        if(user.getUserID() != getIDForNewUser()) {
            return false;
        } else {
            ArrayList<String[]> allRows = getRows();

            String[] newRow = new String[UserDBRowNames.values().length];
            newRow[getIndexOfRowName(UserDBRowNames.USER_ID)] = String.valueOf(user.getUserID());
            newRow[getIndexOfRowName(UserDBRowNames.FIRST_NAME)] = user.getFirstName();
            newRow[getIndexOfRowName(UserDBRowNames.LAST_NAME)] = user.getLastName();
            newRow[getIndexOfRowName(UserDBRowNames.PASSWORD)] = password;
            int permissionLevelIndex = getIndexOfRowName(UserDBRowNames.PERMISSION_LEVEL);
            switch(DelfinUtil.checkUserInstance(user)) {
                case SUPER -> {
                    newRow[permissionLevelIndex] = "1";
                }
                case TREASURER -> {
                    newRow[permissionLevelIndex] = "2";
                }
                case TRAINER -> {
                    newRow[permissionLevelIndex] = "3";
                }
                default -> {
                    newRow[permissionLevelIndex] = "4";
                }
            }


            allRows.add(newRow);

            if(!insertListToDB(allRows)) {
                return false;
            }

            switch(DelfinUtil.checkUserInstance(user)) {
                case MEMBER, COMPETITIVE -> {
                    MemberDB memberDB = new MemberDB();
                    return memberDB.addUserInDB(user);
                }
                case TRAINER -> {
                    TrainerDB trainerDB = new TrainerDB();
                    return trainerDB.addUserInDB(user);
                }
                case SUPER, TREASURER -> {
                    return true;
                }
                case NOTFOUND -> {
                    return false;
                }
            }

        }
        return false;
    }

    public boolean removeUserFromDB(int id) {
        return removeUserFromDB(getUserFromID(id));
    }

    public boolean removeUserFromDB(User user) {
        ArrayList<String[]> allRows = getRows();

        for(String[] singleRow : allRows) {
            int userIDFromDB = Integer.parseInt(singleRow[getIndexOfRowName(UserDBRowNames.USER_ID)]);
            if(userIDFromDB == user.getUserID()) {
                allRows.remove(singleRow);
                break;
            }
        }


        if(!insertListToDB(allRows)) {
            return false;
        }

        switch(DelfinUtil.checkUserInstance(user)) {
            case MEMBER, COMPETITIVE -> {
                MemberDB memberDB = new MemberDB();
                return memberDB.removeUserFromDB(user);
            }
            case TRAINER -> {
                TrainerDB trainerDB = new TrainerDB();
                return trainerDB.removeUserFromDB(user);
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