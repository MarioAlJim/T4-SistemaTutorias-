package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.domain.User;
import com.teamfour.sistutorias.domain.UserRoleProgram;

public class SessionGlobalData {
    private static SessionGlobalData instance;
    private UserRoleProgram user;

    public UserRoleProgram getUserRoleProgram() {
        return user;
    }

    public void setUserRoleProgram(UserRoleProgram userRoleProgram_) {
        this.user = userRoleProgram_;
    }

    private SessionGlobalData() {
        this.user = new UserRoleProgram();
    }

    public static SessionGlobalData getSessionGlobalData() {
        if(instance == null) {
            instance = new SessionGlobalData();
        }
        return instance;
    }
}
