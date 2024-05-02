package database;

import java.util.ArrayList;

public class CrawlDB extends Database {
    public CrawlDB(String filepath) {
        super(filepath);
    }

    @Override
    ArrayList<String> createRowNamesInDB() {
        return null;
    }
}
