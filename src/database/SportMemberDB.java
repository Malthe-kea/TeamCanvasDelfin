package database;

import user_domain.User;

public class SportMemberDB extends Database implements UserReturn {

    public SportMemberDB(String filepath) {
        super(filepath);
    }

}
