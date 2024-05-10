package database;

import database.competition_style_DB.CompetitionDB;
import database.competition_style_DB.StyleDB;
import database.userDB.MemberDB;
import database.userDB.UserDB;
import user_domain.Member;
import user_domain.User;
import user_domain.competition.Competition;
import user_domain.competition.Style;

import java.util.ArrayList;

public class DBController {

    private UserDB userDB;
    private CompetitionDB compDB;
    private StyleDB styleDB;
    private MemberDB memberDB;

    public DBController() {
        userDB = new UserDB();
        compDB = new CompetitionDB();
        styleDB = new StyleDB();
        memberDB = new MemberDB();
    }


    //USERDB METHODS

    //REMOVE USER FROM DB WITH ID. RETURNS TRUE IF SUCCESSFUL
    public boolean removeUserFromDB(int userID) {
        return userDB.removeUserFromDB(userID);
    }

    //REMOVE USER FROM DB WITH USER OBJECT. RETURNS TRUE IF SUCCESSFUL
    public boolean removeUserFromDB(User user) {
        return userDB.removeUserFromDB(user);
    }

    //ADD USER TO DB WITH USER OBJECT. RETURNS TRUE IF SUCCESSFUL
    public boolean addUserToDB(User user, String password) {
        return userDB.addUserInDB(user, password);
    }

    //EDIT USER IN DB WITH USER OBJECT. RETURNS TRUE IF SUCCESSFUL
    public boolean editUserInDB(User user) {
        return userDB.editUserInDB(user);
    }

    //GET LIST OF ALL USERS IN DB
    public ArrayList<User> getListOfAllUsers() {
        return userDB.getListOfUsers();
    }

    //GET USER FROM ID
    public User getUserFromID(int userID) {
        return userDB.getUserFromID(userID);
    }

    //GET USER FROM LASTNAME
    public User getUserFromLastName(String lastName) {
        return userDB.getUserFromLastName(lastName);
    }

    //GET AVAILABLE ID FOR NEW USER ENTRY
    public int getIDForNewUser() {
        return userDB.getIDForNewUser();
    }

    //MEMBERDB METHODS
    //GET LIST OF MEMBERS
    public ArrayList<Member> getListOfMembers() {
        ArrayList<Member> memberList = new ArrayList<>();
        memberDB.getListOfUsers().forEach(user -> memberList.add((Member) user));
        return memberList;
    }

    //COMPETITIONDB METHODS
    //GET LIST OF COMPETITION WITH EMPTY STYLE LIST
    public ArrayList<Competition> getListOfCompetitions() {
        return compDB.getListOfCompetitions();
    }

    //GET LIST OF COMPETITION WITH STYLE LIST FROM COMPETITIVE USER ID
    public ArrayList<Competition> getListOfCompetitions(int userID) {
        return compDB.getListOfCompetitions(userID);
    }

    //GET COMPETITION FROM ID WITH EMPTY STYLE LIST
    public Competition getCompetitionFromID(int competitionID) {
        return compDB.getCompetitionFromID(competitionID);
    }

    //GET COMPETITION FROM ID WITH STYLE LIST FROM COMPETITIVE USER ID
    public Competition getCompetitionFromID(int competitionID, int userID) {
        return compDB.getCompetitionFromID(competitionID, userID);
    }

    //GET COMPETITION FROM LOCATION WITH EMPTY STYLE LIST
    public Competition getCompetitionFromLocation(String competitionLocation) {
        return compDB.getCompetitionFromLocation(competitionLocation);
    }

    //GET COMPETITION FROM LOCATION WITH STYLE LIST FROM COMPETITIVE USER ID
    public Competition getCompetitionFromLocation(String competitionLocation, int userID) {
        return compDB.getCompetitionFromLocation(competitionLocation, userID);
    }

    //EDIT COMPETITION IN DB. RETURNS TRUE IF SUCCESSFUL
    public boolean editCompInDB(Competition comp) {
        return compDB.editCompInDB(comp);
    }

    //GET AVAILABLE ID FOR NEW COMPETITION ENTRY
    public int getIDForNewCompetition() {
        return compDB.getIDForNewCompetition();
    }

    //ADD COMPETITION TO DB. RETURNS TRUE IF SUCCESSFUL
    public boolean addCompToDB(Competition comp) {
        return compDB.addCompetitionToDB(comp);
    }

    //REMOVE COMPETITION TO DB FROM ID. RETURNS TRUE IF SUCCESSFUL
    public boolean removeCompFromDB(int competitionID) {
        return compDB.removeCompFromDB(competitionID);
    }

    //REMOVE COMPETITION TO DB FROM COMPETITION OBJECT. RETURNS TRUE IF SUCCESSFUL
    public boolean removeCompFromDB(Competition comp) {
        return compDB.removeCompFromDB(comp);
    }

    //STYLEDB METHODS
    //GET STYLE FROM STYLE ID
    public Style getStyleFromID(int styleID) {
        return styleDB.getStyleFromID(styleID);
    }

    //EDIT STYLE IN DB. RETURNS TRUE IF SUCCESSFUL
    public boolean editStyleInDB(Style style) {
        return styleDB.editStyleInDB(style);
    }

    //GET list of styles from competition and user id
    public ArrayList<Style> getUserCompetitionStyles(int competitionID, int userID) {
        return styleDB.getUserCompetitionStyles(competitionID, userID);
    }

    //GET ID FOR NEW STYLE ENTRY
    public int getIDForNewStyle() {
        return styleDB.getIDForNewStyle();
    }

    //ADD STYLE TO DB. RETURNS TRUE IF SUCCESSFUL
    public boolean addStyleToDB(Style style) {
        return styleDB.addStyleToDB(style);
    }

    //REMOVE STYLE FROM DB FROM ID. RETURNS TRUE IF SUCCESSFUL
    public boolean removeStyleFromDB(int styleID) {
        return styleDB.removeStyleFromDB(styleID);
    }

    //REMOVE STYLE FROM DB FROM STYLE OBJECT. RETURNS TRUE IF SUCCESSFUL
    public boolean removeStyleFromDB(Style style) {
        return styleDB.removeStyleFromDB(style);
    }


}
