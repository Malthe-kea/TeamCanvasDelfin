package user_domain;
import java.util.ArrayList;
import java.util.Collections;

import user_domain.competition.Competition;
import user_domain.competition.Style;

public class CompetitiveMember extends Member {
    private Style backStrokePR;
    private Style crawlPR;
    private Style breastStrokePR;
    private Style butterflyPR;
    private ArrayList<Competition> competitionList;

    public CompetitiveMember(int userID, String firstName, String lastName, boolean isActiveMember, boolean isCompetitive, int age, boolean isArrears, ArrayList<Competition> competitionList) {
        super(userID, firstName, lastName, isActiveMember, isCompetitive, age, isArrears);
        this.competitionList = competitionList;
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

    /*private void calculatePR(){
        ArrayList<Competition> competitionList = getCompetitionDB();
    }
    */

    private ArrayList<SortExampleTest> sortingExample;

    public void generateSampleData() {
        sortingExample = new ArrayList<>();
        sortingExample.add(new SortExampleTest("Event 1", "Location A", 10.5));
        sortingExample.add(new SortExampleTest("Event 2", "Location B", 9.8));
        sortingExample.add(new SortExampleTest("Event 3", "Location C", 11.2));
        sortingExample.add(new SortExampleTest("Event 4", "Location D", 22.6));
        sortingExample.add(new SortExampleTest("Event 5", "Location E", 9.9));
    }

    public void calculatePR() {
        //Her skal vi bruge databasen i stedet
        generateSampleData();

        //Hurtigste tid til mest langsomme tid
        Collections.sort(sortingExample, (c1, c2) -> Double.compare(c1.tid, c2.tid));

        //Print til at teste om det virker
        for (SortExampleTest competition : sortingExample) {
            System.out.println(competition.staevne + " - " + competition.lokation + " - " + competition.tid);
        }
    }

    public void setBackStrokePR(Style backStrokePR) {
        this.backStrokePR = backStrokePR;
    }

    public void setCrawlPR(Style crawlPR) {
        this.crawlPR = crawlPR;
    }

    public void setBreastStrokePR(Style breastStrokePR) {
        this.breastStrokePR = breastStrokePR;
    }

    public void setButterflyPR(Style butterflyPR) {
        this.butterflyPR = butterflyPR;
    }

    public void addCompition(Competition competition){
        competitionList.add(competition);
    }

    public void removeCompetitionFromID(int id){
        competitionList.removeIf(comp -> id == comp.getID());
    }
}
