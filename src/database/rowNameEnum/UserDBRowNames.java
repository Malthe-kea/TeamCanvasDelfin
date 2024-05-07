package database.rowNameEnum;

//Enum class containing column of UserDB csv file.
public enum UserDBRowNames implements DBRowNames {
    USER_ID("id"),
    PERMISSION_LEVEL("permissionLevel"),
    PASSWORD("password"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName");

    //String variant contains the displayed name inside the csv file for each column.
    private String stringVariant;
    UserDBRowNames(String stringVariant) {
        this.stringVariant = stringVariant;
    }

    //Getter for the string variant. Needed as it would get the null value from the interface DBRowNames.
    public String getStringVariant() {
        return stringVariant;
    }
}
