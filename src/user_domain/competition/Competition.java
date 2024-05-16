package user_domain.competition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Competition {
    private int id;
    private String location;
    private final LocalDate date;
    private ArrayList<Style> styleList;

    public Competition(int id, String location, String date, ArrayList<Style> styleList) {
        this.id = id;
        this.location = location;
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.styleList = styleList;
    }

    public Competition(int id, String location, String date) {
        this.id = id;
        this.location = location;
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.styleList = new ArrayList<>();
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

    public void addStyle(Style style) {
        styleList.add(style);

    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStyleList(ArrayList<Style> styleList) {
        this.styleList = styleList;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public String toString() {
        return
                "Lokation: " + location + "\n" +
                "Dato: " + date +"\n";
    }
}
