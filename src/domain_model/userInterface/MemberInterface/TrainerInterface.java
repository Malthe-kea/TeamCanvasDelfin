package domain_model.userInterface.MemberInterface;

import domain_model.Controller;
import domain_model.userInterface.UserInterface;
import user_domain.SuperUser;
import user_domain.Trainer;
import user_domain.User;

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
                    //TODO Implementer holdvisning fra TrainerProcessor
                }
                case 2 -> {
                    //TODO Implementer TOP 5 - Brystsvømning fra TrainerProcessor
                }
                case 3 -> {
                    //TODO Implementer TOP 5 - Crawl fra TrainerProcessor
                }
                case 4 -> {
                    //TODO Implementer TOP 5 - Butterfly fra TrainerProcessor
                }
                case 5 -> {
                    //TODO Implementer TOP 5 - Rygcrawl fra TrainerProcessor
                }
                case -1 -> {
                    loggedIn = false;
                }
            }
        }
    }

}
