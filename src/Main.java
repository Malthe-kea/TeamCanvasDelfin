import database.UserDB;
import user_domain.Member;
import user_domain.SuperUser;

public class Main {
    public static void main(String[] args) {
        UserDB db = new UserDB();

        if(db.getUserFromID(1) instanceof Member) {
            System.out.println("CREATION SUCCESSFULL");
        }

    }
}