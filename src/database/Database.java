package database;

import database.rowNameEnum.DBRowNames;
import user_domain.Member;
import user_domain.Trainer;
import user_domain.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Database {

    private Scanner sc;
    private final File fileDB;


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
        return UserInstance.SUPER_OR_TREASURER;

    }

    public boolean insertListToDB(ArrayList<String[]> allRowsToInsert) {
        allRowsToInsert.sort(new sortRowByIDComparator());
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
