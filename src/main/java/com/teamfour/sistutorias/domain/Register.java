package com.teamfour.sistutorias.domain;

public class Register {
    private int register_id;
    private String email;
    private int tutorship_id;
    private int educative_program_id;

    public Register() {
    }

    public Register(String email, int tutorship_id, int educative_program_id) {
        this.email = email;
        this.tutorship_id = tutorship_id;
        this.educative_program_id = educative_program_id;
    }

    public int getRegister_id() {
        return register_id;
    }

    public String getEmail() {
        return email;
    }

    public int getTutorship_id() {
        return tutorship_id;
    }

    public int getEducative_program_id() {
        return educative_program_id;
    }

    public void setRegister_id(int register_id) {
        this.register_id = register_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTutorship_id(int tutorship_id) {
        this.tutorship_id = tutorship_id;
    }

    public void setEducative_program_id(int educative_program_id) {
        this.educative_program_id = educative_program_id;
    }
}
