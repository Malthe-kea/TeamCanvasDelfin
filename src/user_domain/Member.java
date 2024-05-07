package user_domain;

public class Member extends User {
    private boolean isActiveMember;
    private boolean isCompetitive;
    private int age;
    private boolean isArrears;
    private boolean isSenior;
    private double yearlyMembershipFee;

    public Member(int userID, String firstName, String lastName, boolean isActiveMember, boolean isCompetitive, int age, boolean isArrears) {
        super(userID, firstName, lastName);
        this.isActiveMember = isActiveMember;
        this.isCompetitive = isCompetitive;
        this.age = age;
        this.isArrears = isArrears;
        this.isSenior = (age >= 18);
        this.yearlyMembershipFee = calculateFee();
    }
    private double calculateFee() {
        if (!isActiveMember) {
            return 500;
        } else if (age < 18) {
            return 1000;
        } else if (age >= 60) {
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
        return age;
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
}
