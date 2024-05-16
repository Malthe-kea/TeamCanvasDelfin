package database.competition_style_DB;

import database.Database;
import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.StyleDBRowNames;
import user_domain.competition.Style;
import user_domain.competition.StyleCategories;

import java.io.File;
import java.util.ArrayList;

public class StyleDB extends Database {

    public StyleDB() {
        super("CompetitionDB" + File.separator + "StyleDB.csv");
    }

    @Override
    public DBRowNames[] getEnumRowNames() {
        return StyleDBRowNames.values();
    }

    public Style getStyleFromID(int styleIDToEdit) {
        ArrayList<String[]> allRows = getRows();

        for (String[] singleRow : allRows) {
            int styleID = Integer.parseInt(singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_ID)]);
            if (styleIDToEdit == styleID) {
                StyleCategories styleCategory = StyleCategories.valueOf(singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_CATEGORY)]);
                int stylePlacement = Integer.parseInt(singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_PLACEMENT)]);
                long timeInSeconds = Long.parseLong(singleRow[getIndexOfRowName(StyleDBRowNames.TIME_IN_SECONDS)]);
                int userID = Integer.parseInt(singleRow[getIndexOfRowName(StyleDBRowNames.USER_ID)]);
                int competitionID = Integer.parseInt(singleRow[getIndexOfRowName(StyleDBRowNames.COMPETITION_ID)]);

                return new Style(styleID, userID, competitionID, styleCategory, stylePlacement, timeInSeconds);
            }
        }

        return null;
    }

    public boolean editStyleInDB(Style styleToEdit) {
        int styleID = styleToEdit.getID();
        ArrayList<String[]> allRows = getRows();
        for (String[] singleRow : allRows) {
            int styleIDFromDB = Integer.parseInt(singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_ID)]);
            if (styleIDFromDB == styleID) {
                singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_CATEGORY)] = styleToEdit.getStyleCategory().name();
                singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_PLACEMENT)] = String.valueOf(styleToEdit.getPlacement());
                singleRow[getIndexOfRowName(StyleDBRowNames.TIME_IN_SECONDS)] = String.valueOf(styleToEdit.getTime().getDurationInSeconds());
                singleRow[getIndexOfRowName(StyleDBRowNames.USER_ID)] = String.valueOf(styleToEdit.getUserID());
                singleRow[getIndexOfRowName(StyleDBRowNames.COMPETITION_ID)] = String.valueOf(styleToEdit.getCompetitionID());
                return super.insertListToDB(allRows);

            }
        }
        return false;
    }


    public ArrayList<Style> getUserCompetitionStyles(int competitionID, int userID) {
        int competitionIDIndex = getIndexOfRowName(StyleDBRowNames.COMPETITION_ID);
        int userIDIndex = getIndexOfRowName(StyleDBRowNames.USER_ID);
        ArrayList<String[]> allRowsFromDB = super.getRows();
        ArrayList<Style> styles = new ArrayList<>();
        for (String[] singleRow : allRowsFromDB) {
            int userIDFromDB = Integer.parseInt(singleRow[userIDIndex]);
            int competitionIDFromDB = Integer.parseInt(singleRow[competitionIDIndex]);
            if (competitionIDFromDB == competitionID && userIDFromDB == userID) {
                int styleID = Integer.parseInt(singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_ID)]);
                StyleCategories styleCategory = StyleCategories.valueOf(singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_CATEGORY)]);
                int stylePlacement = Integer.parseInt(singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_PLACEMENT)]);
                long timeInSeconds = Long.parseLong(singleRow[getIndexOfRowName(StyleDBRowNames.TIME_IN_SECONDS)]);

                styles.add(new Style(styleID, userIDFromDB, competitionID, styleCategory, stylePlacement, timeInSeconds));
            }
        }

        if (styles.isEmpty()) {
            return null;
        } else {
            return styles;
        }
    }

    public ArrayList<String> getStyleList() {
        ArrayList<String[]> allRowsFromDB = super.getRows();
        ArrayList<String> styles = new ArrayList<>();
        for (String[] singleRow : allRowsFromDB) {

            String styleName = singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_CATEGORY)];
            int stylePlacement = Integer.parseInt(singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_PLACEMENT)]);
            long timeInSeconds = Long.parseLong(singleRow[getIndexOfRowName(StyleDBRowNames.TIME_IN_SECONDS)]);
            int userIDIndex = getIndexOfRowName(StyleDBRowNames.USER_ID);

            styles.add(styleName);
            styles.add(String.valueOf(stylePlacement));
            styles.add(String.valueOf(timeInSeconds));
            styles.add(String.valueOf(userIDIndex));
        }

        if (styles.isEmpty()) {
            return null;
        } else {
            return styles;
        }
    }


    public int getIDForNewStyle() {
        return getIDForNewEntry(StyleDBRowNames.STYLE_ID, super.getRows());
    }

    public boolean addStyleToDB(Style styleToAdd) {
        int styleID = styleToAdd.getID();
        if (styleID != getIDForNewStyle()) {
            return false;
        } else {
            ArrayList<String[]> allRows = getRows();
            String[] newRow = new String[StyleDBRowNames.values().length];
            newRow[getIndexOfRowName(StyleDBRowNames.STYLE_ID)] = String.valueOf(styleToAdd.getID());
            newRow[getIndexOfRowName(StyleDBRowNames.STYLE_CATEGORY)] = styleToAdd.getStyleCategory().name();
            newRow[getIndexOfRowName(StyleDBRowNames.STYLE_PLACEMENT)] = String.valueOf(styleToAdd.getPlacement());
            newRow[getIndexOfRowName(StyleDBRowNames.TIME_IN_SECONDS)] = String.valueOf(styleToAdd.getTime().getDurationInSeconds());
            newRow[getIndexOfRowName(StyleDBRowNames.USER_ID)] = String.valueOf(styleToAdd.getUserID());
            newRow[getIndexOfRowName(StyleDBRowNames.COMPETITION_ID)] = String.valueOf(styleToAdd.getCompetitionID());
            allRows.add(newRow);
            return super.insertListToDB(allRows);
        }

    }

    public boolean removeStyleFromDB(int id) {
        int styleID = id;
        ArrayList<String[]> allRows = getRows();
        for (String[] singleRow : allRows) {
            int styleIDFromDB = Integer.parseInt(singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_ID)]);
            if (styleIDFromDB == styleID) {
                allRows.remove(singleRow);
                return super.insertListToDB(allRows);
            }
        }
        return false;
    }

    public boolean removeStyleFromDB(Style style) {
        return removeStyleFromDB(style.getID());
    }
}
