import domain_model.UserInterface;

public class Main {
    public static void main(String[] args) {
        UserInterface UI = new UserInterface();

        UI.startProgram();

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