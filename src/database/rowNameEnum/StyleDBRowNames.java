package database.rowNameEnum;

public enum StyleDBRowNames implements DBRowNames {
    STYLE_ID("id"),
    USER_ID("userID"),
    COMPETITION_ID("competitionID"),
    STYLE_NAME("styleName"),
    STYLE_PLACEMENT("placement"),
    TIME_IN_SECONDS("timeInSeconds");

    final String stringVariant;
    StyleDBRowNames(String stringVariant) {
        this.stringVariant = stringVariant;
    }

    public String getStringVariant() {
        return stringVariant;
    }
}
