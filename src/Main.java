import database.UserDB;
import user_domain.CompetitiveMember;
import user_domain.Trainer;

public class Main {
    public static void main(String[] args) {
        UserDB db = new UserDB();

        Trainer trainer = new Trainer(true); // Pass true or false depending on whether it's a senior trainer or not

        trainer.printFastestTimes();

    }
}