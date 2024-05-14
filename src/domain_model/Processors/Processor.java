package domain_model.Processors;


public interface Processor {


    //TODO
    // 1. Ops�t metoder i dette interface der skal implementes i individuel processor, s� de ensartet kan kaldes fra controlleren.
    // 2. Processoren skal returnere de v�rdier som skal vises tilbage til UI via Controlleren. Lige nu
    // returneres der ikke noget, og dermed er der high coupling, da Processoren er 100% afh�ngig at UserInterface. Derudover er
    // 3. Controlleren skal v�re ansvarlig for at sikre den rigtige processor bliver brugt. Processoren skal ikke returneres til UI.
}
