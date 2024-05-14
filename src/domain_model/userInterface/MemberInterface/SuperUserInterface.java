package domain_model.userInterface.MemberInterface;

import domain_model.Controller;
import domain_model.UserInstance;
import domain_model.userInterface.UserInterface;
import user_domain.SuperUser;
import user_domain.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SuperUserInterface {
    Controller controller;
    SuperUser userLoggingIn;

    public SuperUserInterface(Controller controller, User userLoggingIn) {
        this.controller = controller;
        this.userLoggingIn = (SuperUser) userLoggingIn;
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


            int choice = UserInterface.drawMenu("SuperUser Menu", "Vælg en af følgende muligheder", "Afslut", options);

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
            password = inputForAddUser(title, "Indtast kodeord for medlemmet", "Næste", "Annullér");
            firstName = inputForAddUser(title, "Indtast fornavn for medlemmet", "Næste", "Annullér");
            lastName = inputForAddUser(title, "Indtast efternavn for medlemmet", "Næste", "Annullér");
            birthDate = inputForAddUser(title, "Indtast fødselsdato for medlemmet format (DD/MM/YYYY)", "Næste", "Annullér");
        } catch (IllegalArgumentException e) {
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
            password = inputForAddUser(title, "Indtast kodeord for træneren", "Næste", "Annullér");
            firstName = inputForAddUser(title, "Indtast fornavn for træneren", "Næste", "Annullér");
            lastName = inputForAddUser(title, "Indtast efternavn for træneren", "Næste", "Annullér");
        } catch (IllegalArgumentException e) {
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
            password = inputForAddUser(title, "Indtast kodeord for træneren", "Næste", "Annullér");
            firstName = inputForAddUser(title, "Indtast fornavn for træneren", "Næste", "Annullér");
            lastName = inputForAddUser(title, "Indtast efternavn for træneren", "Næste", "Annullér");
        } catch (IllegalArgumentException e) {
            return;
        }

        controller.createandAddTreasurertoDB(password, firstName, lastName);
    }


    private void editUserMenu() {
        int indexToEdit = 0;

        while (indexToEdit != -1) {
            indexToEdit = UserInterface.drawMenu("Redigér bruger", "Vælg bruger du gerne vil ændre", "Afslut", controller.getUserIDAndNameList());


            if (indexToEdit != -1) {
                ArrayList<String> userInfo = controller.getUserInfo(indexToEdit);
                UserInstance userType = controller.getUserType(indexToEdit);

                if (userType == UserInstance.COMPETITIVE) { //If the user is a member the list is longer than 7
                    userInfo.subList(7, userInfo.size()).clear();
                }

                if (userType == UserInstance.MEMBER || userType == UserInstance.COMPETITIVE) {
                    userInfo.remove(4); //Removes birthdate from editing list.
                }

                if (userType == UserInstance.TRAINER) {
                    userInfo.remove(5);
                }

                userInfo.remove(1); //Removes ID from editing list.
                userInfo.remove(0); //Removes user type from editing list.


                /*
                INDEX SPACES MEMBER:
                0: First name
                1: Last name
                2: Active/Passive
                3: Competitive/Recreational
                4: Arrears
                */

                /*
                INDEX SPACES TRAINER:
                0: First name
                1: Last name
                2: Senior/Junior Trainer
                */

                String newFirstName = null;
                String newLastName = null;
                String newActivePassive = null;
                String newCompetitiveRecreational = null;
                String newArrears = null;

                String isSeniorTrainer = null;

                int choice = 10;
                while (choice != -1) {
                    if (newFirstName != null) {
                        userInfo.set(0, "Fornavn: " + newFirstName);
                    }

                    if (newLastName != null) {
                        userInfo.set(1, "Efternavn: " + newLastName);
                    }

                    if (newActivePassive != null) {
                        userInfo.set(2, "Aktiv/Passiv: " + (newActivePassive.equals("true") ? "Aktiv" : "Passiv"));
                    }

                    if (newCompetitiveRecreational != null) {
                        userInfo.set(3, "Aktivitetsstatus: " + (newCompetitiveRecreational.equals("true") ? "Konkurrence" : "Motionist"));
                    }

                    if (newArrears != null) {
                        userInfo.set(4, "Restance: " + (newArrears.equals("true") ? "Ja" : "Nej"));
                    }

                    if(isSeniorTrainer != null) {
                        userInfo.set(2, "Træner type: " + (isSeniorTrainer.equals("true") ? "Senior" : "Junior"));

                    }

                    choice = UserInterface.drawMenu("Redigér bruger", "Tryk på det du gerne vil ændre", "Gem", userInfo);

                    switch (choice) {
                        case 0 -> {
                            newFirstName = UserInterface.inputMenu("Redigér fornavn", "Indtast nyt fornavn", "Redigér", "Annullér");
                        }
                        case 1 -> {
                            newLastName = UserInterface.inputMenu("Redigér efternavn", "Indtast nyt efternavn", "Redigér", "Annullér");
                        }
                        case 2 -> {
                            if (userType == UserInstance.MEMBER || userType == UserInstance.COMPETITIVE) {
                                newActivePassive = String.valueOf(UserInterface.yesNoMenu("Redigér pausestatus", "Er brugeren aktiv eller passiv?", "Aktiv", "Passiv"));
                            } else if (userType == UserInstance.TRAINER) {
                                isSeniorTrainer = String.valueOf(UserInterface.yesNoMenu("Redigér trænerstatus", "Er brugeren Senior eller Junior træner?", "Senior", "Junior"));
                            }
                        }
                        case 3 -> {
                            newCompetitiveRecreational = String.valueOf(UserInterface.yesNoMenu("Redigér aktivitetsniveau", "Er brugeren konkurrence eller motionist?", "Konkurrence", "Motionist"));
                        }
                        case 4 -> {
                            newArrears = String.valueOf(UserInterface.yesNoMenu("Redigér restancestatus", "Er brugeren i restance?", "Ja", "Nej"));
                        }
                        case -1 -> {
                            if (userType == UserInstance.MEMBER || userType == UserInstance.COMPETITIVE) {
                                controller.editMember(indexToEdit, newFirstName, newLastName, newActivePassive, newCompetitiveRecreational, newArrears);
                            } else if (userType == UserInstance.TRAINER) {
                                controller.editTrainer(indexToEdit, newFirstName, newLastName, isSeniorTrainer);
                            } else if (userType == UserInstance.TREASURER || userType == UserInstance.SUPER) {
                                controller.editAdmin(indexToEdit, newFirstName, newLastName);
                            }

                        }
                    }
                }


            }

        }
    }

    private void deleteUserMenu() {
        int indexToDelete = 0;
        while (indexToDelete != -1) {
            indexToDelete = UserInterface.drawMenu("Slet bruger", "Vælg bruger du gerne vil ændre", "Afslut", controller.getUserIDAndNameList());
            if (indexToDelete != -1) {
                controller.deleteUserFromDB(indexToDelete);
            }
        }
    }

    private void userOverviewMenu() {
        int indexToShow = 0;
        while (indexToShow != -1) {
            indexToShow = UserInterface.drawMenu("Brugeroversigt", "Vælg bruger du gerne vil se", "Afslut", controller.getUserIDAndNameList());
            if (indexToShow != -1) {
                ArrayList<String> userInfo = controller.getUserInfo(indexToShow);
                String displayInfo = String.join("\n", userInfo);
                UserInterface.smallWindow("Bruger info", displayInfo, "OK");
            }
        }
    }


    private String inputForAddUser(String title, String message, String okButtonText, String cancelButtonText) {
        String input = UserInterface.inputMenu(title, message, okButtonText, cancelButtonText);
        if (input == null) {
            throw new IllegalArgumentException();
        }
        return input;
    }
}
