package database.competition_style_DB;

import database.Database;
import database.rowNameEnum.CompetitionDBRowNames;
import database.rowNameEnum.CompetitiveMemberDBRowNames;
import database.rowNameEnum.DBRowNames;
import user_domain.competition.Competition;
import user_domain.competition.Style;

import java.io.File;
import java.util.ArrayList;

public class CompetitionDB extends Database {
    public CompetitionDB() {
        super("CompetitionDB"+File.separator+"CompetitionDB.csv");
    }

    @Override
    public DBRowNames[] getEnumRowNames() {
        return CompetitionDBRowNames.values();
    }

    public Competition getCompetitionFromID(int competitionIDToSearchFor, int userID) {
        int competitiveIDIndex = getIndexOfRowName(CompetitionDBRowNames.COMPETITIVE_ID);
        int locationIndex = getIndexOfRowName(CompetitionDBRowNames.LOCATION);
        int competitionID;
        String location;
        ArrayList<String[]> allRowsFromDB = getRows();
        StyleDB styleDB = new StyleDB();
        for(String[] singleRow : allRowsFromDB) {
            competitionID = Integer.parseInt(singleRow[competitiveIDIndex]);
            if(competitionID == competitionIDToSearchFor) {
                location = singleRow[locationIndex];

                ArrayList<Style> styles = styleDB.getUserCompetitionStyles(competitionID, userID);
                return new Competition(competitionID, location, styles);
            }
        }
        return null;
    }

    public Competition getCompetitionFromLocation(String locationToSearch, int userID) {
        int competitiveIDIndex = getIndexOfRowName(CompetitionDBRowNames.COMPETITIVE_ID);
        int locationIndex = getIndexOfRowName(CompetitionDBRowNames.LOCATION);
        int competitionID;
        String location;
        ArrayList<String[]> allRowsFromDB = getRows();
        StyleDB styleDB = new StyleDB();
        for(String[] singleRow : allRowsFromDB) {
            location = singleRow[locationIndex];
            if(locationToSearch.equalsIgnoreCase(location)) {
                competitionID = Integer.parseInt(singleRow[competitiveIDIndex]);

                ArrayList<Style> styles = styleDB.getUserCompetitionStyles(competitionID, userID);
                return new Competition(competitionID, location, styles);
            }
        }

        return null;

    }

    /*public int[] getStyleEntriesFromID(int id) {
        int indexToSearchBy = getIndexOfRowName(CompetitionDBRowNames.COMPETITIVE_ID);
        ArrayList<String[]> allRowsFromDB = getRows();

        for(String[] singleRow : allRowsFromDB) {
            int competitionID = Integer.parseInt(singleRow[indexToSearchBy]);
            if(competitionID == id) {
                String styleArrayAsString = singleRow[getIndexOfRowName(CompetitionDBRowNames.STYLES_ENTRIES)];
                styleArrayAsString = styleArrayAsString.substring(1,styleArrayAsString.length()-1);
                String[] styleIdArrayAsString = styleArrayAsString.split(",");
                int[] styleEntriesIDList = new int[styleIdArrayAsString.length];

                for (int i = 0; i < styleIdArrayAsString.length; i++) {
                    styleEntriesIDList[i] = Integer.parseInt(styleIdArrayAsString[i]);
                }

                return styleEntriesIDList;
            }
        }

        return null;
    }*/

    public boolean editCompInDB(Competition editedComp) {
        return false;
    }

}
