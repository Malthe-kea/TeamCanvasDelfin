package database.rowNameEnum;

public enum MemberDBRowNames {
    USER_ID("id"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    IS_ACTIVE_MEMBER("isActiveMember"),
    IS_COMPETITIVE("isCompetitive"),
    AGE("age"),
    IS_ARREARS("isArrears"),
    YEARLY_MEMBERSHIP_FEE("yearlyMembershipFee");

    public final String stringVariant;
    MemberDBRowNames(String stringVariant) {
        this.stringVariant = stringVariant;
    }
}
