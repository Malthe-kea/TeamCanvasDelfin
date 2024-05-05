import database.competition_style_DB.CompetitionDB;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        CompetitionDB db = new CompetitionDB();
        System.out.println(Arrays.toString(db.getStyleEntriesFromID(1)));

    }
}