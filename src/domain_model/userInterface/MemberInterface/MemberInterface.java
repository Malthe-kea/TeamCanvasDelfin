package domain_model.userInterface.MemberInterface;

import domain_model.Controller;
import domain_model.userInterface.UserInterface;
import user_domain.CompetitiveMember;
import user_domain.Member;
import user_domain.Trainer;
import user_domain.User;

import java.util.ArrayList;
import java.util.List;

public class MemberInterface {

    Member userLoggingIn;
    Controller controller;

    public MemberInterface(Controller controller, User userLoggingIn) {
        this.controller = controller;
        this.userLoggingIn = (Member) userLoggingIn;
    }

    public void startMenu() {

        boolean loggedIn = true;
        while (loggedIn) {

            ArrayList<String> options = new ArrayList<>(List.of("Se din profil"));

            if(userLoggingIn instanceof CompetitiveMember) {
                options.add("Se dine konkurrenceresultater");
            }



            int choice = UserInterface.drawMenu("Trainer Menu", "Vælg en af følgende muligheder", "Afslut", options);

            switch (choice) {
                case 0 -> {
                    UserInterface.smallWindow("Din profil", userLoggingIn.toString(), "Tilbage");
                }
                case 1 -> {

                    int competitionChoice = -2;
                    while(competitionChoice != -1) {
                        competitionChoice = UserInterface.drawMenu("Dine stævner","Tryk på det stævne du gerne vil se", "Tilbage", new ArrayList<String>());

                        //TODO: Implementer stævnevisning fra MemberProcessor

                    }
                }
                case -1 -> {
                    loggedIn = false;
                }
            }
        }



        UserInterface.smallWindow("Din profil", userLoggingIn.toString(), "Log ud");
    }
}
