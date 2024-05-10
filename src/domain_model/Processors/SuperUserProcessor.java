package domain_model.Processors;

import database.Database;
import database.DBController;
import domain_model.*;
import database.userDB.UserDB;
import user_domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;

public class SuperUserProcessor implements Processor {
    private Scanner userInput;
    DBController dbController;
    private ArrayList<User> userListArr;
    boolean programRunning = true;

    public SuperUserProcessor(DBController dbController) {
        this.dbController = dbController;
        userInput = new Scanner(System.in);
    }

    @Override
    public UIDisplayInfo getMainMenu() {


        ArrayList<String> options = new ArrayList<>(List.of(
                "Tilføj medlem",
                "Tilføj træner",
                "Tilføj konkurrence-medlem",
                "Tilføj kasserer",
                "Ændre medlemsoplysninger",
                "Slet medlem",
                "Se brugeroversigt"));


        return new UIDisplayInfo("SuperUser Menu","Vælg en af følgende muligheder", DisplayType.MENU ,options);


    }

    @Override
    public UIDisplayInfo processMainMenuOption(int option) {
        return null;
    }

    public ArrayList createSuperUser() {
        ArrayList<SuperUser> superUser = new ArrayList<>();
        superUser.add(new SuperUser(1, "flemming", "Rasmussen"));
        return superUser;
    }

    public ArrayList<String> getUserList() {
        ArrayList<String> userList = new ArrayList<>();
        ArrayList<User> allUsers = dbController.getListOfAllUsers();
        for (User u : allUsers) {
            UserInstance userInstance = DelfinUtil.checkUserInstance(u);
            switch (userInstance) {
                case MEMBER -> {
                    userList.add(((Member)u).toString());
                }
                case COMPETITIVE -> {
                    userList.add(((CompetitiveMember)u).toString());
                }
                case TRAINER -> {
                    userList.add(((Trainer)u).toString());
                }
                case SUPER, TREASURER -> {
                    userList.add(u.toString());
                }
            }
        }
        return userList;
    }

    public void CreateandAddMembertoDB(String password, String firstNames, String lastNames, boolean activePassiveInput, String birthDate, boolean isCompetitiveInput, boolean isArrearsInput) {

        Boolean isActiveMember = activePassiveInput;
        Boolean isCompetitive = isCompetitiveInput;
        Boolean isArrears = isArrearsInput;
        //LocalDate dob = LocalDate.parse(birthDate);

        //TODO new member tager imod dateOfBirth som en string, det skal være LocalDate.
        User memberToAdd = new Member(dbController.getIDForNewUser(), firstNames, lastNames, isActiveMember, isCompetitive, birthDate, isArrears);
        dbController.addUserToDB(memberToAdd, password);
    }

    public void CreateandAddTrainertoDB(String password, String firstNames, String lastNames, boolean isSeniorTrainer) {

        User trainerToAdd = new Trainer(dbController.getIDForNewUser(), firstNames, lastNames, isSeniorTrainer);
        dbController.addUserToDB(trainerToAdd, password);
    }

    public void CreateandAddCompetitiveMembertoDB(String password, String firstNames, String lastName, boolean isActiveMemberInput, boolean isCompetitiveInput, String birthDate, boolean isArrearsInput) {

        Boolean isActiveMember = isActiveMemberInput;
        Boolean isCompetitive = isCompetitiveInput;
        Boolean isArrears = isArrearsInput;
        //TODO new competitiveMember tager imod dateOfBirth som en string, det skal være LocalDate.
//        LocalDate dob = LocalDate.parse(birthDate);

//Her skal laves en metode, der tager seneste userID fra DB'en og incrementer den med 1.
        //testDB.add(new CompetitiveMember(3, "Susse", "Sonnegaard", true, true, calculateAge(dob), false));
        User competitiveMemberToAdd = new CompetitiveMember(dbController.getIDForNewUser(), firstNames, lastName, isActiveMember, isCompetitive, birthDate, isArrears);
        dbController.addUserToDB(competitiveMemberToAdd, password);
    }

    public void CreateAndAddTreasurertoDB(String password, String firstNames, String lastNames) {

        User treasurerToAdd = new Treasurer(dbController.getIDForNewUser(), firstNames, lastNames);
        dbController.addUserToDB(treasurerToAdd, password);
    }

