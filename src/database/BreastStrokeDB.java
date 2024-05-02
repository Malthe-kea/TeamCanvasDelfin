package database;

import java.util.ArrayList;

public class BreastStrokeDB extends Database {

    public BreastStrokeDB(String filepath) {
        super(filepath);
    }


    @Override
    ArrayList<String> getRowNamesFromEnumConfig() {
        return null;
    }
}
