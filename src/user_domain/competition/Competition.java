package user_domain.competition;
import java.util.ArrayList;

public class Competition {
    private int id;
    private String location;
    private ArrayList<Style> styleList;

    public Competition(int id, String location) {
        this.id = id;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<Style> getStyleList() {
        return styleList;
    }
}