    //TODO fiks den her metode, så den ikke returnerer noget.
    public void editUserFromDB(int idToEdit, String firstName) {
        UserDB db = new UserDB();
        User userToEdit = db.getUserFromID(idToEdit);

        if (userToEdit.getFirstName().equalsIgnoreCase(firstName)) {

            String command = userInput.nextLine().toLowerCase();
            String commandPrompt = userInput.nextLine().toLowerCase();
            switch (DelfinUtil.checkUserInstance(userToEdit)) {

                case SUPER, TREASURER -> {
                    print("""
                            1. Rediger fornavn
                            2. Rediger efternavn
                            """);
                    commandPrompt = userInput.nextLine().toLowerCase();
                    userToEdit.setFirstName(commandPrompt);
                }
                case MEMBER, COMPETITIVE -> {
                    print("""
                            1. Rediger fornavn
                                 2. Rediger efternavn
                                     3. Rediger aktivitetsstatus
                            4. Konkurrence/Motionist
                            5. Restancestatus.
                            """);
                    if (DelfinUtil.checkUserInstance(userToEdit) == UserInstance.COMPETITIVE) {
                        //TODO når Style.class er oprettet kan vi tilføje discipliner herunder.
                        print("""
                                6. 
                                """);
                    }
                    commandPrompt = userInput.nextLine().toLowerCase();
                    userToEdit.setLastName(commandPrompt);
                }
                case TRAINER -> {
                    commandPrompt = userInput.nextLine().toLowerCase();
                    Trainer trainer = (Trainer) userToEdit;

                    print("""
                            1. Rediger fornavn
                            2. Rediger efternavn
                            3. Senior/Junior
                            """);
                    //TODO lav switchcase, der passer til menuen.

                    boolean seniorOrNot = (commandPrompt.equalsIgnoreCase("senior")) ? true : false;
                    trainer.setSeniorTrainer(seniorOrNot);

                }
                default -> {
                    print("invalid input");
                }

            }
            db.editUserInDB(userToEdit);
        }

    }

    public void deleteUserFromDB(int indexInList) {

        ArrayList<User> allUsers = dbController.getListOfAllUsers();
        User userToDelete = allUsers.get(indexInList);
        dbController.removeUserFromDB(userToDelete);

    }

    public String getUserInfo(int indexToEdit) {
        ArrayList<User> allUsers = dbController.getListOfAllUsers();
        User userForInfo = allUsers.get(indexToEdit);

        switch (DelfinUtil.checkUserInstance(userForInfo)) {
            case MEMBER -> {
                return ((Member)userForInfo).toString();
            }
            case COMPETITIVE -> {
                return ((CompetitiveMember)userForInfo).toString();
            }
            case TRAINER -> {
                return ((Trainer)userForInfo).toString();
            }
            case SUPER, TREASURER -> {
                return userForInfo.toString();
            }
        }
        return "null";
    }


    public Database getUserFromDB(int idToEdit) {
        //TODO send DB return
        String command = "1";
        print("""
                Hvilket brugeroversigt vil du gerne se?
                1. Crawl
                2. Rygcrawl
                3. Brystsvømning
                4. Butterfly
                5. Aktive/Passive medlemmer
                6. Juniorer/Seniorer
                """);
        while (programRunning)
            switch (command) {
                case "1" -> {
                    print("Get XYZ-database");
                }
                case "2" -> {
                    print("Get XYZ-database");
                }
                case "3" -> {
                    print("Get XYZ-database");
                }
                case "4" -> {
                    print("Get XYZ-database");
                }
                case "5" -> {
                    print("Get XYZ-database");
                }
                case "6" -> {
                    print("Get XYZ-database");
                }
                default -> {
                    print("invalid input");
                }
            }
        return null;
    }


    public static int calculateAge(LocalDate dob) {
        LocalDate curDate = LocalDate.now();

        if ((dob != null) && (curDate != null)) {
            return Period.between(dob, curDate).getYears();
        } else {
            return 0;
        }
    }

    private void print(String s) {
        UserInterfaceEsra.print(s);
    }
}
