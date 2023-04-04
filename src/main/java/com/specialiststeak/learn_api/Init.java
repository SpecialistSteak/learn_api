package com.specialiststeak.learn_api;

import com.specialiststeak.learn_api.database.UserDatabase;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Init {

    public static void start() {
        UserDatabase.load();
    }
}
