package domain_model;

import database.DBController;
import domain_model.Processors.*;
import user_domain.Trainer;
import user_domain.User;
import user_domain.competition.StyleCategories;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Processor {

    private final DBController DBCONTROLLER;

    private SuperUserProcessor superUserProcessor;
    private MemberProcessor memberProcessor;
    private TrainerProcessor trainerProcessor;
    private TreasurerProcessor treasurerProcessor;

    public Controller() {
        DBCONTROLLER = new DBController();
        superUserProcessor = new SuperUserProcessor(DBCONTROLLER);
        memberProcessor = new MemberProcessor(DBCONTROLLER);
        trainerProcessor = new TrainerProcessor(DBCONTROLLER);
        treasurerProcessor = new TreasurerProcessor(DBCONTROLLER);

    }

    public User getUserFromPassword(String password) {
        User userLoggingIn = DBCONTROLLER.getUserFromPassword(password);

        return userLoggingIn;
    }


    public void createandAddMembertoDB(
            String password,
            String firstNames,
            String lastNames,
            boolean activePassiveInput,
            String birthDate,
            boolean isCompetitiveInput,
            boolean isArrearsInput) {

        superUserProcessor.CreateandAddMembertoDB(password, firstNames, lastNames, activePassiveInput, birthDate, isCompetitiveInput, isArrearsInput);
    }

    public void createandAddTrainerToDB(
            String password,
            String firstNames,
            String lastNames,
            boolean isSeniorTrainer) {
        superUserProcessor.CreateandAddTrainertoDB(password, firstNames, lastNames, isSeniorTrainer);
    }

    public void createandAddCompetitiveMembertoDB(
            String password,
            String firstNames,
            String lastNames,
            boolean isActiveMemberInput,
            String birthDate,
            boolean isCompetitiveInput,
            boolean isArrearsInput) {

        superUserProcessor.CreateandAddCompetitiveMembertoDB(password, firstNames, lastNames, isActiveMemberInput, isCompetitiveInput, birthDate, isArrearsInput);
    }

    public void createandAddTreasurertoDB(String password, String firstNames, String lastNames) {
        superUserProcessor.CreateAndAddTreasurertoDB(password, firstNames, lastNames);
    }

    public void editMember(
            int indexOfUser,
            String firstNameInput,
            String lastNameInput,
            String isActiveMemberInput,
            String isCompetitiveInput,
            String isArrearsInput) {
        superUserProcessor.editMember(indexOfUser, firstNameInput, lastNameInput, isActiveMemberInput, isCompetitiveInput, isArrearsInput);
    }

    public void editTrainer(
            int indexOfUser,
            String firstNameInput,
            String lastNameInput,
            String isSeniorTrainerInput) {

        superUserProcessor.editTrainer(indexOfUser, firstNameInput, lastNameInput, isSeniorTrainerInput);
    }

    public void editAdmin(int indexOfUser, String firstNameInput, String lastNameInput) {
        superUserProcessor.editAdmin(indexOfUser, firstNameInput, lastNameInput);
    }

    public void deleteUserFromDB(int indexInList) {
        superUserProcessor.deleteUserFromDB(indexInList);
    }

    public ArrayList<String> getUserIDAndNameList() {
        return superUserProcessor.getUserIDAndNameList();
    }

    public ArrayList<String> getUserInfo(int indexOfList) {
        return superUserProcessor.getUserInfo(indexOfList);
    }

    public UserInstance getUserType(int indexOfUser) {
        return superUserProcessor.getUserType(indexOfUser);
    }

    public String getExpectedIncome() {
        return treasurerProcessor.getExpectedIncome();
    }

    public ArrayList<String> getMembersInArrears() {
        return treasurerProcessor.getMembersInArrears();
    }

    public ArrayList<String> getListOfTeams(Trainer trainer) {
        return trainerProcessor.getListOfTeams(trainer);
    }

    public ArrayList<String> getUserInfoForEdit(int indexToShow) {
        return superUserProcessor.getUserInfoForEdit(indexToShow);
    }

    public ArrayList<String> getTopFive(StyleCategories styleCategory) {
        return trainerProcessor.getTopFive(styleCategory);
    }

    public ArrayList<String> getCompetitionList() {
        return trainerProcessor.getCompetitionList();
    }

    public String createCompetition(String location, String date) {
        return trainerProcessor.createCompetition(location, date);
    }

    public void addStyleToMember(int indexOfMember, int indexOfCompetetion, StyleCategories styleType,
                                 long seconds, int placement) {
        trainerProcessor.addStyleToMember(indexOfMember, indexOfCompetetion, styleType, seconds, placement);
    }

    public ArrayList<String> getUIButtonCompetitionList() {
        return trainerProcessor.getUIButtonCompetitionList();
    }

    public ArrayList<String> getListOfCompetitiveMembersForUIButton() {
        return trainerProcessor.getListOfCompetitiveMembersForUIButton();
    }
}
