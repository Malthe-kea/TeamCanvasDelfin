package user_domain.competition;
import java.util.ArrayList;

public class Competition {
    private String location;
    private ArrayList<Style> styleList;

    public Competition(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<Style> getStyleList() {
        return styleList;
    }
}
