package database;

import java.util.ArrayList;

public class BackStrokeDB extends Database {
    public BackStrokeDB(String filepath) {
        super(filepath);
    }

    @Override
    ArrayList<String> getRowNamesFromEnumConfig() {
        return null;
    }
}
