package com.specialiststeak.learn_api.database;

import com.specialiststeak.learn_api.objects.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class UserDatabase implements db {
    private UserDatabase() {
    }

    private static List<User> users = new ArrayList<>();

    public static User getUser(int id) {
        return users.stream().filter(user -> user.id() == id).findFirst().orElse(null);
    }

    public static int getUserIndex(int id) {
        return users.indexOf(getUser(id));
    }

    public static boolean set(int id, User user) {
        try {
            users.set(getUserIndex(id), user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean remove(int id) {
        try {
            users.remove(getUserIndex(id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<User> getAllUsers() {
        return users;
    }

    public static boolean addUser(User user) {
        try {
            users.add(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void load() {
        users.add(new User("Samantha", 124, "samantha@hotmail.com", "samantha2022", "cdn.discordapp.com/attachments/867..."));
        users.add(new User("Michael", 125, "michael@gmail.com", "michael2022", "cdn.discordapp.com/attachments/867..."));
        users.add(new User("Emma", 126, "emma@yahoo.com", "emma2022", "cdn.discordapp.com/attachments/867..."));
        users.add(new User("Daniel", 127, "daniel@gmail.com", "daniel2022", "cdn.discordapp.com/attachments/867..."));
        users.add(new User("Olivia", 128, "olivia@yahoo.com", "olivia2022", "cdn.discordapp.com/attachments/867..."));
        users.add(new User("William", 129, "william@hotmail.com", "william2022", "cdn.discordapp.com/attachments/867..."));
        users.add(new User("Sophia", 130, "sophia@gmail.com", "sophia2022", "cdn.discordapp.com/attachments/867..."));
        users.add(new User("David", 131, "david@yahoo.com", "david2022", "cdn.discordapp.com/attachments/867..."));
        users.add(new User("Mia", 132, "mia@hotmail.com", "mia2022", "cdn.discordapp.com/attachments/867..."));
        users.add(new User("Anthony", 133, "anthony@gmail.com", "anthony2022", "cdn.discordapp.com/attachments/867..."));
    }
}
