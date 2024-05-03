package domain_model;

import user_domain.*;

public class DelfinUtil {

    public static UserInstance checkUserInstance(User user) {
        if(user instanceof CompetitiveMember) {
            return UserInstance.COMPETITIVE;
        }
        if(user instanceof Member) {
            return UserInstance.MEMBER;
        }
        if(user instanceof Trainer) {
            return UserInstance.TRAINER;
        }
        if(user instanceof Treasurer) {
            return UserInstance.TREASURER;
        }
        if(user instanceof SuperUser) {
            return UserInstance.SUPER;
        }
        return UserInstance.NOTFOUND;

    }

}
