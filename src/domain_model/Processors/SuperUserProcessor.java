package domain_model.Processors;

import database.Database;
import database.UserDB;
import user_domain.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class SuperUserProcessor implements Processor {
    private Scanner userInput;
    private ArrayList testDB = new ArrayList<>();
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

    public ArrayList CreateandAddMembertoDB() {

        print("Indtast fornavne");
        String firstNames = userInput.nextLine();

        print("indtast efternavne");
        String lastNames = userInput.nextLine();

        print("Aktiv/passiv");
        String activePassiveInput = userInput.nextLine();
        Boolean isActiveMember = Boolean.parseBoolean(activePassiveInput);

        print("Indtast f�dselsdato i ����-MM-DD");
        String birthDate = userInput.nextLine();
        LocalDate dob = LocalDate.parse(birthDate);


        testDB.add(new Member(1, firstNames, lastNames, isActiveMember, true, calculateAge(dob), false));

        return testDB;
    }

    public ArrayList CreateandAddTrainertoDB() {

        print("Indtast fornavne");
        String firstNames = userInput.nextLine();

        print("indtast efternavne");
        String lastNames = userInput.nextLine();

        print("Aktiv/passiv");

        print("Indtast f�dselsdato i ����-MM-DD");
        String birthDate = userInput.nextLine();
        LocalDate dob = LocalDate.parse(birthDate);

//Her skal laves en metode, der tager seneste userID fra DB'en og incrementer den med 1.
        testDB.add(new Trainer(2, "Flemming", "Rasmussen", true));

        return testDB;
    }

    public ArrayList CreateandAddCompetitiveMembertoDB() {

        print("Indtast fornavne");
        String firstNames = userInput.nextLine();

        print("indtast efternavne");
        String lastNames = userInput.nextLine();

        print("Aktiv/passiv");

        print("Indtast f�dselsdato i ����-MM-DD");
        String birthDate = userInput.nextLine();
        LocalDate dob = LocalDate.parse(birthDate);

//Her skal laves en metode, der tager seneste userID fra DB'en og incrementer den med 1.
        testDB.add(new CompetitiveMember(3, "Susse", "Sonnegaard", true, true, calculateAge(dob), false));

        return testDB;
    }

    public ArrayList CreateandAddTreasurertoDB() {

        print("Indtast fornavne");
        String firstNames = userInput.nextLine();

        print("indtast efternavne");
        String lastNames = userInput.nextLine();

        print("Aktiv/passiv");

        print("Indtast f�dselsdato i ����-MM-DD");
        String birthDate = userInput.nextLine();
        LocalDate dob = LocalDate.parse(birthDate);

//Her skal laves en metode, der tager seneste userID fra DB'en og incrementer den med 1.
        testDB.add(new Treasurer(4, "Steen", "Secher"));

        return testDB;
    }

    public boolean editUserFromDB(int idToEdit, String firstName) {
        UserDB db = new UserDB();
        User userToEdit = db.getUserFromID(idToEdit);

        if(userToEdit.getFirstName().equalsIgnoreCase(firstName)) {
            print("""
                    Hvilket parameter vil du �ndre?
                    1. Fornavn(e)
                    2. Efternavn
                    3. Aktivitetsstatus
                    4.???
                    """);
            String command = userInput.nextLine().toLowerCase();
            //while (programRunning) {
                switch (command) {
                    case "1" -> {
                        //TODO
                        //  Ny scanner variabel som tager input fra bruger til det navn man gerne vil �ndre til.
                        userToEdit.setFirstName("Test igen");
                    }
                    case "2" -> {
                    }
                    case "3" -> {
                    }
                    case "4" -> {
                    }
                    default -> {
                        print("invalid input");
                    }

                }

                return db.editUserInDB(userToEdit);

            //}
        }


        //User userToEdit = null;



        /*for (User u : userListArr) {
            if (u.getUserID() == idToEdit && u.getFirstName() == firstName) {

                break;
            }
        }*/

        return false;
    }

    public void deleteUserFromDB(int idToEdit, String firstName) {
        User userToDelete = null;
        String command = userInput.nextLine().toLowerCase();

        for (User u : userListArr) {
            if (u.getUserID() == idToEdit && u.getFirstName() == firstName) {
                //TODO slet fra DB
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
                3. Brystsv�mning
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
        System.out.println(s);
    }
}
