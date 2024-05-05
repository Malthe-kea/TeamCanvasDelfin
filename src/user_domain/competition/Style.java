package user_domain.competition;

import java.text.SimpleDateFormat;

public class Style {
    private int id;
    private String styleName;
    private int placement;
    private TimeDisplay time;

    public Style(int id, String styleName, int placement, long timeInSeconds) {
        this.id = id;
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
}
