package database;

import database.rowNameEnum.DBRowNames;
import user_domain.Member;
import user_domain.Trainer;
import user_domain.Treasurer;
import user_domain.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Database {

    private Scanner sc;
    private final File fileDB;


    public Database(String filepath) {
        //Sets file path for all databases
        String filePathRoot = "resources" + File.separator + "databases" + File.separator;
        //Instance of file created with specific filepath for csv file retrieved from individual DB classes.
        fileDB = new File(filePathRoot + filepath);
    }

    //Method that retrieves all rows from database EXCLUDING top row that defines the column names.
    public ArrayList<String[]> getRows() {
        //Retrieves scanner with DB csv file as source.
        sc = getScannerFromFile(fileDB);
        //Creates arraylist to be returned with all rows, where each row is represented as a String[] array.
        //Each index of the String[] array represents a column value in chronological order set by the database itself.
        ArrayList<String[]> listOfRows = new ArrayList<>();

        //Loops through each line of the database (csv file)
        while (sc.hasNextLine()) {
            //Takes each line and splits it by the delimiter ';', which represents a new value of a column.
            String[] singleRow = sc.nextLine().split(";");
            //Ads the row represented as a String[] array to the list of all rows.
            listOfRows.add(singleRow);
        }

        //Removes the first value, as the first value always represents the column names, and not actual values of the database.
        listOfRows.remove(0);
        return listOfRows;

    }

    //Method that retrieves the column names of a database (csv file)
    public String[] getRowNames() {
        //Retrieves scanner with DB csv file as source.
        sc = getScannerFromFile(fileDB);

        //Returns the first line of csv file, which is always the column names.
        return sc.nextLine().split(";");
    }

    public int getIndexOfRowName(DBRowNames dbrowNameEnum) {
        String[] rowNamesFromDB = getRowNames();
        int indexToSearchBy = -1;
        for (int i = 0; i < rowNamesFromDB.length; i++) {
            if(dbrowNameEnum.getStringVariant().equalsIgnoreCase(rowNamesFromDB[i])) {
                indexToSearchBy = i;
                break;
            }
        }
        return indexToSearchBy;
    }

    private Scanner getScannerFromFile(File filedb) {
        try {
            if(filedb.createNewFile()) {
                ArrayList<String> rowNamesToInsert = getRowNamesFromEnumConfig();
                String formattedRowNames = String.join(";",rowNamesToInsert)+";";
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

    abstract ArrayList<String> getRowNamesFromEnumConfig();

    public UserInstance checkUserInstance(User user) {
        if(user instanceof Member) {
            return UserInstance.MEMBER_OR_COMPETITIVE;
        }
        if(user instanceof Trainer) {
            return UserInstance.TRAINER;
        }
        if(user instanceof Treasurer) {
            return UserInstance.TREASURER;
        }
        return UserInstance.SUPER;

    }

    public boolean insertListToDB(ArrayList<String[]> allRowsToInsert) {
        allRowsToInsert.sort(new SortRowByIDComparator());
        try {
            PrintStream out = new PrintStream(fileDB);
            out.flush();
            String rowNameAsDBFormat = String.join(";",getRowNamesFromEnumConfig())+";";
            out.println(rowNameAsDBFormat);
            for (String[] singleRow : allRowsToInsert) {
                String rowAsDBFormat = String.join(";",singleRow)+";";
                out.println(rowAsDBFormat);
            }
        } catch(IOException IOE) {
            return false;
        }
        return true;
    }

}
