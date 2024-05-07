package domain_model;

import user_domain.*;

//This class is a utility class, to be called by all classes. Only contains static methods.
public class DelfinUtil {

    //Method for checking which instance a user class is. Returns the corresponding Enum from UserInstance.
    public static UserInstance checkUserInstance(User user) {
        //The if statement always checks from the bottom of the class hierarchy and up.
        //Therefor it checks competitive first, as it is the lowest subclass og User, and SuperUser, Member and Trainer last.
        //If above is not followed, it will break, as Competitive member is also an instance of Member, and would
        //return the incorrect enum.
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
