import database.DBController;
import database.competition_style_DB.CompetitionDB;
import database.competition_style_DB.StyleDB;
import database.rowNameEnum.CompetitiveMemberDBRowNames;
import database.rowNameEnum.DBRowNames;
import database.userDB.CompetitiveMemberDB;
import database.userDB.UserDB;
import domain_model.Processors.TreasurerProcessor;
import user_domain.CompetitiveMember;
import user_domain.Member;
import user_domain.User;
import user_domain.competition.Competition;
import user_domain.competition.Style;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //MAKE CONTROLLER

        TreasurerProcessor treasurerProcessor = new TreasurerProcessor();
        UserDB userDB = new UserDB();
        DBController dbController = new DBController();
        //System.out.println(userDB.getIDForNewUser());
        StyleDB styleDB = new StyleDB();
        CompetitionDB competitionDB = new CompetitionDB();
        Member member1 = new Member(1,"malthe", "Tiufkær", true, false,"29/09/1990", true);
        Member member2 = new Member(2,"malthe", "Tiufkær", true, false,"29/09/1990", true);
        Member member3 = new Member(3,"malthe", "Tiufkær", true, false,"29/09/1990", true);
        Member member4 = new Member(4,"malthe", "Tiufkær", true, false,"29/09/1990", true);

        userDB.addUserInDB(member1,"Bjerne");
        userDB.addUserInDB(member2,"Bjerne");
        userDB.addUserInDB(member3,"Bjerne");
        userDB.addUserInDB(member4,"Bjerne");
        //System.out.println("Den forventede indkomst for i år er: "+ treasurerProcessor.getExpectedIncome());
        //System.out.println("Følgende liste af folk i restance: \n" + treasurerProcessor.geMembersInArrears());
        System.out.println("printing" + styleDB.getStyleList());


        /*if(userDB.getUserFromID(1) instanceof CompetitiveMember member) {
            printCompetitiveMember(member);
            member.getCompetitionList().get(0).getStyleList().get(0).setPlacement(1);
            member.getCompetitionList().get(0).getStyleList().get(0).setStyleName("Long Stroke");
            member.getCompetitionList().get(0).setLocation("Østerbro");
            member.getCompetitionList().get(0).getStyleList().get(0).getTime().setDurationInSeconds(200);
            member.setFirstName("Laura");
            member.setLastName("Nøjden");
            member.setActiveMember(true);
            member.setArrears(false);
            userDB.editUserInDB(member);
            member = (CompetitiveMember) userDB.getUserFromID(1);
            printCompetitiveMember(member);
        }*/

        Competition comp = new Competition(1, "Hellerup", new ArrayList<>());
        Style style1 = new Style(1, comp.getID(), 1, "Butterfly", 2, 100);
        Style style2 = new Style(2, comp.getID(), 1, "Butterfly", 2, 100);
        Style style3 = new Style(3, comp.getID(), 1, "Butterfly", 2, 100);
        Style style4 = new Style(4, comp.getID(), 1, "Butterfly", 2, 100);

        comp.setStyleList(new ArrayList<>(Arrays.asList(style1, style2, style3, style4)));


        /*System.out.println(member.getDateOfBirth());
        System.out.println(member.getAge());*/

        //printCompetitiveMember((CompetitiveMember) userDB.getUserFromID(member.getUserID()));

        CompetitionDB compDB = new CompetitionDB();

        Competition compTest = compDB.getCompetitionFromID(1);



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