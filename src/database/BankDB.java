package database;

import database.Database;
import database.rowNameEnum.DBRowNames;

import java.util.ArrayList;

public class BankDB extends Database {

    public BankDB(String filepath) {
        super(filepath);
    }


    @Override
    public DBRowNames[] getEnumRowNames() {
        return new DBRowNames[0];
    }
}
