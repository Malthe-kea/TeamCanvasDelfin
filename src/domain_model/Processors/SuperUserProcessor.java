package domain_model.Processors;

import database.Database;
import database.DBController;
import domain_model.*;
import user_domain.*;

import java.util.ArrayList;
import java.util.Scanner;

public class SuperUserProcessor implements Processor {
    private Scanner userInput;
    DBController dbController;
    private ArrayList<User> userListArr;
    boolean programRunning = true;

    public SuperUserProcessor(DBController dbController) {
        this.dbController = dbController;
        userInput = new Scanner(System.in);
    }


    public ArrayList createSuperUser() {
        ArrayList<SuperUser> superUser = new ArrayList<>();
        superUser.add(new SuperUser(1, "flemming", "Rasmussen"));
        return superUser;
    }

    public ArrayList<String> getUserIDAndNameList() {
        ArrayList<String> userList = new ArrayList<>();
        ArrayList<User> allUsers = dbController.getListOfAllUsers();
        for (User u : allUsers) {
            UserInstance userInstance = DelfinUtil.checkUserInstance(u);
            userList.add(u.getUserID() + ": " + u.getFirstName() + " " + u.getLastName());
        }
        return userList;
    }

    public void CreateandAddMembertoDB(String password, String firstNames, String lastNames, boolean activePassiveInput, String birthDate, boolean isCompetitiveInput, boolean isArrearsInput) {

        Boolean isActiveMember = activePassiveInput;
        Boolean isCompetitive = isCompetitiveInput;
        Boolean isArrears = isArrearsInput;
        //LocalDate dob = LocalDate.parse(birthDate);

        //TODO new member tager imod dateOfBirth som en string, det skal være LocalDate.
        User memberToAdd = new Member(dbController.getIDForNewUser(), firstNames, lastNames, isActiveMember, isCompetitive, birthDate, isArrears);
        dbController.addUserToDB(memberToAdd, password);
    }

    public void CreateandAddTrainertoDB(String password, String firstNames, String lastNames, boolean isSeniorTrainer) {

        User trainerToAdd = new Trainer(dbController.getIDForNewUser(), firstNames, lastNames, isSeniorTrainer);
        dbController.addUserToDB(trainerToAdd, password);
    }

    public void CreateandAddCompetitiveMembertoDB(String password, String firstNames, String lastName, boolean isActiveMemberInput, boolean isCompetitiveInput, String birthDate, boolean isArrearsInput) {

        Boolean isActiveMember = isActiveMemberInput;
        Boolean isCompetitive = isCompetitiveInput;
        Boolean isArrears = isArrearsInput;
        //TODO new competitiveMember tager imod dateOfBirth som en string, det skal være LocalDate.
//        LocalDate dob = LocalDate.parse(birthDate);

//Her skal laves en metode, der tager seneste userID fra DB'en og incrementer den med 1.
        //testDB.add(new CompetitiveMember(3, "Susse", "Sonnegaard", true, true, calculateAge(dob), false));
        User competitiveMemberToAdd = new CompetitiveMember(dbController.getIDForNewUser(), firstNames, lastName, isActiveMember, isCompetitive, birthDate, isArrears);
        dbController.addUserToDB(competitiveMemberToAdd, password);
    }

    public void CreateAndAddTreasurertoDB(String password, String firstNames, String lastNames) {

        User treasurerToAdd = new Treasurer(dbController.getIDForNewUser(), firstNames, lastNames);
        dbController.addUserToDB(treasurerToAdd, password);
    }

