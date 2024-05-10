package domain_model.Processors;

import database.*;
import domain_model.UIDisplayInfo;
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
        return String.valueOf(exptectedIncome);
    }

    public List<String> geMembersInArrears() {

        ArrayList<String> membersInArrearsList = new ArrayList<>();
        for (Member m : dbController.getListOfMembers()) {
            if (m.isArrears()) {
                membersInArrearsList.add(m.toString());
            }
        }
        return membersInArrearsList;
    }

    @Override
    public UIDisplayInfo getMainMenu() {
        return null;
    }

    @Override
    public UIDisplayInfo processMainMenuOption(int option) {
        return null;
    }
}


