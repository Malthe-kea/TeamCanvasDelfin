package domain_model.Processors;

import database.DBController;

public class TrainerProcessor implements Processor {

    DBController dbController;

    public TrainerProcessor(DBController dbController) {
        this.dbController = dbController;
    }
}
