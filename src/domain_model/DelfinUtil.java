package domain_model;

import user_domain.Member;
import user_domain.Trainer;
import user_domain.Treasurer;
import user_domain.User;

public class DelfinUtil {

    public static UserInstance checkUserInstance(User user) {
        if(user instanceof Member) {
            return UserInstance.MEMBER_OR_COMPETITIVE;
        }
        if(user instanceof Trainer) {
            return UserInstance.TRAINER;
        }
        if(user instanceof Treasurer) {
            return UserInstance.TREASURER;
        }
        return UserInstance.SUPER;

    }

}
