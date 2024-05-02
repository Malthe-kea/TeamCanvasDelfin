package database.rowNameEnum;

public enum MemberDBRowNames implements DBRowNames {
    USER_ID("id"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    IS_ACTIVE_MEMBER("isActiveMember"),
    IS_COMPETITIVE("isCompetitive"),
    AGE("age"),
    IS_ARREARS("isArrears"),
    YEARLY_MEMBERSHIP_FEE("yearlyMembershipFee");

    final String stringVariant;
    MemberDBRowNames(String stringVariant) {
        this.stringVariant = stringVariant;
    }

    public String getStringVariant() {
        return stringVariant;
    }
}
