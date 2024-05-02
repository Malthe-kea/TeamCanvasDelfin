import database.UserDB;
import user_domain.Member;
import user_domain.Trainer;

public class Main {
    public static void main(String[] args) {
        UserDB db = new UserDB();

        Member tr = new Member(1,"My 143","YOLO",true,true,12,false);
        db.editUserInDB(tr);
    }
}