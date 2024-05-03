package database.rowNameEnum;

//Enum class containing column of TrainerDB csv file.
public enum TrainerDBRowNames implements DBRowNames {
    USER_ID("id"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    IS_SENIOR_TRAINER("isSeniorTrainer");

    //String variant contains the displayed name inside the csv file for each column.
    private String stringVariant;
    TrainerDBRowNames(String stringVariant) {
        this.stringVariant = stringVariant;
    }

    //Getter for the string variant. Needed as it would get the null value from the interface DBRowNames.
    public String getStringVariant() {
        return stringVariant;
    }
}
