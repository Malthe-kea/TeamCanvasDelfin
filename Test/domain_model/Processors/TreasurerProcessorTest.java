package domain_model.Processors;

import database.DBController;
import database.userDB.UserDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import user_domain.Member;
import user_domain.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TreasurerProcessorTest {

    @org.junit.jupiter.api.Test
    void getExpectedIncomeOfFiveMembers() {
        ArrayList<Member> dbController = new ArrayList<>();


        //ARRANGE
        Member user1 = new Member(1, "Malthe", "Tiufk?r", true, true, "29/09/1990", false);
        Member user2 = new Member(2, "Oscars", "B?fsandwich", true, true, "29/09/1990", false);
        Member user3 = new Member(3, "Qui", "Noah", true, true, "29/09/1990", false);
        Member user4 = new Member(4, "Steen", "Secher", true, true, "29/09/1990", false);
        Member user5 = new Member(5, "Max", "Mustermann", true, true, "29/09/1990", false);

        dbController.add(user1);
        dbController.add(user2);
        dbController.add(user3);
        dbController.add(user4);
        dbController.add(user5);


        //ACT
        double expectedResults = 8000;
        double actualResults = 0;
        for (Member m : dbController) {
            actualResults += m.getYearlyMembershipFee();
        }

        //ASSERT
        Assertions.assertEquals(expectedResults, actualResults);
    }

    @org.junit.jupiter.api.Test
    void geMembersInArrears() {
        //ARRANGE
        boolean isRunning = true;
        ArrayList<Member> dbController = new ArrayList<>();
        ArrayList<String> membersInArrears = new ArrayList<>();

        Member user1 = new Member(1, "Malthe", "Tiufk?r", true, true, "29/09/1990", true);
        Member user2 = new Member(2, "Oscars", "B?fsandwich", true, true, "29/09/1990", false);
        Member user3 = new Member(3, "Qui", "Noah", true, true, "29/09/1990", true);
        Member user4 = new Member(4, "Steen", "Secher", true, true, "29/09/1990", true);
        Member user5 = new Member(5, "Max", "Mustermann", true, true, "29/09/1990", false);

        dbController.add(user1);
        dbController.add(user2);
        dbController.add(user3);
        dbController.add(user4);
        dbController.add(user5);

        for (Member m : dbController) {
            if (m.isArrears()) {
                membersInArrears.add(m.toString());
            }
        }
            //ACT
            double expectedResults = 3;
            double actualResults = membersInArrears.size();

            //ASSERT
            Assertions.assertEquals(expectedResults, actualResults);
        }
    }
