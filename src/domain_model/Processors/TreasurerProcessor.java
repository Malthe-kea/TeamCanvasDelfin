package domain_model.Processors;

import database.DBController;

public class TreasurerProcessor implements Processor {

    DBController dbController;

    public TreasurerProcessor(DBController dbController) {
        this.dbController = dbController;
    }
}
