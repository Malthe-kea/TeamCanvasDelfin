package database;

import user_domain.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Database {

    private Scanner sc;
    private File fileDB;


    public Database(String filepath) {
        String filePathRoot = "resources" + File.separator + "databases" + File.separator;
        fileDB = new File(filePathRoot + filepath);
    }

    public ArrayList<String[]> getRows() {
        sc = getScannerFromFile(fileDB);
        ArrayList<String[]> listOfRows = new ArrayList<>();
        while (sc.hasNextLine()) {
            String[] singleRow = sc.nextLine().split(";");
            listOfRows.add(singleRow);
        }
        listOfRows.remove(0);
        return listOfRows;

    }

    public String[] getRowNames() {
        sc = getScannerFromFile(fileDB);
        return sc.nextLine().split(";");
    }

    public int getIndexOfRowName(String rowName) {
        String[] rowNamesFromDB = getRowNames();
        int indexToSearchBy = -1;
        for (int i = 0; i < rowNamesFromDB.length; i++) {
            if(rowName.equalsIgnoreCase(rowNamesFromDB[i])) {
                indexToSearchBy = i;
                break;
            }
        }
        return indexToSearchBy;
    }

    private Scanner getScannerFromFile(File filedb) {
        try {
            if(filedb.createNewFile()) {
                ArrayList<String> rowNamesToInsert = createRowNamesInDB();
                String formattedRowNames = String.join(";",rowNamesToInsert);
                PrintStream out = new PrintStream(filedb);
                out.println(formattedRowNames);
                return getScannerFromFile(filedb);
            } else {
                return new Scanner(fileDB, StandardCharsets.ISO_8859_1);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    abstract ArrayList<String> createRowNamesInDB();

}
