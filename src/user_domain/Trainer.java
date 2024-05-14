package user_domain;
import java.util.ArrayList;
import java.util.Collections;

public class Trainer extends User {
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

    public void generateSampleData() {
        sortingExample = new ArrayList<>();
        sortingExample.add(new SortExampleTest("Event 1", "Location A", 10.5));
        sortingExample.add(new SortExampleTest("Event 2", "Location B", 9.8));
        sortingExample.add(new SortExampleTest("Event 3", "Location C", 11.2));
        sortingExample.add(new SortExampleTest("Event 4", "Location D", 22.6));
        sortingExample.add(new SortExampleTest("Event 5", "Location E", 17.5));
        sortingExample.add(new SortExampleTest("Event 6", "Location F", 11.9));
        sortingExample.add(new SortExampleTest("Event 7", "Location H", 21.9));
    }

    public void printFastestTimes() {
        //Midlertidig 'database'
        generateSampleData();

        //Sorterer
        Collections.sort(sortingExample, (c1, c2) -> Double.compare(c1.tid, c2.tid));

        //Printer 5 hurtigste tider
        int count = 0;
        for (SortExampleTest competition : sortingExample) {
            if (count >= 5) {
                break;
            }
            System.out.println(competition.staevne + " - " + competition.lokation + " - " + competition.tid);
            count++;
        }
    }

    @Override
    public String toString() {
        return "Kontotype: Træner\n"
                + super.toString() +"\n"+
                "Er senior træner (ja/nej): " + isSeniorTrainer;
    }
}
