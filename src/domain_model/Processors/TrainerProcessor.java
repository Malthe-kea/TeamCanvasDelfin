package domain_model.Processors;

import database.DBController;
import domain_model.userInterface.UIDisplayInfo;

public class TrainerProcessor implements Processor {

    DBController dbController;

    public TrainerProcessor(DBController dbController) {
        this.dbController = dbController;
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