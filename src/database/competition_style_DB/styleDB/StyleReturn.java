package database.competition_style_DB.styleDB;

import java.io.File;

public interface StyleReturn {


    static String getFolderPath() {
        return "StyleDB" + File.separator;
    }
}
