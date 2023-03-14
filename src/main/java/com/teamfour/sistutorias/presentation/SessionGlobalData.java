package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.domain.User;
import com.teamfour.sistutorias.domain.UserRoleProgram;

public class SessionGlobalData {
    private static SessionGlobalData instance;
    private User user;
    private UserRoleProgram userRoleProgram;

    public User getUser() {
        return user;
    }

    public void setUser(User user_) {
        this.user = user_;
    }

    public UserRoleProgram getUserRoleProgram() {
        return userRoleProgram;
    }

    public void setUserRoleProgram(UserRoleProgram userRoleProgram_) {
        this.userRoleProgram = userRoleProgram_;
    }

    private SessionGlobalData() {
    }

    public static SessionGlobalData getSessionGlobalData() {
        if(instance == null) {
            instance = new SessionGlobalData();
        }
        return instance;
    }
}
