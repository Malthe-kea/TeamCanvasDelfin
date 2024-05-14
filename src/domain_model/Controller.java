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
            String userIDInput,
            String firstNameInput,
            String lastNameInput,
            String isActiveMemberInput,
            String isCompetitiveInput,
            String isArrearsInput)
    {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.editMember(userIDInput, firstNameInput, lastNameInput, isActiveMemberInput, isCompetitiveInput, isArrearsInput);
    }

    public void editTrainer(String userIDInput, String firstNameInput, String lastNameInput, String isSeniorTrainerInput) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.editTrainer(userIDInput,firstNameInput,lastNameInput,isSeniorTrainerInput);
    }

    public void editAdmin() {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.editAdmin();
    }

    public void deleteUserFromDB(int indexInList) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        superUserProcessor.deleteUserFromDB(indexInList);
    }

    public ArrayList<String> getUserList() {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        return superUserProcessor.getUserIDAndNameList();
    }

    public String getUserInfo(int indexOfList) {
        SuperUserProcessor superUserProcessor = (SuperUserProcessor) processor;

        return superUserProcessor.getUserInfo(indexOfList);
    }


}
