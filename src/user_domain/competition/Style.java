package user_domain.competition;

import java.text.SimpleDateFormat;

public class Style {
private String styleName;
private int placement;
private SimpleDateFormat time;

    public Style(String styleName, int placement, SimpleDateFormat time) {
        this.styleName = styleName;
        this.placement = placement;
        this.time = time;
    }

    public String getStyleName() {
        return styleName;
    }

    public int getPlacement() {
        return placement;
    }

    public SimpleDateFormat getTime() {
        return time;
    }
}
