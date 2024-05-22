package domain_model.userInterface.MemberInterface;

import domain_model.Controller;
import domain_model.userInterface.UserInterface;
import user_domain.SuperUser;
import user_domain.Trainer;
import user_domain.User;
import user_domain.competition.StyleCategories;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class TrainerInterface {

    Controller controller;
    Trainer userLoggingIn;

    public TrainerInterface(Controller controller, User userLoggingIn) {
        this.controller = controller;
        this.userLoggingIn = (Trainer) userLoggingIn;
    }

    public void startMenu() {
        boolean loggedIn = true;
        while (loggedIn) {

            ArrayList<String> options = new ArrayList<>(List.of(
                    "Se din profil",
                    "Se dit hold",
                    "Se stævner",
                    "Opret stævne",
                    "Registre stævneresultater",
                    "Se TOP 5 - Brystsvømning",
                    "Se TOP 5 - Crawl",
                    "Se TOP 5 - Butterfly",
                    "Se TOP 5 - Rygcrawl"));


            int choice = UserInterface.drawMenu("Trainer Menu", "Vælg en af følgende muligheder", "Afslut", options);

            switch (choice) {
                case 0 -> {
                    UserInterface.smallWindow("Din profil", userLoggingIn.toString(), "Tilbage");
                }
                case 1 -> {
                    UserInterface.showList("Dit hold", controller.getListOfTeams(userLoggingIn));
                }
                case 2 -> {
                    UserInterface.showList("Stævner", controller.getCompetitionList());
                }
                case 3 -> {
                    createCompetitionUI();
                }
                case 4 -> {
                    registerCompetitionResults();
                }
                case 5 -> {
                    UserInterface.showList("TOP 5 - Brystsvømning", controller.getTopFive(StyleCategories.BREASTSTROKE));
                }
                case 6 -> {
                    UserInterface.showList("TOP 5 - Crawl", controller.getTopFive(StyleCategories.CRAWL));
                }
                case 7 -> {
                    UserInterface.showList("TOP 5 - Butterfly", controller.getTopFive(StyleCategories.BUTTERFLY));
                }
                case 8 -> {
                    UserInterface.showList("TOP 5 - Rygcrawl", controller.getTopFive(StyleCategories.BACKSTROKE));
                }
                case -1 -> {
                    loggedIn = false;
                }
            }
        }
    }


    public void createCompetitionUI() {
        String location = UserInterface.inputMenu("Opret stævne", "Indtast stævnets navn","Næste", "Annuller");
        if(location == null) {
            return;
        }
        String date = UserInterface.inputMenu("Opret stævne", "Indtast stævnets dato (DD/MM/YYYY)", "Opret", "Annuller");
        if(date == null) {
            return;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            formatter.parse(date);
        } catch(DateTimeParseException DTPE) {
            UserInterface.showErrorMessage("FEJL", "Datoen er ikke i korrekt format. Prøv igen.");
            return;
        }

        controller.createCompetition(location, date);

    }

    public void registerCompetitionResults() {
        int userIDToEdit = UserInterface.drawMenu("Registrer stævneresultater", "Vælg bruger at tilføje resultat til", "Annuller", controller.getListOfCompetitiveMembersForUIButton());
        if(userIDToEdit == -1) {
            return;
        }
        int competitionID = UserInterface.drawMenu("Registrer stævneresultater", "Vælg stævne at tilføje til", "Annuller", controller.getUIButtonCompetitionList());
        if(competitionID == -1) {
            return;
        }

        int styleChoice = UserInterface.drawMenu("Registrer stævneresultater", "Vælg stilart", "Annuller",
                new ArrayList<>(List.of("Brystsvømning", "Crawl", "Butterfly", "Rygcrawl")));
        StyleCategories style = switch (styleChoice) {
            case 0 -> StyleCategories.BREASTSTROKE;
            case 1 -> StyleCategories.CRAWL;
            case 2 -> StyleCategories.BUTTERFLY;
            case 3 -> StyleCategories.BACKSTROKE;
            default -> null;
        };
        long totalSeconds = 0;
        int placement = 0;

        try {
            Long minutes = 60*(Long.parseLong(UserInterface.getInputCheckNull("Registrer stævneresultater", "Indtast minutter", "Næste", "Annuller")));
            Long seconds = Long.parseLong(UserInterface.getInputCheckNull("Registrer stævneresultater", "Indtast sekunder", "Næste", "Annuller"));
            totalSeconds = minutes + seconds;

            placement = Integer.parseInt(UserInterface.getInputCheckNull("Registrer stævneresultater", "Indtast placering", "Registrer", "Annuller"));

        } catch (NumberFormatException NFE) {
            UserInterface.showErrorMessage("FEJL", "Indtast venligst et tal.");
        } catch(IllegalArgumentException IAE) {
            return;
        }

        controller.addStyleToMember(userIDToEdit, competitionID, style, totalSeconds, placement);

    }

}
