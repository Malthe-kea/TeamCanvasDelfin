package domain_model.Processors;

import database.Database;
import database.DBController;
import domain_model.*;
import domain_model.userInterface.UserInterface;
import user_domain.*;

import java.util.ArrayList;
import java.util.List;
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

    public void CreateandAddMembertoDB(
            String password,
            String firstNames,
            String lastNames,
            boolean activePassiveInput,
            String birthDate,
            boolean isCompetitiveInput,
            boolean isArrearsInput) {

        Boolean isActiveMember = activePassiveInput;
        Boolean isCompetitive = isCompetitiveInput;
        Boolean isArrears = isArrearsInput;

        User memberToAdd = new Member(dbController.getIDForNewUser(), firstNames, lastNames, isActiveMember, isCompetitive, birthDate, isArrears);
        dbController.addUserToDB(memberToAdd, password);
    }

    public void CreateandAddTrainertoDB(
            String password,
            String firstNames,
            String lastNames,
            boolean isSeniorTrainer) {

        User trainerToAdd = new Trainer(dbController.getIDForNewUser(), firstNames, lastNames, isSeniorTrainer);
        dbController.addUserToDB(trainerToAdd, password);
    }

    public void CreateandAddCompetitiveMembertoDB(
            String password,
            String firstNames,
            String lastName,
            boolean isActiveMemberInput,
            boolean isCompetitiveInput,
            String birthDate,
            boolean isArrearsInput) {

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
            int indexOfUser,
            String firstNameInput,
            String lastNameInput,
            String isActiveMemberInput,
            String isCompetitiveInput,
            String isArrearsInput) {
        ArrayList<User> allUsers = dbController.getListOfAllUsers();

        Member userToEdit = (Member) allUsers.get(indexOfUser);

        if (firstNameInput != null) {
            userToEdit.setFirstName(firstNameInput);
        }

        if (lastNameInput != null) {
            userToEdit.setLastName(lastNameInput);
        }

        boolean previousCompetitive = userToEdit.isCompetitive();

        boolean isCompetitive = previousCompetitive;
        if (isCompetitiveInput != null) {
            isCompetitive = Boolean.parseBoolean(isCompetitiveInput);
            userToEdit.setActiveMember(Boolean.parseBoolean(isActiveMemberInput));
        }

        if (isArrearsInput != null) {
            userToEdit.setArrears(Boolean.parseBoolean(isArrearsInput));
        }

        if (isCompetitive != previousCompetitive) {
            User convertedMember;

            if (isCompetitive) {
                convertedMember = new CompetitiveMember(
                        userToEdit.getUserID(),
                        userToEdit.getFirstName(),
                        userToEdit.getLastName(),
                        userToEdit.isActiveMember(),
                        true,
                        userToEdit.getDateOfBirth(),
                        userToEdit.isArrears());
            } else {
                convertedMember = new Member(
                        userToEdit.getUserID(),
                        userToEdit.getFirstName(),
                        userToEdit.getLastName(),
                        userToEdit.isActiveMember(),
                        true,
                        userToEdit.getDateOfBirth(),
                        userToEdit.isArrears());
            }

            String password = dbController.getPasswordFromID(userToEdit.getUserID());
            dbController.removeUserFromDB(userToEdit.getUserID());
            dbController.addUserToDB(convertedMember, password);
        } else {
            dbController.editUserInDB(userToEdit);
        }

    }

    public void editTrainer(
            int indexOfUser,
            String firstNameInput,
            String lastNameInput,
            String isSeniorTrainerInput) {

        ArrayList<User> allUsers = dbController.getListOfAllUsers();

        Trainer userToEdit = (Trainer) allUsers.get(indexOfUser);

        if (firstNameInput != null) {
            userToEdit.setFirstName(firstNameInput);
        }
        if (lastNameInput != null) {
            userToEdit.setLastName(lastNameInput);
        }
        boolean isSeniorTrainer = Boolean.parseBoolean(isSeniorTrainerInput);
        userToEdit.setSeniorTrainer(isSeniorTrainer);

        dbController.editUserInDB(userToEdit);
    }

    public void editAdmin(int indexOfUser, String firstNameInput, String lastNameInput) {
        ArrayList<User> allUsers = dbController.getListOfAllUsers();
        User userToEdit = allUsers.get(indexOfUser);

        if (firstNameInput != null) {
            userToEdit.setFirstName(firstNameInput);
        }
        if (lastNameInput != null) {
            userToEdit.setLastName(lastNameInput);
        }
        dbController.editUserInDB(userToEdit);
    }


    public void deleteUserFromDB(int indexInList) {

        ArrayList<User> allUsers = dbController.getListOfAllUsers();
        User userToDelete = allUsers.get(indexInList);
        dbController.removeUserFromDB(userToDelete);

    }

    public ArrayList<String> getUserInfo(int indexToShow) {
        ArrayList<User> allUsers = dbController.getListOfAllUsers();
        User userForInfo = allUsers.get(indexToShow);
        ArrayList<String> userInfo;
        String userString = userForInfo.toString();
        userInfo = new ArrayList<>(List.of(userString.split("\n")));
        //userInfo.remove(0);

        return userInfo;
    }

    public ArrayList<String> getUserInfoForEdit(int indexToShow) {
        ArrayList<User> allUsers = dbController.getListOfAllUsers();
        User userForInfo = allUsers.get(indexToShow);
        ArrayList<String> userInfo =new ArrayList<>();
        String firstName = "Fornavn: " + userForInfo.getFirstName();
        String lastName = "Efternavn: " + userForInfo.getLastName();
        userInfo.add(firstName);
        userInfo.add(lastName);

        switch (DelfinUtil.checkUserInstance(userForInfo)) {
            case MEMBER -> {
                String activePassive = "Aktiv/Passiv: " + ( ((Member) userForInfo).isActiveMember() ? "Aktiv" : "Passiv");
                userInfo.add(activePassive);
                String competitiveRecreational = "Aktivitetsstatus: " + (((Member) userForInfo).isCompetitive() ? "Konkurrence" : "Motonist");
                userInfo.add(competitiveRecreational);
                String arrears = "Restance: " + (((Member) userForInfo).isArrears() ? "Ja" : "Nej");
                userInfo.add(arrears);
            }
            case TRAINER -> {
                String SeniorTrainer = "Trænertype: " + (((Trainer) userForInfo).isSeniorTrainer() ? "Senior" : "Junior");
                userInfo.add(SeniorTrainer);
            }
        }

        return userInfo;
    }

    public UserInstance getUserType(int indexOfUser) {
        ArrayList<User> allUsers = dbController.getListOfAllUsers();
        User user = allUsers.get(indexOfUser);
        return DelfinUtil.checkUserInstance(user);
    }

}
