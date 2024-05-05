package database.rowNameEnum;

public enum CompetitiveMemberDBRowNames implements DBRowNames {
    USER_ID("id"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    COMPETITION_LIST("competitionList");

    final String stringVariant;

    CompetitiveMemberDBRowNames(String stringVariant) {
        this.stringVariant = stringVariant;
    }

    @Override
    public String getStringVariant() {
        return stringVariant;
    }
}
