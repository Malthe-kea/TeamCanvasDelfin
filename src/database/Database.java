package database;

import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.UserDBRowNames;

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
            //Adds the row represented as a String[] array to the list of all rows.
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

    //Method that gets the index of the wished column.
    public int getIndexOfRowName(DBRowNames dbrowNameEnum) {
        //Gets String[] array with the column names
        String[] rowNamesFromDB = getRowNames();
        int indexToSearchBy = -1;
        //Loops through column names.
        for (int i = 0; i < rowNamesFromDB.length; i++) {
            //Checks if the column name from the database is equal to the column name in the enum config.
            if(dbrowNameEnum.getStringVariant().equalsIgnoreCase(rowNamesFromDB[i])) {
                //Sets the index number where the wished column name is.
                indexToSearchBy = i;
                break;
            }
        }
        return indexToSearchBy;
    }

    //Method that returns a scanner from the file.
    private Scanner getScannerFromFile(File filedb) {
        //Try catch for IOException. Throws runtime of the stream exception.
        try {
            //Tries to create the file. If the file doesn't exist it returns true. If it exists it returns false.
            if(filedb.createNewFile()) {
                //Gets rowNames from the enum config.
                ArrayList<String> rowNamesToInsert = getRowNamesFromEnumConfig();
                //Formats the rowNames with delimiter ';' for insertion into DB
                String formattedRowNames = String.join(";",rowNamesToInsert)+";";
                //Creates printstream with fileDB.
                PrintStream out = new PrintStream(filedb);
                //Inserts rownames into the DB.
                out.println(formattedRowNames);
                //Calls recursive as file exists and filedb.createNewFile() should return false.
                return getScannerFromFile(filedb);
            } else {
                //Returns a scanner because the file exists.
                return new Scanner(fileDB, StandardCharsets.ISO_8859_1);
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

        //Method for getting the string formats of the column names, which is stores inside the enum class of each
        //database row name enums. See package rowNameEnum.
        public ArrayList<String> getRowNamesFromEnumConfig() {
            //Creates new arraylist for row Name String to be inserted into.
            ArrayList<String> rowNamesToCreate = new ArrayList<>();
            //Loops through each Enum value in the MemberDB Row Name Enum class.
            for (DBRowNames singleRowName : getEnumRowNames()) {
                //Adds the string variant of the enum, which correlates to the column name in the MemberDB csv file,
                //to the arraylist.
                rowNamesToCreate.add(singleRowName.getStringVariant());
            }
            return rowNamesToCreate;
        }

    public abstract DBRowNames[] getEnumRowNames();

    //Method that inserts list of rows into database
    public boolean insertListToDB(ArrayList<String[]> allRowsToInsert) {
        //Sorts the list in id in chronological order.
        allRowsToInsert.sort(new SortRowByIDComparator());
        try {
            //Creates new printstream with file.
            PrintStream out = new PrintStream(fileDB);
            //Removes all content of the file.
            out.flush();
            //Gets and inserts the rowName into the file.
            String rowNameAsDBFormat = String.join(";",getRowNamesFromEnumConfig());
            out.println(rowNameAsDBFormat);

            //Loops through the rows.
            for (String[] singleRow : allRowsToInsert) {
                //Formats each row with delimiter ';' for insertion in database.
                String rowAsDBFormat = String.join(";",singleRow);
                //Inserts the row into the csv file.
                out.println(rowAsDBFormat);
            }
        } catch(IOException IOE) {
            //Returns false as insertion was unsuccessful.
            return false;
        }
        //Returns true as insertion was successful.
        return true;
    }

    public int getIDForNewEntry(DBRowNames dbRowName, ArrayList<String[]> allRows) {
        allRows.sort(new SortRowByIDComparator());
        int newID = allRows.size()+1;
        for (int i = 1; i < allRows.size(); i++) {
            int previousID = Integer.parseInt(allRows.get(i - 1)[getIndexOfRowName(dbRowName)]);
            int currentID = Integer.parseInt(allRows.get(i)[getIndexOfRowName(dbRowName)]);
            if (currentID - previousID != 1) {
                newID = previousID + 1;
                break;
            }
        }

        return newID;
    }


}
