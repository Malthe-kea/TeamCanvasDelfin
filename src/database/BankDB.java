package database;

import java.util.ArrayList;

public class BankDB extends Database {

    public BankDB(String filepath) {
        super(filepath);
    }


    @Override
    ArrayList<String> getRowNamesFromEnumConfig() {
        return null;
    }
}
