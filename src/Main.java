import database.UserDB;
import domain_model.Processors.SuperUserProcessor;
import user_domain.Member;
import user_domain.Trainer;

public class Main {
    public static void main(String[] args) {
        SuperUserProcessor spu = new SuperUserProcessor();
        System.out.println(spu.editUserFromDB(1, "Noah"));

    }
}