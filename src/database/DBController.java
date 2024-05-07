package database;

import database.competition_style_DB.CompetitionDB;
import database.competition_style_DB.StyleDB;
import database.userDB.UserDB;

public class DBController {

    private UserDB userDB;
    private CompetitionDB compDB;
    private StyleDB styleDB;

    public DBController() {
        userDB = new UserDB();
        compDB = new CompetitionDB();
        styleDB = new StyleDB();
    }





}
