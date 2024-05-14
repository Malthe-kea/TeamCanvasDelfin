package user_domain;

public class Treasurer extends User {

    public Treasurer(int userID, String firstName, String lastName) {
        super(userID, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Kontotype: Kassér\n"
                + super.toString();
    }
}
