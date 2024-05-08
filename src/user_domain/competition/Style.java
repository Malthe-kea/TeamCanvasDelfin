package user_domain.competition;

import java.text.SimpleDateFormat;

public class Style {
    private int id;
    private int userID;
    private int competitionID;
    private StyleCategories styleCategory;
    private int placement;
    private final TimeDisplay time;

    public Style(int id, int userID, int competitionID, StyleCategories styleCategory, int placement, long timeInSeconds) {
        this.id = id;
        this.userID = userID;
        this.competitionID = competitionID;
        this.styleCategory = styleCategory;
        this.placement = placement;
        this.time = new TimeDisplay(timeInSeconds);
    }

    public StyleCategories getStyleCategory() {
        return styleCategory;
    }

    public int getPlacement() {
        return placement;
    }

    public TimeDisplay getTime() {
        return time;
    }

    public int getID() {
        return id;
    }

    public void setStyleName(StyleCategories styleCategory) {
        this.styleCategory = styleCategory;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCompetitionID() {
        return competitionID;
    }

    public void setCompetitionID(int competitionID) {
        this.competitionID = competitionID;
    }
}
