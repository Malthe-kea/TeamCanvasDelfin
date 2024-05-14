import database.DBController;
import domain_model.userInterface.UserInterface;
import user_domain.competition.Competition;

public class Main {
    public static void main(String[] args) {

        UserInterface UI = new UserInterface();
        UI.startProgram();

        /*TODO
            1. SuperUserProcessor editMember skal laves og ops�ttes til UI.
            2. SuperUserProcessor Brugeroversigt skal fixes med bug hvor kun superuser vises.
            3. toString metoder for alle brugertyper skal laves. De skal kalde deres parent class toString,
            og blot bygges videre p� denne.
            4. MemberProcessor skal laves. Ved metoder hvor der vises data, return�r ArrayList<String>
            5. TrainerProcessor skal laves. Ved metoder hvor der vises data, return�r ArrayList<String>
            6. Kass�rProcessor skal laves. Ved metoder hvor der vises data, return�r ArrayList<String>
            7. Member, Trainer og Kass�r processor metoder skal tilf�jes til Controller.
            8. UI skal have en cancel button ved login siden, som lukker programmet.
            9. UI skal have "Ja/Nej" knapper istedet for input text.
            10. REFACTORING N�R OVENST�ENDE ER DONE.
        */


    }



//    static void printCompetitiveMember(CompetitiveMember member) {
//        System.out.println(member.getUserID());
//        System.out.println(member.getFirstName());
//        System.out.println(member.getLastName());
//        System.out.println(member.getAge());
//        System.out.println(member.isActiveMember());
//        System.out.println(member.isArrears());
//        System.out.println(member.isCompetitive());
//        System.out.println(member.getYearlyMembershipFee());
//        for (Competition comp : member.getCompetitionList()) {
//            System.out.println(comp.getID());
//            System.out.println(comp.getLocation());
//            for (Style style : comp.getStyleList()) {
//                System.out.println(style.getStyleName());
//                System.out.println(style.getPlacement());
//                System.out.println(style.getTime().getDisplayTime());
//            }
//        }
//    }
}