package domain_model.Processors;

import database.DBController;
import domain_model.userInterface.UIDisplayInfo;
import user_domain.Member;

public class MemberProcessor implements Processor {
    private DBController dbController;
    private Member member;

    public MemberProcessor(DBController dbController) {
        this.dbController = dbController;
    }

    // Metode til at vise tr?ningsresultat og dato for hver disciplin
    public void displayTrainingResultsForEachDiscipline() {
        System.out.println("Results: ");

    }

    // Metode til at vise alle registrerede konkurrenceresultater
    public void displayCompetitionResults(int userID) {
        if (member.isCompetitive()) {
            dbController.getListOfCompetitions(userID);
        }
    }
}
