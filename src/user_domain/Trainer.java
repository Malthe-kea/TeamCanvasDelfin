package user_domain;

import java.util.ArrayList;
import java.util.Collections;

public class Trainer extends User {
    private ArrayList<CompetitiveMember> competitiveMemberList;
    private boolean isSeniorTrainer;
    private ArrayList<SortExampleTest> sortingExample;


    public Trainer(int userID, String firstName, String lastName, boolean isSeniorTrainer) {
        super(userID, firstName, lastName);
        this.isSeniorTrainer = isSeniorTrainer;
    }


    public boolean isSeniorTrainer() {
        return isSeniorTrainer;
    }

    public void setSeniorTrainer(boolean seniorTrainer) {
        isSeniorTrainer = seniorTrainer;
    }

    @Override
    public String toString() {
        String result = "Kontotype: Træner\n" +
                super.toString() + "\n";
        if (isSeniorTrainer = true) {
            result += "Senior træner. " + "\n";
        }
        if (competitiveMemberList != null) {
            result += "Liste over konkurrencesvømmere: " + "\n";
        }
        return result;
    }
}
