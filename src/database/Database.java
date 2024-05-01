package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Database {

    private Scanner sc;
    private File fileDB;


    public Database(String filepath) {
        fileDB = new File(filepath);
    }

    public ArrayList<String[]> getRows() {
        //DETTE FOREGÅR I ABSTRACT DATABASE
        try {
            sc = new Scanner(fileDB);
        } catch(FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        }
        ArrayList<String[]> listOfRows = new ArrayList<>();
        boolean firstIteration = true;
        while(sc.hasNextLine()) {
            if(firstIteration) {
                firstIteration = false;
            } else {
                String[] singleRow = sc.nextLine().split(";");
                listOfRows.add(singleRow);
            }
        }

        return listOfRows;

    }

    public String[] getRowNames() {
        try {
            sc = new Scanner(fileDB);
        } catch(FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        }

        return sc.nextLine().split(";");
    }

        /*//TODO
        // 1. find bedre variabelnavne.
        //DETTE FOREG?R I UserDB
        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                System.out.println(rowNames[i] + ": " + row[i]);
            }
        }*/


    abstract void arrangeData();

}
