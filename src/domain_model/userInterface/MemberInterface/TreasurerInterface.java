package domain_model.userInterface.MemberInterface;

import domain_model.Controller;
import domain_model.userInterface.UserInterface;
import user_domain.User;

import java.util.ArrayList;
import java.util.List;

public class TreasurerInterface {

    User userLoggingIn;
    Controller controller;

    public TreasurerInterface(Controller controller, User userLoggingIn) {
        this.controller = controller;
        this.userLoggingIn = userLoggingIn;
    }

    public void startMenu() {
        boolean loggedIn = true;
        while (loggedIn) {

            ArrayList<String> options = new ArrayList<>(List.of(
                    "Se din profil",
                    "Se total indt�gt",
                    "Se medlemmer i restance"));

            int choice = UserInterface.drawMenu("Trainer Menu", "V�lg en af f�lgende muligheder", "Afslut", options);

            switch (choice) {
                case 0 -> {
                    UserInterface.smallWindow("Din profil", userLoggingIn.toString(), "Tilbage");
                }
                case 1 -> {
                    UserInterface.smallWindow("Total indt�gt", controller.getExpectedIncome(), "Tilbage");
                }
                case 2 -> {
                    UserInterface.drawMenu("Medlemmer i restance", "Nedenst�ende medlemmer er i restance", "Tilbage", controller.getMembersInArrears());
                }

                case -1 -> {
                    loggedIn = false;
                }
            }
        }
    }
}
