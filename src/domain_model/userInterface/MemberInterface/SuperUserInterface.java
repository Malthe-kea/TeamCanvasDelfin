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
                    "Tilf�j medlem",
                    "Tilf�j tr�ner",
                    "Tilf�j kasserer",
                    "�ndre medlemsoplysninger",
                    "Slet medlem",
                    "Se brugeroversigt"));


            int choice = UserInterface.drawMenu("SuperUser Menu", "V�lg en af f�lgende muligheder", options);

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
            password = inputForAddUser(title, "Indtast kodeord for medlemmet", "N�ste","Annull�r");
            firstName = inputForAddUser(title, "Indtast fornavn for medlemmet","N�ste","Annull�r");
            lastName = inputForAddUser(title, "Indtast efternavn for medlemmet", "N�ste","Annull�r");
            birthDate = inputForAddUser(title, "Indtast f�dselsdato for medlemmet format (DD/MM/YYYY)", "N�ste","Annull�r");
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
        String title = "Ny tr�ner";
        String password;
        String firstName;
        String lastName;

        try {
            password = inputForAddUser(title, "Indtast kodeord for tr�neren", "N�ste","Annull�r");
            firstName = inputForAddUser(title, "Indtast fornavn for tr�neren", "N�ste","Annull�r");
            lastName = inputForAddUser(title, "Indtast efternavn for tr�neren", "N�ste","Annull�r");
        } catch(IllegalArgumentException e) {
            return;
        }

        boolean isSeniorTrainer = UserInterface.yesNoMenu(title, "Er tr�neren Senior eller junior tr�ner?", "Senior", "Junior");

        controller.createandAddTrainerToDB(password, firstName, lastName, isSeniorTrainer);
    }

    private void addTreasurerMenu() {
        String title = "Ny kass�r";
        String password;
        String firstName;
        String lastName;
        try {
            password = inputForAddUser(title, "Indtast kodeord for tr�neren", "N�ste","Annull�r");
            firstName = inputForAddUser(title, "Indtast fornavn for tr�neren", "N�ste","Annull�r");
            lastName = inputForAddUser(title, "Indtast efternavn for tr�neren", "N�ste","Annull�r");
        } catch(IllegalArgumentException e) {
            return;
        }

        controller.createandAddTreasurertoDB(password, firstName, lastName);
    }


    private void editUserMenu() {
        int indexToEdit = 0;

        while (indexToEdit != -1) {
            indexToEdit = UserInterface.drawMenu("Redig�r bruger", "V�lg bruger du gerne vil �ndre", controller.getUserIDAndNameList());

            if(indexToEdit != -1) {
                ArrayList<String> userInfo = controller.getUserInfo(indexToEdit);
                int infoSize = userInfo.size()-1;
                if (infoSize >= 8) {
                    userInfo.subList(8, infoSize + 1).clear();
                }


                userInfo.remove(0); //Removes ID from editing list.
                userInfo.remove(2); //Removes birthdate from editing list.


                UserInterface.drawMenu("Redig�r bruger","Tryk p� det du gerne vil �ndre",userInfo);

            }

        }
    }

    private void deleteUserMenu() {
        int indexToDelete = 0;
        while (indexToDelete != -1) {
            indexToDelete = UserInterface.drawMenu("Slet bruger", "V�lg bruger du gerne vil �ndre", controller.getUserIDAndNameList());
            if (indexToDelete != -1) {
                controller.deleteUserFromDB(indexToDelete);
            }
        }
    }

    private void userOverviewMenu() {
        int indexToShow = 0;
        while (indexToShow != -1) {
            indexToShow = UserInterface.drawMenu("Brugeroversigt", "V�lg bruger du gerne vil se", controller.getUserIDAndNameList());
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
