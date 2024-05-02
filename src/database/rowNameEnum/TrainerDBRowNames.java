package database.rowNameEnum;

public enum TrainerDBRowNames implements DBRowNames {
    USER_ID("id"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    IS_SENIOR_TRAINER("isSeniorTrainer");

    private String stringVariant;
    TrainerDBRowNames(String stringVariant) {
        this.stringVariant = stringVariant;
    }

    public String getStringVariant() {
        return stringVariant;
    }
}
