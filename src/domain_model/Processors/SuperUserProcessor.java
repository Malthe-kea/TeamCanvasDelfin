package domain_model.Processors;

import database.Database;
import database.DBController;
import domain_model.*;
import database.userDB.UserDB;
import user_domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;

public class SuperUserProcessor implements Processor {
    private Scanner userInput;
    DBController dbController;
    private ArrayList<User> userListArr;
    boolean programRunning = true;

    public SuperUserProcessor(DBController dbController) {
        this.dbController = dbController;
        userInput = new Scanner(System.in);
    }

    @Override
    public UIDisplayInfo getMainMenu() {


        ArrayList<String> options = new ArrayList<>(List.of(
                "Tilføj medlem",
                "Tilføj træner",
                "Tilføj konkurrence-medlem",
                "Tilføj kasserer",
                "Ændre medlemsoplysninger",
                "Slet medlem",
                "Se brugeroversigt"));


        return new UIDisplayInfo("SuperUser Menu", "Vælg en af følgende muligheder", DisplayType.MENU, options);


    }

    @Override
    public UIDisplayInfo processMainMenuOption(int option) {
        return null;
    }

    public ArrayList createSuperUser() {
        ArrayList<SuperUser> superUser = new ArrayList<>();
        superUser.add(new SuperUser(1, "flemming", "Rasmussen"));
        return superUser;
    }

    public ArrayList<String> getUserList() {
        ArrayList<String> userList = new ArrayList<>();
        ArrayList<User> allUsers = dbController.getListOfAllUsers();
        for (User u : allUsers) {
            UserInstance userInstance = DelfinUtil.checkUserInstance(u);
            switch (userInstance) {
                case MEMBER -> {
                    userList.add(((Member) u).toString());
                }
                case COMPETITIVE -> {
                    userList.add(((CompetitiveMember) u).toString());
                }
                case TRAINER -> {
                    userList.add(((Trainer) u).toString());
                }
                case SUPER, TREASURER -> {
                    userList.add(u.toString());
                }
            }
        }
        return userList;
    }

    public void CreateandAddMembertoDB(String password, String firstNames, String lastNames, boolean activePassiveInput, String birthDate, boolean isCompetitiveInput, boolean isArrearsInput) {

        Boolean isActiveMember = activePassiveInput;
        Boolean isCompetitive = isCompetitiveInput;
        Boolean isArrears = isArrearsInput;
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

        User competitiveMemberToAdd = new CompetitiveMember(dbController.getIDForNewUser(), firstNames, lastName, isActiveMember, isCompetitive, birthDate, isArrears);
        dbController.addUserToDB(competitiveMemberToAdd, password);
    }

    public void CreateAndAddTreasurertoDB(String password, String firstNames, String lastNames) {

        User treasurerToAdd = new Treasurer(dbController.getIDForNewUser(), firstNames, lastNames);
        dbController.addUserToDB(treasurerToAdd, password);
    }

    public void editMember(
            String userIDInput,
            String firstNameInput,
            String lastNameInput,
            String isActiveMemberInput,
            String isCompetitiveInput,
            String isArrearsInput) {

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

    public String getUserInfo(int indexToEdit) {
        ArrayList<User> allUsers = dbController.getListOfAllUsers();
        User userForInfo = allUsers.get(indexToEdit);

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
    }

