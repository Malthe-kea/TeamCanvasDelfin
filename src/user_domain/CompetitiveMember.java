package user_domain;
import java.util.ArrayList;
import java.util.Collections;

import user_domain.competition.Competition;
import user_domain.competition.Style;

public class CompetitiveMember extends Member {

    private ArrayList<Competition> competitionList;

    public CompetitiveMember(int userID,
                             String firstName,
                             String lastName,
                             boolean isActiveMember,
                             boolean isCompetitive,
                             String dateOfBirth,
                             boolean isArrears,
                             ArrayList<Competition> competitionList) {
        super(userID, firstName, lastName, isActiveMember, isCompetitive, dateOfBirth, isArrears);
        this.competitionList = competitionList;
    }

    public CompetitiveMember(int userID,
                             String firstName,
                             String lastName,
                             boolean isActiveMember,
                             boolean isCompetitive,
                             String dateOfBirth,
                             boolean isArrears) {
        super(userID, firstName, lastName, isActiveMember, isCompetitive, dateOfBirth, isArrears);
        this.competitionList = new ArrayList<>();
    }

    public ArrayList<Competition> getCompetitionList() {
        return competitionList;
    }

    public void addCompetition(Competition competition){
        competitionList.add(competition);
    }

    @Override
    public String toString() {
        String result =
                "Kontotype: Konkurrencesvømmer\n" +
                super.toString() + "\n";
        return result;
    }

}
