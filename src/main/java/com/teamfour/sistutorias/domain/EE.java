package com.teamfour.sistutorias.domain;

public class EE {
    private int idEe;
    private String name;

    public EE() {
        this.setIdEe(0);
        this.setName("");
    }

    public int getIdEe() {
        return idEe;
    }

    public void setIdEe(int idEe) {
        this.idEe = idEe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

