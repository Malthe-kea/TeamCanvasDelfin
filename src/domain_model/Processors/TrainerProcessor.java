package domain_model.Processors;

import database.DBController;
import database.competition_style_DB.StyleDB;
import user_domain.*;

import java.util.ArrayList;
import java.util.List;

public class TrainerProcessor implements Processor {
    StyleDB styleDB = new StyleDB();

    public TrainerProcessor() {}
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
