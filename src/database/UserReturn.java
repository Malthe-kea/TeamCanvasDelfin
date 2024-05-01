package database;

import user_domain.User;

import java.util.ArrayList;

public interface UserReturn {

    User getUserFromID(int id);
    User getUserFromFullname(String name);

    ArrayList<User> getListOfUsers();
}
