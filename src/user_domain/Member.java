package user_domain;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Member extends User {
    private boolean isActiveMember;
    DateTimeFormatter birthDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private boolean isCompetitive;
    private final LocalDate dateOfBirth;
    private boolean isArrears;
    private boolean isSenior;
    private final double yearlyMembershipFee;

    public Member(int userID,
                  String firstName,
                  String lastName,
                  boolean isActiveMember,
                  boolean isCompetitive,
                  String dateOfBirth,
                  boolean isArrears) {
        super(userID, firstName, lastName);
        this.dateOfBirth = LocalDate.parse(dateOfBirth, birthDateFormat);
        this.isActiveMember = isActiveMember;
        this.isCompetitive = isCompetitive;
        this.isArrears = isArrears;
        this.isSenior = (getAge() >= 18);
        this.yearlyMembershipFee = calculateFee();
    }

    private double calculateFee() {
        if (!isActiveMember) {
            return 500;
        } else if (getAge() < 18) {
            return 1000;
        } else if (getAge() >= 60) {
            return 1200;
        }
        return 1600;
    }

    public boolean isActiveMember() {
        return isActiveMember;
    }

    public boolean isCompetitive() {
        return isCompetitive;
    }

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public String getDateOfBirth() {
        return dateOfBirth.format(birthDateFormat);
    }

    public boolean isArrears() {
        return isArrears;
    }

    public boolean isSenior() {
        return isSenior;
    }

    public double getYearlyMembershipFee() {
        return yearlyMembershipFee;
    }

    public void setActiveMember(boolean activeMember) {
        isActiveMember = activeMember;
    }

    public void setCompetitive(boolean competitive) {
        isCompetitive = competitive;
    }

    public void setArrears(boolean arrears) {
        isArrears = arrears;
    }

    public void setSenior(boolean senior) {
        isSenior = senior;
    }

    @Override
    public String toString() {
        String result = super.toString() + "\n";
        result += "Fødselsdato: " + dateOfBirth + "\n";
        result += "Kontingent: " + yearlyMembershipFee + "\n" ;

        if (isActiveMember) {
            result+= "Aktivt member. " + "\n";
        } else {
            result+= "Inaktivt member. " + "\n";
        }
        if (isCompetitive) {
            result+= "Member er konkurrencesvømmer." + "\n";
        } else {
            result+= "Member er motionist." + "\n";
        }
        if (isArrears) {
            result+= "Member er i restance." + "\n";
        } else {
            result+= "Member er ikke i restance." + "\n";
        }
        if (isSenior) {
            result+= "Member er senior." + "\n";
        } else {
            result+= "Member er junior." + "\n";
        }

        if(!(this instanceof CompetitiveMember)) {
            return "Kontotype: Motonist\n" + result;
        } else {
            return result;
        }
    }

}
