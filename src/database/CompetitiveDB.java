package database;

import database.rowNameEnum.CompetitiveDBRowNames;
import database.rowNameEnum.TrainerDBRowNames;
import user_domain.User;
import user_domain.competition.Competition;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CompetitiveDB extends Database {
    public CompetitiveDB() {
        super("Competitive.csv");
    }

    @Override
    public ArrayList<String> getRowNamesFromEnumConfig() {
        ArrayList<String> rowNamesToCreate = new ArrayList<>();
        for(CompetitiveDBRowNames competitiveDBRowName : CompetitiveDBRowNames.values()) {
            rowNamesToCreate.add(competitiveDBRowName.getStringVariant());
        }
        return rowNamesToCreate;
    }

    public Competition getCompetitionFromID(int id) {
        int indexToSearchBy = getIndexOfRowName(CompetitiveDBRowNames.COMPETITIVE_ID);
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
        int indexToSearchBy = getIndexOfRowName(CompetitiveDBRowNames.COMPETITIVE_ID);
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

    public int[] getParticipantsIDFromID(int id) {
        int indexToSearchBy = getIndexOfRowName(CompetitiveDBRowNames.COMPETITIVE_ID);
        ArrayList<String[]> allRowsFromDB = getRows();

        for(String[] singleRow : allRowsFromDB) {
            int competitionID = Integer.parseInt(singleRow[indexToSearchBy]);
            if(competitionID == id) {
                String userArrayAsString = singleRow[2];
                String userArrayWithoutBrackets = userArrayAsString.substring(1,userArrayAsString.length()-1);
                String[] userIdArrayAsString = userArrayWithoutBrackets.split(",");
                int[] participantsIDList = new int[userIdArrayAsString.length];

                for (int i = 0; i < userIdArrayAsString.length; i++) {
                    participantsIDList[i] = Integer.parseInt(userIdArrayAsString[i]);
                }

                return participantsIDList;
            }
        }

        return null;
    }

}
