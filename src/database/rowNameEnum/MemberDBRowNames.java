package database.rowNameEnum;

//Enum class containing column of MemberDB csv file.
public enum MemberDBRowNames implements DBRowNames {
    USER_ID("id"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    IS_ACTIVE_MEMBER("isActiveMember"),
    IS_COMPETITIVE("isCompetitive"),
    AGE("age"),
    IS_ARREARS("isArrears"),
    YEARLY_MEMBERSHIP_FEE("yearlyMembershipFee");

    //String variant contains the displayed name inside the csv file for each column.
    final String stringVariant;
    MemberDBRowNames(String stringVariant) {
        this.stringVariant = stringVariant;
    }

    //Getter for the string variant. Needed as it would get the null value from the interface DBRowNames.
    public String getStringVariant() {
        return stringVariant;
    }
}
