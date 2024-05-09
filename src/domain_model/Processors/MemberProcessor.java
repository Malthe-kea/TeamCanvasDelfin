package domain_model.Processors;

import database.DBController;
import user_domain.Member;

public class MemberProcessor implements Processor {
    private DBController dbController;
    private Member member;

    public MemberProcessor(DBController dbController) {
        this.dbController = dbController;
    }

//    public void MemberInfoMenu(String command) {
//        System.out.println("Menu options: ");
//        switch (command) {
//            case "1" -> {
//                displayMemberOverview();
//            }
//            case "2" -> {
//                displayTrainingResultsForEachDiscipline();
//            }
//            case "3" -> {
//                displayCompetitionResults();
//            }
//
//        }
//    }

    public void displayMemberOverview() {
        System.out.println("Member profile:");
        System.out.println("Is active/passive: " + member.isActiveMember());
        System.out.println("Is a competitive member: " + member.isCompetitive());
        System.out.println("Restance: " + member.isArrears());
        System.out.println("Yearly membership fee: " + member.getYearlyMembershipFee());
    }

    // Metode til at vise tr?ningsresultat og dato for hver disciplin
    public void displayTrainingResultsForEachDiscipline() {
        System.out.println("Results: ");

    }

    // Metode til at vise alle registrerede konkurrenceresultater
    public void displayCompetitionResults() {
        if (member.isCompetitive()) {
        //return CompetitiveMember.getCompetitionDB();
        //return UserDB.getPR(); //eller noget til at f? pr for hver disciplin

        }

    }

    }

