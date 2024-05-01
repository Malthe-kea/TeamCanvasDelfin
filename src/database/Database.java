package database;

import user_domain.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
            sc = new Scanner(fileDB,StandardCharsets.ISO_8859_1);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        ArrayList<String[]> listOfRows = new ArrayList<>();
        while(sc.hasNextLine()) {
            String[] singleRow = sc.nextLine().split(";");
            listOfRows.add(singleRow);
        }
        listOfRows.remove(0);
        return listOfRows;

    }

    public String[] getRowNames() {
        try {
            sc = new Scanner(fileDB,StandardCharsets.ISO_8859_1);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

        return sc.nextLine().split(";");
    }

}
