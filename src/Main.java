import database.UserDB;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.startProgram();
        SuperUserProcessor spu = new SuperUserProcessor();
        System.out.println(spu.editUserFromDB(1, "Noah"));

    }
}