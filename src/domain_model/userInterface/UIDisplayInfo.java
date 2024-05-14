package domain_model.userInterface;

import java.util.ArrayList;

public class UIDisplayInfo {
    private String title;
    private String message;
    private ArrayList<String> options;
    private DisplayType displayType;

    public UIDisplayInfo(String title, String message, DisplayType displayType, ArrayList<String> options) {
        this.title = title;
        this.displayType = displayType;
        this.message = message;
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public DisplayType getDisplayType() {
        return displayType;
    }
}
