import database.MemberDB;
import database.TrainerDB;
import database.UserDB;
import user_domain.Member;
import user_domain.SuperUser;
import user_domain.Trainer;
import user_domain.User;

public class Main {
    public static void main(String[] args) {
        UserDB db = new UserDB();
        if (db.getUserFromLastName("van wagenen") instanceof Trainer) {
            System.out.println("CREATION SUCCESSFULL");
        }

        for (User user : db.getListOfUsers()) {
            System.out.println(user.getFirstName());
        }

    }
}