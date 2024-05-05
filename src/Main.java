import database.competition_style_DB.CompetitionDB;
import database.rowNameEnum.CompetitiveMemberDBRowNames;
import database.rowNameEnum.DBRowNames;
import database.userDB.CompetitiveMemberDB;
import database.userDB.UserDB;
import user_domain.CompetitiveMember;
import user_domain.competition.Competition;
import user_domain.competition.Style;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDB userDB = new UserDB();

        if(userDB.getUserFromID(1) instanceof CompetitiveMember member) {
            System.out.println(member.getUserID());
            System.out.println(member.getFirstName());
            System.out.println(member.getLastName());
            System.out.println(member.getAge());
            System.out.println(member.isActiveMember());
            System.out.println(member.isArrears());
            System.out.println(member.isCompetitive());
            System.out.println(member.getYearlyMembershipFee());
            for (Competition comp : member.getCompetitionList()) {
                System.out.println(comp.getID());
                System.out.println(comp.getLocation());
                for (Style style : comp.getStyleList()) {
                    System.out.println(style.getStyleName());
                    System.out.println(style.getPlacement());
                    System.out.println(style.getTime().getDisplayTime());
                }
            }
        }


    }
}