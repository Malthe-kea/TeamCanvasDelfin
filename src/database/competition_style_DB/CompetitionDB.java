package database.competition_style_DB;

import database.Database;
import database.rowNameEnum.CompetitionDBRowNames;
import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.MemberDBRowNames;
import user_domain.competition.Competition;
import user_domain.competition.Style;

import java.io.File;
import java.util.ArrayList;

public class CompetitionDB extends Database {
    public CompetitionDB() {
        super("CompetitionDB" + File.separator + "CompetitionDB.csv");
    }

    @Override
    public DBRowNames[] getEnumRowNames() {
        return CompetitionDBRowNames.values();
    }

    public ArrayList<Competition> getListOfCompetitions(int userID) {
        ArrayList<Competition> competitionList = new ArrayList<>();
        ArrayList<String[]> allRowsFromDB = getRows();
        StyleDB styleDB = new StyleDB();
        for (String[] singleRow : allRowsFromDB) {
            int competitionID = Integer.parseInt(singleRow[getIndexOfRowName(CompetitionDBRowNames.COMPETITIVE_ID)]);
            String location = singleRow[getIndexOfRowName(CompetitionDBRowNames.LOCATION)];
            String date = singleRow[getIndexOfRowName(CompetitionDBRowNames.DATE)];
            ArrayList<Style> styles;

            if(userID == -1) {
                styles = new ArrayList<>();
            } else {
                styles = styleDB.getUserCompetitionStyles(competitionID, userID);
            }
            competitionList.add(new Competition(competitionID, location, date, styles));

        }
        return competitionList;
    }

    public ArrayList<Competition> getListOfCompetitions() {
        return getListOfCompetitions(-1);
    }

    public Competition getCompetitionFromID(int competitionIDToSearchFor) {
        return getCompetitionFromID(competitionIDToSearchFor, -1);
    }

    public Competition getCompetitionFromID(int competitionIDToSearchFor, int userID) {
        int competitiveIDIndex = getIndexOfRowName(CompetitionDBRowNames.COMPETITIVE_ID);
        int locationIndex = getIndexOfRowName(CompetitionDBRowNames.LOCATION);
        int dateIndex = getIndexOfRowName(CompetitionDBRowNames.DATE);
        int competitionID;
        String location;
        String date;
        ArrayList<String[]> allRowsFromDB = getRows();
        StyleDB styleDB = new StyleDB();
        for (String[] singleRow : allRowsFromDB) {
            competitionID = Integer.parseInt(singleRow[competitiveIDIndex]);
            if (competitionID == competitionIDToSearchFor) {
                location = singleRow[locationIndex];
                date = singleRow[dateIndex];
                ArrayList<Style> styles;
                if(userID == -1) {
                    styles = new ArrayList<>();
                } else {
                    styles = styleDB.getUserCompetitionStyles(competitionID, userID);
                }
                return new Competition(competitionID, location, date , styles);
            }
        }
        return null;
    }

    public boolean doesCompetitionLocationExist(String location) {
        ArrayList<String[]> allRows = getRows();

        for (String[] singleRow : allRows) {
            String compLocationFromDB = singleRow[getIndexOfRowName(CompetitionDBRowNames.LOCATION)];
            if (compLocationFromDB.equalsIgnoreCase(location)) {
                return true;
            }
        }
        return false;
    }

    public Competition getCompetitionFromLocation(String locationToSearch) {
        return getCompetitionFromLocation(locationToSearch, -1);
    }

    public Competition getCompetitionFromLocation(String locationToSearch, int userID) {
        int competitiveIDIndex = getIndexOfRowName(CompetitionDBRowNames.COMPETITIVE_ID);
        int locationIndex = getIndexOfRowName(CompetitionDBRowNames.LOCATION);
        int dateIndex = getIndexOfRowName(CompetitionDBRowNames.DATE);
        int competitionID;
        String location;
        String date;
        ArrayList<String[]> allRowsFromDB = getRows();
        StyleDB styleDB = new StyleDB();
        for (String[] singleRow : allRowsFromDB) {
            location = singleRow[locationIndex];
            date = singleRow[dateIndex];
            if (locationToSearch.equalsIgnoreCase(location)) {
                competitionID = Integer.parseInt(singleRow[competitiveIDIndex]);
                ArrayList<Style> styles;

                if(userID == -1) {
                    styles = new ArrayList<>();
                } else {
                    styles = styleDB.getUserCompetitionStyles(competitionID, userID);
                }
                return new Competition(competitionID, location, date, styles);
            }
        }

        return null;

    }


    public boolean editCompInDB(Competition editedComp) {
        ArrayList<Style> styleListToEdit = editedComp.getStyleList();

        if (!styleListToEdit.isEmpty()) {
            StyleDB styleDB = new StyleDB();
            for (Style styleToEdit : styleListToEdit) {
                styleDB.editStyleInDB(styleToEdit);
            }
        }

        ArrayList<String[]> allRows = getRows();

        for (String[] singleRow : allRows) {
            int compIDFromDB = Integer.parseInt(singleRow[getIndexOfRowName(CompetitionDBRowNames.COMPETITIVE_ID)]);
            if (compIDFromDB == editedComp.getID()) {
                singleRow[getIndexOfRowName(CompetitionDBRowNames.LOCATION)] = editedComp.getLocation();
                return super.insertListToDB(allRows);
            }

        }
        return false;
    }

    public int getIDForNewCompetition() {
        return getIDForNewEntry(CompetitionDBRowNames.COMPETITIVE_ID, super.getRows());
    }

    public boolean addCompetitionToDB(Competition comp) {

        ArrayList<Style> styleListToAdd = comp.getStyleList();

        if (!styleListToAdd.isEmpty()) {
            StyleDB styleDB = new StyleDB();
            for (Style styleToAdd : styleListToAdd) {
                styleDB.addStyleToDB(styleToAdd);
            }
        }

        if (comp.getID() != getIDForNewCompetition() || doesCompetitionLocationExist(comp.getLocation())) {
            return false;
        } else {
            ArrayList<String[]> allRows = getRows();
            String[] newRow = new String[CompetitionDBRowNames.values().length];
            newRow[getIndexOfRowName(CompetitionDBRowNames.COMPETITIVE_ID)] = String.valueOf(comp.getID());
            newRow[getIndexOfRowName(CompetitionDBRowNames.LOCATION)] = comp.getLocation();
            allRows.add(newRow);
            return super.insertListToDB(allRows);
        }




    }

    public boolean removeCompFromDB(int id) {
        ArrayList<String[]> allRows = getRows();

        for (String[] singleRow : allRows) {
            int compIDFromDB = Integer.parseInt(singleRow[getIndexOfRowName(CompetitionDBRowNames.COMPETITIVE_ID)]);
            if (compIDFromDB == id) {
                allRows.remove(singleRow);
                return super.insertListToDB(allRows);
            }
        }
        return false;
    }

    public boolean removeCompFromDB(Competition comp) {
        return removeCompFromDB(comp.getID());
    }

}
