package database;

import user_domain.User;

import java.io.File;
import java.util.ArrayList;

public interface UserReturn {

    User getUserFromID(int id);
    User getUserFromFullname(String name);

    ArrayList<User> getListOfUsers();

    static String getFolderPath() {
        return "UserDB"+ File.separator;
    }
}
