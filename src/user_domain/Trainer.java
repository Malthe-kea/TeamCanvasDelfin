package user_domain;
import java.util.ArrayList;

public class Trainer extends User {
    private ArrayList<CompetitiveMember> competitiveMemberList;
    private boolean isSeniorTrainer;

    public Trainer(boolean isSeniorTrainer) {
        this.isSeniorTrainer = isSeniorTrainer;
    }

    public boolean isSeniorTrainer() {
        return isSeniorTrainer;
    }
}
