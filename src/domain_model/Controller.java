package domain_model;

import database.DBController;
import domain_model.Processors.*;
import user_domain.User;

public class Controller {

    private final DBController DBCONTROLLER;

    private Processor processor;

    public Controller() {
        DBCONTROLLER = new DBController();

    }

    public void getProcessorFromUserPassword(String password) {
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


    }

    public Processor getProcessor() {
        return processor;
    }

    public DBController getDBController() {
        return DBCONTROLLER;
    }
}
