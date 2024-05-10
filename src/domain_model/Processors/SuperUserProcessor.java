package domain_model.Processors;

import database.Database;
import database.DBController;
import domain_model.DelfinUtil;
import domain_model.UserInstance;
import domain_model.UserInterface;
import database.userDB.UserDB;
import user_domain.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;

public class SuperUserProcessor implements Processor {
    private Scanner userInput;
    DBController dbController = new DBController();
    private ArrayList<User> userListArr;
    boolean programRunning = true;

    public SuperUserProcessor() {
        userInput = new Scanner(System.in);
    }

    public ArrayList createSuperUser() {
        ArrayList<SuperUser> superUser = new ArrayList<>();
        superUser.add(new SuperUser(1, "flemming", "Rasmussen"));
        return superUser;
    }

    public void CreateandAddMembertoDB(String password, int userID, String firstNames, String lastNames, String activePassiveInput, String birthDate, String isCompetitiveInput, String isArrearsInput) {

        Boolean isActiveMember = Boolean.parseBoolean(activePassiveInput);
        Boolean isCompetitive = Boolean.parseBoolean(isCompetitiveInput);
        Boolean isArrears = Boolean.parseBoolean(isArrearsInput);
        LocalDate dob = LocalDate.parse(birthDate);

        //TODO new member tager imod dateOfBirth som en string, det skal være LocalDate.
        User memberToAdd = new Member(userID, firstNames, lastNames, isActiveMember, isCompetitive, birthDate, isArrears);
        dbController.addUserToDB(memberToAdd, password);
    }

    public void CreateandAddTrainertoDB(int userID, String password, String firstNames, String lastNames, String isSeniorTrainerInput) {

        Boolean isSeniorTrainer = Boolean.parseBoolean(isSeniorTrainerInput);
        User trainerToAdd = new Trainer(userID, firstNames, lastNames, isSeniorTrainer);
        dbController.addUserToDB(trainerToAdd, password);
    }

    public void CreateandAddCompetitiveMembertoDB(int userID, String password, String firstNames, String lastName, String isActiveMemberInput, String isCompetitiveInput, String birthDate, String isArrearsInput, ArrayList competitionList) {

        Boolean isActiveMember = Boolean.parseBoolean(isActiveMemberInput);
        Boolean isCompetitive = Boolean.parseBoolean(isCompetitiveInput);
        Boolean isArrears = Boolean.parseBoolean(isArrearsInput);
        //TODO new competitiveMember tager imod dateOfBirth som en string, det skal være LocalDate.
        LocalDate dob = LocalDate.parse(birthDate);

//Her skal laves en metode, der tager seneste userID fra DB'en og incrementer den med 1.
        //testDB.add(new CompetitiveMember(3, "Susse", "Sonnegaard", true, true, calculateAge(dob), false));
        User competitiveMemberToAdd = new CompetitiveMember(userID, firstNames, lastName, isActiveMember, isCompetitive, birthDate, isArrears, competitionList);
        dbController.addUserToDB(competitiveMemberToAdd, password);
    }

    public void CreateAndAddTreasurertoDB(int userID, String password, String firstNames, String lastNames) {

        User treasurerToAdd = new Treasurer(userID, firstNames, lastNames);
        dbController.addUserToDB(treasurerToAdd, password);
    }

    //TODO fiks den her metode, så den ikke returnerer noget.
    public boolean editUserFromDB(int idToEdit, String firstName) {
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
            return db.editUserInDB(userToEdit);
        }


        return false;
    }

    public void deleteUserFromDB(int idToEdit, String firstName) {
        User userToDelete = null;
        String command = userInput.nextLine().toLowerCase();

        for (User u : userListArr) {
            if (u.getUserID() == idToEdit && u.getFirstName() == firstName) {
                dbController.removeUserFromDB(u);
            }
        }
    }


    public Database getUserFromDB(int idToEdit) {
        //TODO send DB return
        String command = userInput.nextLine().toLowerCase();
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
        UserInterface.print(s);
    }
}
