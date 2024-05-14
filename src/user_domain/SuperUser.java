package user_domain;

public class SuperUser extends User {

    public SuperUser(int userID, String firstName, String lastName) {
        super(userID, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Kontotype: Superbruger\n"
                + super.toString();
    }
}