    public void editMember(String userIDInput, String firstNameInput, String lastNameInput, String isActiveMemberInput, String isCompetitiveInput, String isArrearsInput) {
        int userID = Integer.parseInt(userIDInput);
        Member userToEdit = (Member) dbController.getUserFromID(userID);

        if (!firstNameInput.isBlank()) {
            userToEdit.setFirstName(firstNameInput);
        }

        if (!lastNameInput.isBlank()) {
            userToEdit.setLastName(lastNameInput);
        }

        userToEdit.setActiveMember(Boolean.parseBoolean(isActiveMemberInput));
        userToEdit.setArrears(Boolean.parseBoolean(isArrearsInput));

        boolean isCompetitive = Boolean.parseBoolean(isCompetitiveInput);

        if (isCompetitive != userToEdit.isCompetitive()) {
            User convertedMember;

            if (isCompetitive) {
                convertedMember = new CompetitiveMember(userToEdit.getUserID(), userToEdit.getFirstName(), userToEdit.getLastName(), userToEdit.isActiveMember(), true, userToEdit.getDateOfBirth(), userToEdit.isArrears());
            } else {
                convertedMember = new Member(userToEdit.getUserID(), userToEdit.getFirstName(), userToEdit.getLastName(), userToEdit.isActiveMember(), true, userToEdit.getDateOfBirth(), userToEdit.isArrears());
            }

            String password = dbController.getPasswordFromID(userToEdit.getUserID());
            dbController.removeUserFromDB(userID);
            dbController.addUserToDB(convertedMember, password);
        } else {
            dbController.editUserInDB(userToEdit);
        }

    }

    public void editTrainer(String userIDInput, String firstNameInput, String lastNameInput, String isSeniorTrainerInput) {
        int userID = Integer.parseInt(userIDInput);
        Member userToEdit = (Member) dbController.getUserFromID(userID);

        if (!firstNameInput.isBlank()) {
            userToEdit.setFirstName(firstNameInput);
        }
        if (!lastNameInput.isBlank()) {
            userToEdit.setLastName(lastNameInput);
        }
        boolean isSenior = Boolean.parseBoolean(isSeniorTrainerInput);
        userToEdit.setSenior(isSenior);

        dbController.editUserInDB(userToEdit);
    }
    public void editTreassurer(String userIDInput, String firstNameInput, String lastNameInput) {
        int userID = Integer.parseInt(userIDInput);
        Member userToEdit = (Member) dbController.getUserFromID(userID);

        if (!firstNameInput.isBlank()) {
            userToEdit.setFirstName(firstNameInput);
        }
        if (!lastNameInput.isBlank()) {
            userToEdit.setLastName(lastNameInput);
        }
        dbController.editUserInDB(userToEdit);
    }




    public void editAdmin() {

    }

    public void deleteUserFromDB(int indexInList) {

        ArrayList<User> allUsers = dbController.getListOfAllUsers();
        User userToDelete = allUsers.get(indexInList);
        dbController.removeUserFromDB(userToDelete);

    }

    public String getUserInfo(int indexToShow) {
        ArrayList<User> allUsers = dbController.getListOfAllUsers();
        User userForInfo = allUsers.get(indexToShow);

        switch (DelfinUtil.checkUserInstance(userForInfo)) {
            case MEMBER -> {
                return ((Member) userForInfo).toString();
            }
            case COMPETITIVE -> {
                return ((CompetitiveMember) userForInfo).toString();
            }
            case TRAINER -> {
                return ((Trainer) userForInfo).toString();
            }
            case SUPER, TREASURER -> {
                return userForInfo.toString();
            }
        }
        return "null";
    }


    public Database getUserFromDB(int idToEdit) {
        //TODO send DB return
        String command = "1";
        print("""
                Hvilket brugeroversigt vil du gerne se?
                1. Crawl
                2. Rygcrawl
                3. Brystsvømning
                4. Butterfly
                5. Aktive/Passive medlemmer
                6. Juniorer/Seniorer
                """);
        while (programRunning)
            switch (command) {
                case "1" -> {
                    print("Get XYZ-database");
                }
                case "2" -> {
                    print("Get XYZ-database");
                }
                case "3" -> {
                    print("Get XYZ-database");
                }
                case "4" -> {
                    print("Get XYZ-database");
                }
                case "5" -> {
                    print("Get XYZ-database");
                }
                case "6" -> {
                    print("Get XYZ-database");
                }
                default -> {
                    print("invalid input");
                }
            }
        return null;
    }

    private void print(String s) {
        System.out.println(s);
    }
}
