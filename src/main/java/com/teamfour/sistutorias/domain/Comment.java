package com.teamfour.sistutorias.domain;

public class Comment {
    private int comment_id;
    private String description;
    private int register;

    public Comment(String description) {
        this.description = description;
    }

    public Comment() {

    }

    public int getComment_id() {
        return comment_id;
    }
    public String getDescription() {
        return description;
    }

    public int getRegister() {
        return register;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRegister(int register) {
        this.register = register;
    }
}
