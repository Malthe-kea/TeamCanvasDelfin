package user_domain;
import java.util.ArrayList;

import user_domain.competition.Competition;
import user_domain.competition.Style;

public class CompetitiveMember extends Member {
    private Style backStrokePR;
    private Style crawlPR;
    private Style breastStrokePR;
    private Style butterflyPR;
    private ArrayList<Competition> competitionList;

    public CompetitiveMember(int userID, String firstName, String lastName, boolean isActiveMember, boolean isCompetitive, int age, boolean isArrears) {
        super(userID, firstName, lastName, isActiveMember, isCompetitive, age, isArrears);
    }

    public ArrayList<Competition> getCompetitionList() {
        return competitionList;
    }

    public Style getBackStrokePR() {
        return backStrokePR;
    }

    public Style getCrawlPR() {
        return crawlPR;
    }

    public Style getBreastStrokePR() {
        return breastStrokePR;
    }

    public Style getButterflyPR() {
        return butterflyPR;
    }
    //Muligvis omdøbes.
    private ArrayList<Competition> getCompetitionDB(){
        return null;
    }

    //TODO
    //Fremgangsmåden her vil være at sortere fra lavest til højest ;)
    private void calculatePR(){
        ArrayList<Competition> competitionList = getCompetitionDB();
    }
}