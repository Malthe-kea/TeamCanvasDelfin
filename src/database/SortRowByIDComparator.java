package database;

import java.util.Comparator;

public class SortRowByIDComparator implements Comparator<String[]> {
    //Comparator that compares id in the format received from the database.
    //This comparator can take in a single row, and as long as the id
    //always is the first value in the array, it can compare the id regardless of how the row is built.
    @Override
    public int compare(String[] o1, String[] o2) {
        int id1 = Integer.parseInt(o1[0]);
        int id2 = Integer.parseInt(o2[0]);
        return Integer.compare(id1,id2);
    }
}