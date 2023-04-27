package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.domain.RoleProgram;
import com.teamfour.sistutorias.domain.User;
import com.teamfour.sistutorias.domain.UserRoleProgram;

public class SessionGlobalData {
    private static SessionGlobalData instance;
    private UserRoleProgram user;
    private RoleProgram roleProgram;

    public UserRoleProgram getUserRoleProgram() {
        return user;
    }

    public void setUserRoleProgram(UserRoleProgram user) {
        this.user = user;
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

    public void setActiveRole(RoleProgram roleProgram) {
        this.roleProgram = roleProgram;
    }

    public RoleProgram getActiveRole() {
        return roleProgram;
    }
}
