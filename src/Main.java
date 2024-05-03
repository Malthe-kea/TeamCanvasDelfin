import database.UserDB;
import domain_model.UserInterface;

public class Main {
    public static void main(String[] args) {
        UserDB db = new UserDB();
        UserInterface ui = new UserInterface();
        ui.startProgram();

    }
}