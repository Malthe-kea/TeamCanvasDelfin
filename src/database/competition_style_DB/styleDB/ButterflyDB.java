package database.competition_style_DB.styleDB;

import database.Database;

import java.util.ArrayList;

public class ButterflyDB extends Database implements StyleReturn {

    public ButterflyDB() {
        super(StyleReturn.getFolderPath() + "ButterflyDB.csv");
    }


    @Override
    protected ArrayList<String> getRowNamesFromEnumConfig() {
        return null;
    }
}


