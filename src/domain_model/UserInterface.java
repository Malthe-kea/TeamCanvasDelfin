package domain_model;

import database.DBController;
import domain_model.Processors.*;
import user_domain.Member;

import javax.swing.*;
import java.util.Scanner;

public class UserInterface {
    private boolean programIsRunning;
    Scanner userInput;
    Controller controller;
    boolean loginSucces;

    MemberProcessor memberProcessor;
    SuperUserProcessor superUserProcessor;
    TrainerProcessor trainerProcessor;
    TreasurerProcessor treasurerProcessor;

    public UserInterface() {
        controller = new Controller();
        programIsRunning = true;
        loginSucces = true;
        userInput = new Scanner(System.in);

        Member member = new Member(1, "esra", "lund", false, false, "29/09/1990", false); // Eksempel p� medlem

        memberProcessor = new MemberProcessor(member);
        superUserProcessor = new SuperUserProcessor();
        treasurerProcessor = new TreasurerProcessor();
        trainerProcessor = new TrainerProcessor();

    }

    public void startProgram() {
        while (programIsRunning) {
            showWelcomeMessage();
            String[] loginInfo = showLoginPopUp();
            if (loginInfo != null && loginInfo.length == 2) {
                String userID = loginInfo[0];
                String password = loginInfo[1];
                if (loginSucces) {
                    navigateToRole(userID);
//                   programIsRunning = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Login mislykkedes. Pr�v igen.", "Fejl", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private String[] showLoginPopUp() {
        String[] loginInfo = new String[2];
        String userID = JOptionPane.showInputDialog(null, "Indtast UserID");
        String password = JOptionPane.showInputDialog(null, "Indtast Password");

        if ((userID != null && !userID.isEmpty()) && (password != null && !password.isEmpty())) {
            loginInfo[0] = userID;
            loginInfo[1] = password;
            return loginInfo;
        }
        return null;
    }

    private void navigateToRole(String userID) {
        switch (userID.toLowerCase()) {
            case "superuser":
                JOptionPane.showMessageDialog(null, "Logget ind som 'SuperUser'", "Login Success!", JOptionPane.INFORMATION_MESSAGE);
                loginAsSuperUser();
                break;

            case "trainer":
                JOptionPane.showMessageDialog(null, "Logget ind som 'Trainer'", "Login Success!", JOptionPane.INFORMATION_MESSAGE);
                loginAsTrainer();
                break;

            case "treasurer":
                JOptionPane.showMessageDialog(null, "Logget ind som 'Treasurer'", "Login Success", JOptionPane.INFORMATION_MESSAGE);
                loginAsTreasurer();
                break;

            case "member":
                JOptionPane.showMessageDialog(null, "Logget ind som 'Member'", "Login Success", JOptionPane.INFORMATION_MESSAGE);
                loginAsMember();
                break;

            default:
                print("Forkert input, pr�v igen.");
                showWelcomeMessage();
                break;
        }
    }

    private void showWelcomeMessage() {
        JOptionPane.showMessageDialog(null, "Velkommen til Delfinen Sv�mmehal. \n Indtast login.", "Velkommen", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loginAsSuperUser() {
        print("Logget ind som Super User.");
        superUserMenuInformation();
        String command = userInput.nextLine().toLowerCase();

        switch (command) {
            case "1" -> {

                //TODO indsæt korrekte parametre herunder.
                //superUserProcessor.CreateandAddMembertoDB();
            }
            case "2" -> {
                //TODO indsæt korrekte parametre herunder.
                //superUserProcessor.CreateandAddTrainertoDB();
            }
            case "3" -> {
                //TODO indsæt korrekte parametre herunder.
                //superUserProcessor.CreateandAddCompetitiveMembertoDB();
            }
            case "4" -> {
                //TODO indsæt korrekte parametre herunder.
                //superUserProcessor.CreateAndAddTreasurertoDB();
            }
            case "5" -> {
                //TODO indsæt korrekte parametre herunder.
                //superUserProcessor.editUserFromDB(1, "esra");
            }
            case "6" -> {
                //TODO indsæt korrekte parametre herunder.
                //superUserProcessor.deleteUserFromDB(1, "esra");
            }
            case "7" -> {
                //TODO indsæt korrekte parametre herunder.
                //superUserProcessor.getUserFromDB(1);
            }
            case "9" -> {
                print("Farvel");
                programIsRunning = false;
                System.exit(0);
            }
            default -> {
                print("Forkert input, pr�v igen");
                showWelcomeMessage();
            }
        }
    }

    private void loginAsTrainer() {
        print("Logget ind som Trainer.");
    }

    private void loginAsTreasurer() {
        print("Logget ind som Treasurer.");
        treasurerMenuInformation();
        String command = userInput.nextLine().toLowerCase();
        switch (command) {
            case "1" -> {
                print("Dette års forventede indkomst er: ");
                treasurerProcessor.getExpectedIncome();
            }
            case "2" -> {

            }
            case "3" -> {

            }
            case "9" -> {
                print("Farvel");
                programIsRunning = false;
                System.exit(0);
            }
        }
    }

    private void loginAsMember() {
        print("Logget ind som Member.");
        memberMenuInformation();
        String command = userInput.nextLine().toLowerCase();

        switch (command) {
            case "1" -> {
                memberProcessor.displayMemberOverview();
            }
            case "2" -> {
                memberProcessor.displayTrainingResultsForEachDiscipline();
            }
            case "3" -> {
                memberProcessor.displayCompetitionResults();
            }
            case "9" -> {
                print("Farvel");
                programIsRunning = false;
                System.exit(0);
            }
        }
    }


    private void superUserMenuInformation() {
        print("""
                Du har nu f�lgende muligheder:
                1 - Tilf�j member til databasen
                2 - Tilf�j tr�ner til databasen
                3 - Tilf�j konkurrence-medlem til databasen
                4 - Tilf�j kasserer til databasen
                5 - �ndre medlemsoplysninger
                6 - Slet medlem
                7 - Se brugeroversigt
                9 - Exit
                """);
    }

    private void memberMenuInformation() {
        print("""
                Du har nu f�lgende muligheder:
                1 - Se member information
                2 - Se tr�ningsresultater
                3 - Se konkurrenceresultater  
                9 - Exit     
                """);
    }

    private void trainerMenuInformation() {
        print("""
                Du har nu f�lgende muligheder:
                1 - 
                2 - 
                3 -     
                """);
    }

    private void treasurerMenuInformation() {
        print("""
                Du har nu f�lgende muligheder:
                1 - Tjek den forventede årlige indkomst.
                2 - Få en liste af medlemmer, der er i restance. 
                3 -     
                """);
    }


    public static void print(String s) {
        System.out.println(s);
    }
}


//    private void logout() {
//        int valg = JOptionPane.showOptionDialog(null,
//                "Vil du logge ud?",
//                "Log ud",
//                JOptionPane.YES_NO_OPTION,
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                new String[]{"Ja", "Nej"},
//                "Ja");
//        if (valg == JOptionPane.YES_OPTION) {
//            startProgram();
//        } else {
//            //JOptionPane.showMessageDialog(null,"Du er fortsat logget ind", "forts�t", JOptionPane.PLAIN_MESSAGE);
//            }
//        }
//    }









