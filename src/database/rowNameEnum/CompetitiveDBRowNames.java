package database.rowNameEnum;

public enum CompetitiveDBRowNames implements DBRowNames {
    COMPETITIVE_ID("id"),
    LOCATION("location"),
    USERS_WHO_HAS_COMPETED("userParticipation");


    final String stringVariant;
    CompetitiveDBRowNames(String stringVariant) {
        this.stringVariant = stringVariant;
    }

    public String getStringVariant() {
        return stringVariant;
    }


}
