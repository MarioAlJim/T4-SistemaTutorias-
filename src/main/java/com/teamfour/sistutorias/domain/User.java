package com.teamfour.sistutorias.domain;

public class User extends Person{
    private String email;
    private String password;

    private String roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void addRole(String role) {
        if (this.roles == null) {
            this.roles = role;
        } else {
            this.roles += ", " + role;
        }
    }

    public void removeRole(String role) {
        if (this.roles != null) {
            String[] roles = this.roles.split(",");
            this.roles = "";
            for (String s : roles) {
                if (!s.equals(role)) {
                    if (this.roles.equals("")) {
                        this.roles = s;
                    } else {
                        this.roles += ", " + s;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getPaternalSurname() + " " + this.getMaternalSurname();
    }
}
