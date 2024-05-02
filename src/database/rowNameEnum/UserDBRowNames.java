package database.rowNameEnum;

public enum UserDBRowNames implements DBRowNames {
    USER_ID("id"),
    PERMISSION_LEVEL("permissionLevel"),
    PASSWORD("password"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName");

    private String stringVariant;
    UserDBRowNames(String stringVariant) {
        this.stringVariant = stringVariant;
    }

    public String getStringVariant() {
        return stringVariant;
    }
}