package domain_model.Processors;

import domain_model.UIDisplayInfo;

public interface Processor {


    UIDisplayInfo getMainMenu();
    UIDisplayInfo processMainMenuOption(int option);


    //TODO
    // 1. Opsæt metoder i dette interface der skal implementes i individuel processor, så de ensartet kan kaldes fra controlleren.
    // 2. Processoren skal returnere de værdier som skal vises tilbage til UI via Controlleren. Lige nu
    // returneres der ikke noget, og dermed er der high coupling, da Processoren er 100% afhængig at UserInterface. Derudover er
    // 3. Controlleren skal være ansvarlig for at sikre den rigtige processor bliver brugt. Processoren skal ikke returneres til UI.
}
