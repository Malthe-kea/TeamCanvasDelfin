package database.rowNameEnum;

public enum UserDBRowNames {
    USER_ID("id"),
    PERMISSION_LEVEL("permissionLevel"),
    PASSWORD("password"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName");

    public final String stringVariant;
    UserDBRowNames(String stringVariant) {
        this.stringVariant = stringVariant;
    }
}
