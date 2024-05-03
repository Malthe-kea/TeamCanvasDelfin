package database.rowNameEnum;

//Enum class containing column of CompetitiveDB csv file.
public enum CompetitiveDBRowNames implements DBRowNames {
    COMPETITIVE_ID("id"),
    LOCATION("location"),
    USERS_WHO_HAS_COMPETED("userParticipation");

    //String variant contains the displayed name inside the csv file for each column.
    final String stringVariant;
    CompetitiveDBRowNames(String stringVariant) {
        this.stringVariant = stringVariant;
    }

    //Getter for the string variant. Needed as it would get the null value from the interface DBRowNames.
    public String getStringVariant() {
        return stringVariant;
    }


}
