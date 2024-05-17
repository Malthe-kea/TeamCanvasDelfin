import database.DBController;
import domain_model.Processors.TrainerProcessor;
import domain_model.userInterface.UserInterface;
import user_domain.CompetitiveMember;
import user_domain.Member;
import user_domain.competition.Competition;
import user_domain.competition.Style;
import user_domain.competition.StyleCategories;

import java.security.spec.RSAOtherPrimeInfo;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        UserInterface UI = new UserInterface();
        UI.startProgram();

        /*TODO
            1. SuperUserProcessor editMember skal laves og opsættes til UI. - X
            2. SuperUserProcessor Brugeroversigt skal fixes med bug hvor kun superuser vises. - X
            3. toString metoder for alle brugertyper skal laves. De skal kalde deres parent class toString,
            og blot bygges videre på denne. - X
            4. MemberProcessor skal laves. Ved metoder hvor der vises data, returnér ArrayList<String>
            5. TrainerProcessor skal laves. Ved metoder hvor der vises data, returnér ArrayList<String>
            6. KassérProcessor skal laves. Ved metoder hvor der vises data, returnér ArrayList<String> - X
            7. Member, Trainer og Kassér processor metoder skal tilføjes til Controller.
            8. UI skal have en cancel button ved login siden, som lukker programmet. - X
            9. UI skal have "Ja/Nej" knapper istedet for input text. - X
            10. REFACTORING NÅR OVENSTÅENDE ER DONE.
            11. Læg overstående i Trello
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