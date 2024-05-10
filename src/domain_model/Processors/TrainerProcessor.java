package domain_model.Processors;

import database.DBController;
import database.competition_style_DB.StyleDB;
import domain_model.UIDisplayInfo;
import user_domain.*;

import java.util.ArrayList;
import java.util.List;

import database.DBController;

public class TrainerProcessor implements Processor {

    DBController dbController;

    public TrainerProcessor(DBController dbController) {
        this.dbController = dbController;
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




    //TODO tr?ner skal oprette st?vner for members. Det er ikke noget SuperUser g?r.

//
//    public List<String> getBackStrokeTopFive(){
//        ArrayList<StyleDB> backStrokeTopFive = new ArrayList<>();
//        styleDB.getStyleList();
//
//        for (Member m : styleDB){
//
//
//        }
//
//
//
//        return backStrokeTopFive;
//    }
//}
