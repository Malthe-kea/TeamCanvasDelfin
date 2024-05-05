package database.competition_style_DB.styleDB;

import database.Database;

import java.util.ArrayList;

public class CrawlDB extends Database implements StyleReturn {
    public CrawlDB() {
        super(StyleReturn.getFolderPath()+"CrawlDB.csv");
    }


    @Override
    protected ArrayList<String> getRowNamesFromEnumConfig() {
        return null;
    }
}
