package domain_model.userInterface.MemberInterface;

import domain_model.Controller;
import domain_model.userInterface.UserInterface;
import user_domain.SuperUser;
import user_domain.Trainer;
import user_domain.User;
import user_domain.competition.StyleCategories;

import java.util.ArrayList;
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
                    "Se st�vner",
                    "Opret st�vne",
                    "Registre st�vneresultater",
                    "Se TOP 5 - Brystsv�mning",
                    "Se TOP 5 - Crawl",
                    "Se TOP 5 - Butterfly",
                    "Se TOP 5 - Rygcrawl"));


            int choice = UserInterface.drawMenu("Trainer Menu", "V�lg en af f�lgende muligheder", "Afslut", options);

            switch (choice) {
                case 0 -> {
                    UserInterface.smallWindow("Din profil", userLoggingIn.toString(), "Tilbage");
                }
                case 1 -> {
                    UserInterface.showList("Dit hold", controller.getListOfTeams(userLoggingIn));
                }
                case 2 -> {

                }
                case 3 -> {
                    //TODO OPRET ST�VNE
                }
                case 4 -> {
                    //TODO REGISTRER ST�VNERESULTATER
                }
                case 5 -> {
                    UserInterface.showList("TOP 5 - Brystsv�mning", controller.getTopFive(StyleCategories.BREASTSTROKE));
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

}
