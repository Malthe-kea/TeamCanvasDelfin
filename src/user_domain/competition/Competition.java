package user_domain.competition;
import java.util.ArrayList;

public class Competition {
    private int id;
    private String location;
    private ArrayList<Style> styleList;

    public Competition(int id, String location, ArrayList<Style> styleList) {
        this.id = id;
        this.location = location;
        this.styleList = styleList;
    }

    public String getLocation() {
        return location;
    }

    public int getID() {
        return id;
    }

    public ArrayList<Style> getStyleList() {
        return styleList;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStyleList(ArrayList<Style> styleList) {
        this.styleList = styleList;
    }
}
