package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.domain.*;

public class SessionGlobalData {
    private static SessionGlobalData instance;
    private UserRoleProgram user;
    private RoleProgram roleProgram;
    private Tutorship currentTutorship;
    private Period currentPeriod;
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

    public Tutorship getCurrentTutorship() {
        return currentTutorship;
    }

    public void setCurrentTutorship(Tutorship currentTutorship) {
        this.currentTutorship = currentTutorship;
    }

    public Period getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(Period currentPeriod) {
        this.currentPeriod = currentPeriod;
    }
}
