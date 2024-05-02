package user_domain;
import java.util.ArrayList;

public class Trainer extends User {
    private ArrayList<CompetitiveMember> competitiveMemberList;
    private boolean isSeniorTrainer;

    public Trainer(int userID, String firstName, String lastName, boolean isSeniorTrainer) {
        super(userID, firstName, lastName);
        this.isSeniorTrainer = isSeniorTrainer;
    }

    public boolean isSeniorTrainer() {
        return isSeniorTrainer;
    }
}
