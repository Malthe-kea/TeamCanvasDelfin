package database;

import java.util.ArrayList;

public class ButterflyDB extends Database {

    public ButterflyDB(String filepath) {
        super(filepath);
    }

    @Override
    ArrayList<String> createRowNamesInDB() {
        return null;
    }
}
