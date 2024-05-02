import database.UserDB;
import user_domain.CompetitiveMember;

public class Main {
    public static void main(String[] args) {
        UserDB db = new UserDB();


        //Laver en instans for at teste om den printer dataen korrekt
        CompetitiveMember member = new CompetitiveMember(true, true, 25, false);
        //Kaller calculatePR metoden
        member.calculatePR();

    }
}