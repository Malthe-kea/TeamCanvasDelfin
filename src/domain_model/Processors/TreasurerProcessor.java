package domain_model.Processors;

import user_domain.Member;

import java.util.ArrayList;
import java.util.List;

import database.DBController;

public class TreasurerProcessor implements Processor {
    DBController dbController;

    public TreasurerProcessor(DBController dbController) {
        this.dbController = dbController;
    }

    public String getExpectedIncome() {
        double exptectedIncome = 0;

        for (Member m : dbController.getListOfMembers()) {
            exptectedIncome += m.getYearlyMembershipFee();
        }
        return exptectedIncome+"DKK";
    }

    public ArrayList<String> getMembersInArrears() {

        ArrayList<String> membersInArrearsList = new ArrayList<>();
        for (Member m : dbController.getListOfMembers()) {
            if (m.isArrears()) {
                membersInArrearsList.add("ID " + m.getUserID() + ": " + m.getFirstName() + " " + m.getLastName());
            }
        }
        return membersInArrearsList;
    }
}


