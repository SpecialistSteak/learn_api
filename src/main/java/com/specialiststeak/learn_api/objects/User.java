package com.specialiststeak.learn_api.objects;

import java.io.File;

public record User(String name, int id, String email, String password, String profilePicture) {
    public User setName(String name) {
        return new User(name, id, email, password, profilePicture);
    }

    public User setId(int id) {
        return new User(name, id, email, password, profilePicture);
    }

    public User setEmail(String email) {
        return new User(name, id, email, password, profilePicture);
    }

    public User setPassword(String password) {
        return new User(name, id, email, password, profilePicture);
    }

    public User setProfilePicture(String profilePicture) {
        return new User(name, id, email, password, profilePicture);
    }
}