package domain_model.Processors;

import database.DBController;
import database.competition_style_DB.CompetitionDB;
import user_domain.CompetitiveMember;
import user_domain.Member;
import user_domain.Trainer;
import user_domain.User;
import user_domain.competition.Competition;
import user_domain.competition.Style;
import user_domain.competition.StyleCategories;

import java.util.ArrayList;
import java.util.Comparator;

public class TrainerProcessor implements Processor {

    DBController dbController;


    public TrainerProcessor(DBController dbController) {
        this.dbController = dbController;
    }

    public ArrayList<String> getTopFive(StyleCategories styleCategory) {
        ArrayList<Style> tempStyleArr = new ArrayList<>();
        ArrayList<Member> tempMemberArr = dbController.getListOfMembers();
        tempMemberArr.removeIf(member -> !(member instanceof CompetitiveMember));
        ArrayList<CompetitiveMember> tempCompMemberArr = new ArrayList<>();

        for (Member m : tempMemberArr) {
            tempCompMemberArr.add((CompetitiveMember) m);
        }
        tempCompMemberArr.removeIf(member -> member.getCompetitionList().isEmpty());

        for (CompetitiveMember member : tempCompMemberArr) {
            for (Competition c : member.getCompetitionList()) {
                for (Style s : c.getStyleList()) {
                    if (s.getStyleCategory() == styleCategory) {
                        tempStyleArr.add(s);
                    }
                }
            }
        }
        tempStyleArr.sort(Comparator.naturalOrder());
        ArrayList<String> topFiveStylesToString = new ArrayList<>();
        ArrayList<Style> topFiveStyles = new ArrayList<>();
        for (Style s : tempStyleArr) {
            boolean isIdenticalUser = false;
            for (Style fs : topFiveStyles) {
                if (fs.getUserID() == s.getUserID()) {
                    isIdenticalUser = true;
                    break;
                }
            }
            if (isIdenticalUser == false) {
                User user = dbController.getUserFromID(s.getUserID());
                topFiveStyles.add(s);

                topFiveStylesToString.add(user.getFirstName() + " " + user.getLastName() + "\n" + s.toString());

                if (topFiveStyles.size() == 5) {
                    return topFiveStylesToString;
                }
            }
        }
        return topFiveStylesToString;
    }

    public ArrayList<String> getListOfTeams(Trainer trainer) {

        ArrayList<Member> tempMemberArr = dbController.getListOfMembers();
        tempMemberArr.removeIf(member -> !(member instanceof CompetitiveMember));
        ArrayList<CompetitiveMember> tempCompMemberArr = new ArrayList<>();

        for (Member m : tempMemberArr) {
            tempCompMemberArr.add((CompetitiveMember) m);
        }
        if (trainer.isSeniorTrainer()) {
            tempCompMemberArr.removeIf(member -> !(member.isSenior()));
        } else {
            tempCompMemberArr.removeIf(member -> member.isSenior());
        }
        ArrayList<String> finalListOfTeams = new ArrayList<>();
        tempCompMemberArr.forEach(member -> finalListOfTeams.add(member.toString()));
        return finalListOfTeams;
    }

    public void createCompetition(String location, String date) {
        Competition competition = new Competition(dbController.getIDForNewCompetition(), location, date);
        if (!dbController.getListOfCompetitions().contains(competition.getID()))
            dbController.addCompToDB(competition);
        else {
            doubleCompInDBError(competition.toString());
        }
    }

    public String doubleCompInDBError(String c) {
        return c.toString() + " Findes allerede.";
    }

    public ArrayList<String> getCompetitionList() {
        boolean isNotFull = true;
        ArrayList<Competition> tempComp = dbController.getListOfCompetitions();
        ArrayList<String> returnCompList = new ArrayList<>();
        while (isNotFull) {
            for (int i = 0; i < tempComp.size(); i++) {
            for (Competition c : tempComp)
                returnCompList.add(c.toString());
                return returnCompList;
        }
    }
        return null;
}
}
