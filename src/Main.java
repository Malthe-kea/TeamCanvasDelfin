import domain_model.Processors.SuperUserProcessor;
import domain_model.UserInterface;
import database.UserDB;
import user_domain.CompetitiveMember;
import user_domain.Trainer;

public class Main {
    public static void main(String[] args) {
        SuperUserProcessor spu = new SuperUserProcessor();
        System.out.println(spu.editUserFromDB(1, "Noah"));

    }
}