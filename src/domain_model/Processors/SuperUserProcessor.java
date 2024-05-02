package domain_model.Processors;

import database.UserDB;
import user_domain.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class SuperUserProcessor implements Processor {
    private Scanner userInput = new Scanner(System.in);
    private ArrayList testDB = new ArrayList<>();
    private ArrayList<User> userListArr;

    public SuperUserProcessor() {

    }

    public ArrayList createSuperUser() {
        ArrayList<SuperUser> superUser = new ArrayList<>();
        superUser.add(new SuperUser(1, "flemming", "Rasmussen"));
        return superUser;
    }

    public ArrayList CreateandAddMembertoDB() {

        System.out.println("Indtast fornavne");
        String firstNames = userInput.nextLine();

        System.out.println("indtast efternavne");
        String lastNames = userInput.nextLine();

        System.out.println("Aktiv/passiv");
        String activePassiveInput = userInput.nextLine();
        Boolean isActiveMember = Boolean.parseBoolean(activePassiveInput);

        System.out.println("Indtast fødselsdato i ÅÅÅÅ-MM-DD");
        String birthDate = userInput.nextLine();
        LocalDate dob = LocalDate.parse(birthDate);


        testDB.add(new Member(1, firstNames, lastNames, isActiveMember, true, calculateAge(dob), false));

        return testDB;
    }

    public ArrayList CreateandAddTrainertoDB() {

        System.out.println("Indtast fornavne");
        String firstNames = userInput.nextLine();

        System.out.println("indtast efternavne");
        String lastNames = userInput.nextLine();

        System.out.println("Aktiv/passiv");

        System.out.println("Indtast fødselsdato i ÅÅÅÅ-MM-DD");
        String birthDate = userInput.nextLine();
        LocalDate dob = LocalDate.parse(birthDate);

//Her skal laves en metode, der tager seneste userID fra DB'en og incrementer den med 1.
        testDB.add(new Trainer(2, "lsdfklr1fwdslfææ", "Flemming", "Rasmussen", true));

        return testDB;
    }

    public ArrayList CreateandAddCompetitiveMembertoDB() {

        System.out.println("Indtast fornavne");
        String firstNames = userInput.nextLine();

        System.out.println("indtast efternavne");
        String lastNames = userInput.nextLine();

        System.out.println("Aktiv/passiv");

        System.out.println("Indtast fødselsdato i ÅÅÅÅ-MM-DD");
        String birthDate = userInput.nextLine();
        LocalDate dob = LocalDate.parse(birthDate);

//Her skal laves en metode, der tager seneste userID fra DB'en og incrementer den med 1.
        testDB.add(new CompetitiveMember(3, "Susse", "Sonnegaard", true, true, calculateAge(dob), false));

        return testDB;
    }

    public ArrayList CreateandAddTreasurertoDB() {

        System.out.println("Indtast fornavne");
        String firstNames = userInput.nextLine();

        System.out.println("indtast efternavne");
        String lastNames = userInput.nextLine();

        System.out.println("Aktiv/passiv");

        System.out.println("Indtast fødselsdato i ÅÅÅÅ-MM-DD");
        String birthDate = userInput.nextLine();
        LocalDate dob = LocalDate.parse(birthDate);

//Her skal laves en metode, der tager seneste userID fra DB'en og incrementer den med 1.
        testDB.add(new Treasurer(4, "1+23kdsf+2", "Steen", "Secher"));

        return testDB;
    }


    public void editUserFromDB(int idToEdit, String firstName) {
        User userToEdit = null;

        for (User u : userListArr) {
            if (u.getUserID() == idToEdit && u.getFirstName() == firstName) {
                String command = new Scanner(System.in);
                print("""
                         Hvilket parameter vil du ændre?
                         1. Fornavn(e)
                         2. Efternavn
                         3. Aktivitetsstatus
                         4.???
                         """);
                switch (command){
                    case "1" -> {

                    }
                }


                u.setFirstName("Test");
                break;
            }
        }
        return null;
    }


    public void getUserFromDB() {

    }

    public void deleteUserFromDB() {

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
