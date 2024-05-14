package domain_model.userInterface.MemberInterface;

import database.DBController;
import domain_model.Controller;
import domain_model.userInterface.UserInterface;

import java.util.ArrayList;
import java.util.List;

public class SuperUserInterface {
    Controller controller;

    public SuperUserInterface(Controller controller) {
        this.controller = controller;
    }

    public void startMenu() {
        boolean loggedIn = true;
        while (loggedIn) {

            ArrayList<String> options = new ArrayList<>(List.of(
                    "Tilføj medlem",
                    "Tilføj træner",
                    "Tilføj kasserer",
                    "Ændre medlemsoplysninger",
                    "Slet medlem",
                    "Se brugeroversigt"));


            int choice = UserInterface.drawMenu("SuperUser Menu", "Vælg en af følgende muligheder", options);

            switch (choice) {
                case 0 -> {
                    addMemberMenu();

                }
                case 1 -> {
                    addTrainerMenu();
                }
                case 2 -> {
                    addTreasurerMenu();
                }
                case 3 -> {
                    editUserMenu();
                }
                case 4 -> {
                    deleteUserMenu();
                }
                case 5 -> {
                    userOverviewMenu();
                }
                case -1 -> {
                    loggedIn = false;
                }
            }
        }
    }


    private void addMemberMenu() {
        String title = "Nyt medlem";
        String password = UserInterface.inputMenu(title, "Indast kodeord for medlemmet");
        String firstName = UserInterface.inputMenu(title, "Indast fornavn for medlemmet");
        String lastName = UserInterface.inputMenu(title, "Indast efternavn for medlemmet");
        boolean activePassiveInput = UserInterface.yesNoMenu(title, "Er medlemmet aktivt eller passivt?");
        String birthDate = UserInterface.inputMenu(title, "Indast fødselsdato for medlemmet format (DD/MM/YYYY)");
        boolean isCompetitiveInput = UserInterface.yesNoMenu(title, "Er medlemmet konkurrence svømmer?");
        boolean isArrearsInput = false;

        if (isCompetitiveInput) {
            controller.createandAddCompetitiveMembertoDB(password, firstName, lastName, activePassiveInput, birthDate, isCompetitiveInput, isArrearsInput);
        } else {
            controller.createandAddMembertoDB(password, firstName, lastName, activePassiveInput, birthDate, isCompetitiveInput, isArrearsInput);
        }
    }

    private void addTrainerMenu() {
        String title = "Ny træner";
        String password = UserInterface.inputMenu(title, "Indast kodeord for træneren");
        String firstName = UserInterface.inputMenu(title, "Indast fornavn for træneren");
        String lastName = UserInterface.inputMenu(title, "Indast efternavn for træneren");
        boolean isSeniorTrainer = UserInterface.yesNoMenu(title, "Er træneren junior træner tryk Ja. Tryk nej hvis junior?");

        controller.createandAddTrainerToDB(password, firstName, lastName, isSeniorTrainer);
    }

    private void addTreasurerMenu() {
        String title = "Ny kassér";
        String password = UserInterface.inputMenu(title, "Indast kodeord for træneren");
        String firstName = UserInterface.inputMenu(title, "Indast fornavn for træneren");
        String lastName = UserInterface.inputMenu(title, "Indast efternavn for træneren");

        controller.createandAddTreasurertoDB(password, firstName, lastName);
    }


    private void editUserMenu() {
        int indexToEdit = 0;

        while (indexToEdit != -1) {
            indexToEdit = UserInterface.drawMenu("Slet bruger", "Vælg bruger du gerne vil ændre", controller.getUserList());
        }
    }

    private void deleteUserMenu() {
        int indexToDelete = 0;
        while (indexToDelete != -1) {
            indexToDelete = UserInterface.drawMenu("Slet bruger", "Vælg bruger du gerne vil ændre", controller.getUserList());
            if (indexToDelete != -1) {
                controller.deleteUserFromDB(indexToDelete);
            }
        }
    }

    private void userOverviewMenu() {
        int indexToShow = 0;
        while (indexToShow != -1) {
            indexToShow = UserInterface.drawMenu("Brugeroversigt", "Vælg bruger du gerne vil se", controller.getUserList());
            if (indexToShow != -1) {
                UserInterface.smallWindow("Bruger info", controller.getUserInfo(indexToShow), "OK");
            }
        }
    }
}
