package database.competition_style_DB.styleDB;

import database.Database;

import java.util.ArrayList;

public class BackStrokeDB extends Database implements StyleReturn {
    public BackStrokeDB() {
        super(StyleReturn.getFolderPath()+"BackStrokeDB.csv");
    }

    @Override
    protected ArrayList<String> getRowNamesFromEnumConfig() {
        return null;
    }
}
