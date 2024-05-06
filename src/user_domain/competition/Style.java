package user_domain.competition;

import java.text.SimpleDateFormat;

public class Style {
    private int id;
    private int userID;
    private int competitionID;
    private String styleName;
    private int placement;
    private final TimeDisplay time;

    public Style(int id, int userID, int competitionID, String styleName, int placement, long timeInSeconds) {
        this.id = id;
        this.userID = userID;
        this.competitionID = competitionID;
        this.styleName = styleName;
        this.placement = placement;
        this.time = new TimeDisplay(timeInSeconds);
    }

    public String getStyleName() {
        return styleName;
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

    public void setStyleName(String styleName) {
        this.styleName = styleName;
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
