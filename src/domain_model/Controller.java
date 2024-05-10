package domain_model;

import database.DBController;
import domain_model.Processors.*;
import user_domain.User;

import java.util.ArrayList;

public class Controller implements Processor {

    private final DBController DBCONTROLLER;

    private Processor processor;
    private UserInstance userInstance;

    public Controller() {
        DBCONTROLLER = new DBController();

    }

    public UIDisplayInfo getMainMenu() {
        return processor.getMainMenu();
    }

    public UserInstance getUserFromPassword(String password) {
        User userLoggingIn = DBCONTROLLER.getUserFromPassword(password);
        userInstance = DelfinUtil.checkUserInstance(userLoggingIn);

        switch (userInstance) {
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

        return userInstance;
    }


    public void CreateandAddMembertoDB(String password, String firstNames, String lastNames, boolean activePassiveInput, String birthDate, boolean isCompetitiveInput, boolean isArrearsInput) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.CreateandAddMembertoDB(password, firstNames, lastNames, activePassiveInput, birthDate, isCompetitiveInput, isArrearsInput);
    }

    public void CreateandAddTrainerToDB(String password, String firstNames, String lastNames, boolean isSeniorTrainer) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.CreateandAddTrainertoDB(password, firstNames, lastNames, isSeniorTrainer);
    }

    public void CreateandAddCompetitiveMembertoDB(String password, String firstNames, String lastNames, boolean isActiveMemberInput, String birthDate, boolean isCompetitiveInput, boolean isArrearsInput) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;
        superUserProcessor.CreateandAddCompetitiveMembertoDB(password, firstNames, lastNames, isActiveMemberInput, isCompetitiveInput, birthDate, isArrearsInput);
    }

    public void CreateandAddTreasurertoDB(String password, String firstNames, String lastNames) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.CreateAndAddTreasurertoDB(password, firstNames, lastNames);
    }

    public void editUserFromDB(int idToEdit, String firstName) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.editUserFromDB(idToEdit, firstName);
    }

    public void deleteUserFromDB(int indexInList) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.deleteUserFromDB(indexInList);
    }

    public ArrayList<String> getUserList() {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        return superUserProcessor.getUserList();
    }

    public String getUserInfo(int indexOfList) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        return superUserProcessor.getUserInfo(indexOfList);
    }


    @Override
    public UIDisplayInfo processMainMenuOption(int option) {
        return processor.processMainMenuOption(option);
    }
}
