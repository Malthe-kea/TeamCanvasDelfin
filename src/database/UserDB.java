package database;

import java.io.File;
import java.util.ArrayList;

public class UserDB extends Database {
    public UserDB() {
        super("resources"+File.separator+"databases"+File.separator+"userDB.csv");
    }

    

    /*public void arrangeData() {
        ArrayList<String[]> rows = getRows();
        String[] rowNames = getRowNames();

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                System.out.println(rowNames[i] + ": " + row[i]);
            }
        }
    }*/
}
