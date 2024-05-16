package domain_model;

import database.DBController;
import domain_model.Processors.*;
import user_domain.Trainer;
import user_domain.User;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Processor {

    private final DBController DBCONTROLLER;

    private Processor processor;
    private UserInstance userInstance;

    public Controller() {
        DBCONTROLLER = new DBController();

    }
    public User getUserFromPassword(String password) {
        User userLoggingIn = DBCONTROLLER.getUserFromPassword(password);

        switch (DelfinUtil.checkUserInstance(userLoggingIn)) {
            case MEMBER, COMPETITIVE -> {
                processor = new MemberProcessor(DBCONTROLLER);
            }
            case TRAINER -> {
                processor = new TrainerProcessor(DBCONTROLLER);
            }
            case SUPER -> {
                processor = new SuperUserProcessor(DBCONTROLLER);
            }
            case TREASURER -> {
                processor = new TreasurerProcessor(DBCONTROLLER);
            }
        }

        return userLoggingIn;
    }


    public void createandAddMembertoDB(String password, String firstNames, String lastNames, boolean activePassiveInput, String birthDate, boolean isCompetitiveInput, boolean isArrearsInput) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.CreateandAddMembertoDB(password, firstNames, lastNames, activePassiveInput, birthDate, isCompetitiveInput, isArrearsInput);
    }

    public void createandAddTrainerToDB(String password, String firstNames, String lastNames, boolean isSeniorTrainer) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.CreateandAddTrainertoDB(password, firstNames, lastNames, isSeniorTrainer);
    }

    public void createandAddCompetitiveMembertoDB(String password, String firstNames, String lastNames, boolean isActiveMemberInput, String birthDate, boolean isCompetitiveInput, boolean isArrearsInput) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;
        superUserProcessor.CreateandAddCompetitiveMembertoDB(password, firstNames, lastNames, isActiveMemberInput, isCompetitiveInput, birthDate, isArrearsInput);
    }

    public void createandAddTreasurertoDB(String password, String firstNames, String lastNames) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.CreateAndAddTreasurertoDB(password, firstNames, lastNames);
    }

    public void editMember(
            int indexOfUser,
            String firstNameInput,
            String lastNameInput,
            String isActiveMemberInput,
            String isCompetitiveInput,
            String isArrearsInput)
    {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.editMember(indexOfUser, firstNameInput, lastNameInput, isActiveMemberInput, isCompetitiveInput, isArrearsInput);
    }

    public void editTrainer(int indexOfUser, String firstNameInput, String lastNameInput, String isSeniorTrainerInput) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.editTrainer(indexOfUser,firstNameInput,lastNameInput,isSeniorTrainerInput);
    }

    public void editAdmin(int indexOfUser, String firstNameInput, String lastNameInput) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.editAdmin(indexOfUser, firstNameInput, lastNameInput);
    }

    public void deleteUserFromDB(int indexInList) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.deleteUserFromDB(indexInList);
    }

    public ArrayList<String> getUserIDAndNameList() {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        return superUserProcessor.getUserIDAndNameList();
    }

    public ArrayList<String> getUserInfo(int indexOfList) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        return superUserProcessor.getUserInfo(indexOfList);
    }

    public UserInstance getUserType(int indexOfUser) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;
        return superUserProcessor.getUserType(indexOfUser);
    }

    public String getExpectedIncome() {
        TreasurerProcessor treasurerProcessor = (TreasurerProcessor) processor;
        return treasurerProcessor.getExpectedIncome();
    }

    public ArrayList<String> getMembersInArrears() {
        TreasurerProcessor treasurerProcessor = (TreasurerProcessor) processor;
        return treasurerProcessor.getMembersInArrears();
    }
    public ArrayList<String> getListOfTeams(Trainer trainer){
        TrainerProcessor trainerProcessor = (TrainerProcessor) processor;
        return trainerProcessor.getListOfTeams(trainer);
    }

    public ArrayList<String> getCompetitionList(){
        TrainerProcessor trainerProcessor = (TrainerProcessor) processor;
        return trainerProcessor.getCompetitionList();
    }

    public void createCompetition(String location, String date){
        TrainerProcessor trainerProcessor = (TrainerProcessor) processor;
        trainerProcessor.createCompetition(location,date);
    }


}
