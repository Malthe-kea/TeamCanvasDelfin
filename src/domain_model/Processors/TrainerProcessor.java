package domain_model.Processors;

import database.DBController;
import user_domain.CompetitiveMember;
import user_domain.Member;
import user_domain.Trainer;
import user_domain.User;
import user_domain.competition.Competition;
import user_domain.competition.Style;
import user_domain.competition.StyleCategories;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TrainerProcessor implements Processor {

    DBController dbController;

    public TrainerProcessor(DBController dbController) {
        this.dbController = dbController;
    }

    public ArrayList<String> getTopFive(StyleCategories styleCategory) {
        ArrayList<Style> tempStyleArr = new ArrayList<>();
        ArrayList<CompetitiveMember> tempCompMemberArr = dbController.getListOfCompetitiveMembers();
        tempCompMemberArr.removeIf(member -> member.getCompetitionList().isEmpty());

        HashMap<Style, Competition> tempStyleCompMap = new HashMap<>();

        for (CompetitiveMember member : tempCompMemberArr) {
            for (Competition c : member.getCompetitionList()) {
                for (Style s : c.getStyleList()) {
                    if (s.getStyleCategory() == styleCategory) {
                        tempStyleArr.add(s);
                        tempStyleCompMap.put(s, c);
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
            if (!isIdenticalUser) {
                User user = dbController.getUserFromID(s.getUserID());
                topFiveStyles.add(s);
                Competition c = tempStyleCompMap.get(s);
                String styleString =
                        user.getFirstName() + " " + user.getLastName() + "\n" +
                                "Stævne: " + c.getLocation() + "\n" +
                                "Dato: " + c.getDate() + "\n" +
                                s.toString();

                topFiveStylesToString.add(styleString);

                if (topFiveStyles.size() == 5) {
                    return topFiveStylesToString;
                }
            }
        }
        return topFiveStylesToString;
    }

    public ArrayList<String> getListOfTeams(Trainer trainer) {

        ArrayList<CompetitiveMember> tempCompMemberArr = dbController.getListOfCompetitiveMembers();
        if (trainer.isSeniorTrainer()) {
            tempCompMemberArr.removeIf(member -> !(member.isSenior()));
        } else {
            tempCompMemberArr.removeIf(member -> member.isSenior());
        }
        ArrayList<String> finalListOfTeams = new ArrayList<>();
        tempCompMemberArr.forEach(member -> finalListOfTeams.add(member.toString()));
        return finalListOfTeams;
    }

    //TODO træner skal kunne oprette stævner.
    //TODO træner skal kunne registrere stævneresultater.

    public void addStyleToMember(int indexOfMember, int indexOfCompetetion, StyleCategories styleType,
                                 long seconds, int placement) {
        CompetitiveMember thisMemberToEdit = dbController.getListOfCompetitiveMembers().get(indexOfMember);
        Competition thisCompToAdd = dbController.getListOfCompetitions().get(indexOfCompetetion);
        Style styleToAdd = new Style(dbController.getIDForNewStyle(), thisMemberToEdit.getUserID(),
                thisCompToAdd.getID(), styleType, placement, seconds);

        dbController.addStyleToDB(styleToAdd);
    }
}
