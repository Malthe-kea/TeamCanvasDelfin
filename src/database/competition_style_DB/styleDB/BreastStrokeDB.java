package database.competition_style_DB.styleDB;

import database.Database;

import java.util.ArrayList;

public class BreastStrokeDB extends Database implements StyleReturn {

    public BreastStrokeDB() {
        super(StyleReturn.getFolderPath()+"BreastStrokeDB.csv");
    }

    @Override
    protected ArrayList<String> getRowNamesFromEnumConfig() {
        return null;
    }
}
