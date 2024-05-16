package domain_model.Processors;

import database.DBController;
import user_domain.Member;

public class MemberProcessor implements Processor {
    private DBController dbController;
    private Member member;

    public MemberProcessor(DBController dbController) {
        this.dbController = dbController;
    }

    public void displayTrainingResultsForEachDiscipline() {
        System.out.println("Results: ");

    }

    public void displayCompetitionResults(int userID) {
        if (member.isCompetitive()) {
            dbController.getListOfCompetitions(userID);
        }
    }
}
