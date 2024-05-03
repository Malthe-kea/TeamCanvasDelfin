package database.rowNameEnum;

//Interface for all rowNameEnums. Needed to be able to upcast in Database.
public interface DBRowNames{

    //String variant contains the displayed name inside the csv file for each column.
    String stringVariant = null;

    String getStringVariant();
}
