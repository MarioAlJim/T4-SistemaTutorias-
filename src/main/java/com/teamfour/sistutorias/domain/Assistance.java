package com.teamfour.sistutorias.domain;
import javafx.scene.control.CheckBox;

public class Assistance extends Tutorado {

    private int assistance_id;
    private int registration_id;
    private Boolean  asistencia = false;
    private Boolean riesgo = false;
    private CheckBox checkBoxAsistencia = new CheckBox();
    private CheckBox checkBoxRiesgo = new CheckBox();
    private int register_id;

    public int getAssistance_id() { return assistance_id; }

    public int getRegistration_id() { return registration_id; }

    public Boolean getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Boolean asistencia) {
        this.asistencia = asistencia;
    }

    public Boolean getRiesgo() {
        return riesgo;
    }

    public int getRegister_id() { return register_id; }

    public void setAssistance_id(int assistance_id) { this.assistance_id = assistance_id; }

    public void setRegistration_id(int registration_id) { this.registration_id = registration_id; }

    public void setRiesgo(Boolean riesgo) {
        this.riesgo = riesgo;
    }

    public CheckBox getCheckBoxAsistencia() {
        return checkBoxAsistencia;
    }

    public void setCheckBoxAsistencia(CheckBox checkBoxAsistencia) {
        this.checkBoxAsistencia = checkBoxAsistencia;
    }

    public CheckBox getCheckBoxRiesgo() {
        return checkBoxRiesgo;
    }

    public void setCheckBoxRiesgo(CheckBox checkBoxRiesgo) {
        this.checkBoxRiesgo = checkBoxRiesgo;
    }

    public void setRegister_id(int register_id) { this.register_id = register_id; }
}
