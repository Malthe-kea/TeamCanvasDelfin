package user_domain;

public class Member extends User {
    private boolean isActiveMember;
    private boolean isCompetitive;
    private int age;
    private boolean isArrears;
    private boolean isSenior;
    private double yearlyMembershipFee;

    public Member(boolean isActiveMember, boolean isCompetitive, int age, boolean isArrears) {
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

    public boolean isArrears() {
        return isArrears;
    }
    public double getYearlyMembershipFee() {
        return yearlyMembershipFee;
    }
}
