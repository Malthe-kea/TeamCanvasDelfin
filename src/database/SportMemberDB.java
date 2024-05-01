package database;
import java.util.ArrayList;

import user_domain.User;

public class SportMemberDB extends Database implements UserReturn {
    @Override
    public User getUserFromID(int id) {
        return null;
    }

    @Override
    public User getUserFromFullname(String name) {
        return null;
    }

    @Override
    public ArrayList<User> getListOfUsers() {
        return null;
    }

    public SportMemberDB(String filepath) {
        super(filepath);
    }

}
