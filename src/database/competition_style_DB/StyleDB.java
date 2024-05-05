package database.competition_style_DB;

import database.Database;
import database.rowNameEnum.DBRowNames;
import database.rowNameEnum.StyleDBRowNames;
import user_domain.competition.Style;

import java.io.File;
import java.util.ArrayList;

public class StyleDB extends Database {

    public StyleDB() {
        super("CompetitionDB"+ File.separator+"StyleDB.csv");
    }

    @Override
    public DBRowNames[] getEnumRowNames() {
        return StyleDBRowNames.values();
    }


    public ArrayList<Style> getUserCompetitionStyles(int competitionID, int userID) {
        int competitionIDFromDB = getIndexOfRowName(StyleDBRowNames.COMPETITION_ID);
        int userIDFromDB = getIndexOfRowName(StyleDBRowNames.USER_ID);
        ArrayList<String[]> allRowsFromDB = super.getRows();
        ArrayList<Style> styles = new ArrayList<>();
        for(String[] singleRow : allRowsFromDB) {

            if(competitionIDFromDB == competitionID && userIDFromDB == userID) {
                int styleID = Integer.parseInt(singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_ID)]);
                String styleName = singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_NAME)];
                int stylePlacement = Integer.parseInt(singleRow[getIndexOfRowName(StyleDBRowNames.STYLE_PLACEMENT)]);
                long timeInSeconds = Long.parseLong(singleRow[getIndexOfRowName(StyleDBRowNames.TIME_IN_SECONDS)]);

                styles.add(new Style(styleID, styleName, stylePlacement, timeInSeconds));
            }
        }

        if(styles.isEmpty()) {
            return null;
        } else {
            return styles;
        }


    }
}
