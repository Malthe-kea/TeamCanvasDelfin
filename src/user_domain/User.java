package user_domain;

public abstract class User {

    private int userID;
    private String firstName;
    private String lastName;

    public User(int userID, String firstName, String lastName) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}