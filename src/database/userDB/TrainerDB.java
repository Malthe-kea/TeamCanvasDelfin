package database.userDB;

import database.Database;
import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.TrainerDBRowNames;
import database.rowNameEnum.UserDBRowNames;
import user_domain.Trainer;
import user_domain.User;

import java.util.ArrayList;

public class TrainerDB extends Database implements UserReturn {

    public TrainerDB() {
        //Sets the filepath of the specific csv file to be used when calling this DB class.
        super(UserReturn.getFolderPath()+"TrainerDB.csv");
    }

    //Method for retreiving a Trainer instance based on data from database, where the search criteria is that the
    //Trainer retrieved needs to match the inserted id of the trainer.
    @Override
    public User getUserFromID(int id) {
        return searchAndCreateUser(TrainerDBRowNames.USER_ID, String.valueOf(id));
    }

    //Method for retreiving a Trainer instance based on data from Trainer, where the search criteria is that the
    //Trainer retrieved needs to match the inserted last name of the trainer.
    @Override
    public User getUserFromLastName(String name) {
        return searchAndCreateUser(TrainerDBRowNames.LAST_NAME, name);
    }

    //Retrieves list of trainer instances from all users based on database.
    public ArrayList<User> getListOfUsers() {
        //Gets list of rows from the database class getRows method.
        //Inserts the rows from this specific database class into the UserReturn default method for getting users.
        return getListOfUsers(super.getRows());
    }

    //Method for searching through database and creating a new Trainer instance based on found trainer.
    public User searchAndCreateUser(DBRowNames catToFindBy, String searchValue) {
        //Gets the index number of the column that was wished to be searched for.
        int indexToSearchBy = super.getIndexOfRowName(catToFindBy);
        //Calls the default method in User Return, inserting the rows and index of column to be searched through.
        return UserReturn.super.searchAndCreateUser(searchValue, indexToSearchBy, super.getRows());
    }

    //Method for creating a trainer Instance from a single row. A Single row represents one trainers data.
    @Override
    public User createUserFromSingleRow(String[] singleRow) {
        //Gets the trainers id from the row.
        int userid = Integer.parseInt(singleRow[getIndexOfRowName(TrainerDBRowNames.USER_ID)]);
        //Gets first and last name from the trainer.
        String firstName = singleRow[getIndexOfRowName(TrainerDBRowNames.FIRST_NAME)];
        String lastName = singleRow[getIndexOfRowName(TrainerDBRowNames.LAST_NAME)];
        //Gets the boolean value representing if the trainer is a junior (false) or senior (true) trainer.
        boolean isSeniorTrainer = Boolean.parseBoolean(singleRow[getIndexOfRowName(TrainerDBRowNames.IS_SENIOR_TRAINER)]);
        //Inserts the above values into a new trainer instance.
        return new Trainer(userid,firstName,lastName,isSeniorTrainer);
    }

    //Method for getting the string formats of the column names, which is stores inside the enum class of each
    //database row name enums. See package rowNameEnum.
    @Override
    public DBRowNames[] getEnumRowNames() {
        return TrainerDBRowNames.values();
    }

    //Method for editing a trainer in the TrainerDB csv file. Returns true if edit was successfull.
    //The method takes in a user instance. This user instance is the EDITED version of the user instance,
    // which has been edited and processed inside the SuperUserProcessor.
    @Override
    public boolean editUserInDB(User user) {
        //Checks of the user instance is indeed an instance of Trainer.
        if(user instanceof Trainer trainer) {
            //Gets list of all trainer instances from DB.
            ArrayList<User> allUsers = getListOfUsers();
            //Gets list of all trainers as formatted DB String from DB.
            ArrayList<String[]> allRows = super.getRows();
            //Creates new String[] array with length corresponding to amount of columns in DB.
            String[] newRow = new String[TrainerDBRowNames.values().length];
            //Gets the id, first name, last name and boolean value of trainer (Senior or Junior) as a String
            newRow[getIndexOfRowName(TrainerDBRowNames.USER_ID)] = String.valueOf(trainer.getUserID());
            newRow[getIndexOfRowName(TrainerDBRowNames.FIRST_NAME)] = trainer.getFirstName();
            newRow[getIndexOfRowName(TrainerDBRowNames.LAST_NAME)] = trainer.getLastName();
            newRow[getIndexOfRowName(TrainerDBRowNames.IS_SENIOR_TRAINER)] = String.valueOf(trainer.isSeniorTrainer());

            //Loops through list of user instances.
            for (int i = 0; i < allUsers.size(); i++) {
                //If the userid in allusers equals the trainer id of the wished edited trainer,
                // it means we now know the index place of the wished trainer in the database,
                // and we can now replace this index.
                if(allUsers.get(i).getUserID() == trainer.getUserID()) {
                    //We replace the old member with the new edited member with equal id.
                    allRows.set(i,newRow);
                    break;
                }
            }
            //We insert the edited row into the database.
            return insertListToDB(allRows);

        }
        return false;
    }

    public int getIDForNewUser() {
        return getIDForNewEntry(UserDBRowNames.USER_ID, super.getRows());
    }

    public boolean addUserInDB(User user) {
        if(!(user instanceof Trainer trainer && user.getUserID() == getIDForNewUser())) {
            return false;
        } else {
            ArrayList<String[]> allRows = super.getRows();
            String[] newRow = new String[TrainerDBRowNames.values().length];
            newRow[getIndexOfRowName(TrainerDBRowNames.USER_ID)] = String.valueOf(trainer.getUserID());
            newRow[getIndexOfRowName(TrainerDBRowNames.FIRST_NAME)] = trainer.getFirstName();
            newRow[getIndexOfRowName(TrainerDBRowNames.LAST_NAME)] = trainer.getLastName();
            newRow[getIndexOfRowName(TrainerDBRowNames.IS_SENIOR_TRAINER)] = String.valueOf(trainer.isSeniorTrainer());
            allRows.add(newRow);
            return insertListToDB(allRows);
        }
    }

    @Override
    public boolean removeUserFromDB(User user) {
        if(user instanceof Trainer trainer) {
            ArrayList<String[]> allRows = getRows();

            for(String[] singleRow : allRows) {
                int userIDFromDB = Integer.parseInt(singleRow[getIndexOfRowName(TrainerDBRowNames.USER_ID)]);
                if(userIDFromDB == trainer.getUserID()) {
                    allRows.remove(singleRow);
                    return insertListToDB(allRows);
                }
            }
        }
        return false;
    }
}
