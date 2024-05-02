package database;

import java.util.ArrayList;

public class CompetitiveDB extends Database {
    public CompetitiveDB(String filepath) {
        super(filepath);
    }


    @Override
    ArrayList<String> createRowNamesInDB() {
        return null;
    }
}
