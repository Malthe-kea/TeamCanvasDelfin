package domain_model.userInterface.MemberInterface;

import domain_model.Controller;
import domain_model.userInterface.UserInterface;

import java.lang.reflect.Array;
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
        String password;
        String firstName;
        String lastName;
        String birthDate;

        try {
            password = inputForAddUser(title, "Indtast kodeord for medlemmet", "Næste","Annullér");
            firstName = inputForAddUser(title, "Indtast fornavn for medlemmet","Næste","Annullér");
            lastName = inputForAddUser(title, "Indtast efternavn for medlemmet", "Næste","Annullér");
            birthDate = inputForAddUser(title, "Indtast fødselsdato for medlemmet format (DD/MM/YYYY)", "Næste","Annullér");
        } catch(IllegalArgumentException e) {
            return;
        }


        boolean activePassiveInput = UserInterface.yesNoMenu(title, "Er medlemmet aktivt eller passivt?", "Aktiv", "Passiv");
        boolean isCompetitiveInput = UserInterface.yesNoMenu(title, "Hvad er medlemmets aktivitetniveau?", "Konkurrence", "Motonist");
        boolean isArrearsInput = false;

        if (isCompetitiveInput) {
            controller.createandAddCompetitiveMembertoDB(password, firstName, lastName, activePassiveInput, birthDate, isCompetitiveInput, isArrearsInput);
        } else {
            controller.createandAddMembertoDB(password, firstName, lastName, activePassiveInput, birthDate, isCompetitiveInput, isArrearsInput);
        }
    }

    private void addTrainerMenu() {
        String title = "Ny træner";
        String password;
        String firstName;
        String lastName;

        try {
            password = inputForAddUser(title, "Indtast kodeord for træneren", "Næste","Annullér");
            firstName = inputForAddUser(title, "Indtast fornavn for træneren", "Næste","Annullér");
            lastName = inputForAddUser(title, "Indtast efternavn for træneren", "Næste","Annullér");
        } catch(IllegalArgumentException e) {
            return;
        }

        boolean isSeniorTrainer = UserInterface.yesNoMenu(title, "Er træneren Senior eller junior træner?", "Senior", "Junior");

        controller.createandAddTrainerToDB(password, firstName, lastName, isSeniorTrainer);
    }

    private void addTreasurerMenu() {
        String title = "Ny kassér";
        String password;
        String firstName;
        String lastName;
        try {
            password = inputForAddUser(title, "Indtast kodeord for træneren", "Næste","Annullér");
            firstName = inputForAddUser(title, "Indtast fornavn for træneren", "Næste","Annullér");
            lastName = inputForAddUser(title, "Indtast efternavn for træneren", "Næste","Annullér");
        } catch(IllegalArgumentException e) {
            return;
        }

        controller.createandAddTreasurertoDB(password, firstName, lastName);
    }


    private void editUserMenu() {
        int indexToEdit = 0;

        while (indexToEdit != -1) {
            indexToEdit = UserInterface.drawMenu("Redigér bruger", "Vælg bruger du gerne vil ændre", controller.getUserIDAndNameList());

            if(indexToEdit != -1) {
                ArrayList<String> userInfo = controller.getUserInfo(indexToEdit);
                int infoSize = userInfo.size()-1;
                if (infoSize >= 8) {
                    userInfo.subList(8, infoSize + 1).clear();
                }


                userInfo.remove(0); //Removes ID from editing list.
                userInfo.remove(2); //Removes birthdate from editing list.


                UserInterface.drawMenu("Redigér bruger","Tryk på det du gerne vil ændre",userInfo);

            }

        }
    }

    private void deleteUserMenu() {
        int indexToDelete = 0;
        while (indexToDelete != -1) {
            indexToDelete = UserInterface.drawMenu("Slet bruger", "Vælg bruger du gerne vil ændre", controller.getUserIDAndNameList());
            if (indexToDelete != -1) {
                controller.deleteUserFromDB(indexToDelete);
            }
        }
    }

    private void userOverviewMenu() {
        int indexToShow = 0;
        while (indexToShow != -1) {
            indexToShow = UserInterface.drawMenu("Brugeroversigt", "Vælg bruger du gerne vil se", controller.getUserIDAndNameList());
            if (indexToShow != -1) {
                ArrayList<String> userInfo = controller.getUserInfo(indexToShow);
                String displayInfo = String.join("\n", userInfo);
                UserInterface.smallWindow("Bruger info", displayInfo, "OK");
            }
        }
    }



    private String inputForAddUser(String title, String message, String okButtonText, String cancelButtonText) {
        String input = UserInterface.inputMenu(title, message, okButtonText, cancelButtonText);
        if(input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return input;
    }
}
