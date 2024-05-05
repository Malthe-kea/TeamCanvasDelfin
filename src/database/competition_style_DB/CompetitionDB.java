package database.competition_style_DB;

import database.Database;
import database.rowNameEnum.CompetitionDBRowNames;
import user_domain.competition.Competition;

import java.util.ArrayList;

public class CompetitionDB extends Database {
    public CompetitionDB() {
        super("Competitive.csv");
    }

    @Override
    public ArrayList<String> getRowNamesFromEnumConfig() {
        ArrayList<String> rowNamesToCreate = new ArrayList<>();
        for(CompetitionDBRowNames competitiveDBRowName : CompetitionDBRowNames.values()) {
            rowNamesToCreate.add(competitiveDBRowName.getStringVariant());
        }
        return rowNamesToCreate;
    }

    public Competition getCompetitionFromID(int id) {
        int indexToSearchBy = getIndexOfRowName(CompetitionDBRowNames.COMPETITIVE_ID);
        ArrayList<String[]> allRowsFromDB = getRows();

        for(String[] singleRow : allRowsFromDB) {
            int competitionID = Integer.parseInt(singleRow[indexToSearchBy]);
            if(competitionID == id) {
                String location = singleRow[1];
                return new Competition(competitionID, location);
            }
        }

        return null;
    }

    public Competition getCompetitionFromLocation(String locationToSearch) {
        int indexToSearchBy = getIndexOfRowName(CompetitionDBRowNames.COMPETITIVE_ID);
        ArrayList<String[]> allRowsFromDB = getRows();

        for(String[] singleRow : allRowsFromDB) {
            String location = singleRow[1];
            if(locationToSearch.equalsIgnoreCase(location)) {
                int competitionID = Integer.parseInt(singleRow[0]);

                return new Competition(competitionID, location);
            }
        }

        return null;
    }

    public int[] getStyleEntriesFromID(int id) {
        int indexToSearchBy = getIndexOfRowName(CompetitionDBRowNames.COMPETITIVE_ID);
        ArrayList<String[]> allRowsFromDB = getRows();

        for(String[] singleRow : allRowsFromDB) {
            int competitionID = Integer.parseInt(singleRow[indexToSearchBy]);
            if(competitionID == id) {
                String styleArrayAsString = singleRow[2];
                String styleArrayWithoutBrackets = styleArrayAsString.substring(1,styleArrayAsString.length()-1);
                String[] styleIdArrayAsString = styleArrayWithoutBrackets.split(",");
                int[] styleEntriesIDList = new int[styleIdArrayAsString.length];

                for (int i = 0; i < styleIdArrayAsString.length; i++) {
                    styleEntriesIDList[i] = Integer.parseInt(styleIdArrayAsString[i]);
                }

                return styleEntriesIDList;
            }
        }

        return null;
    }

}
