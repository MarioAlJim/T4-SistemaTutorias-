package com.teamfour.sistutorias.domain;
import javafx.scene.control.CheckBox;

public class Assistance extends Tutorado {
    private Boolean  asistencia = false;
    private Boolean riesgo = false;
    private CheckBox checkBoxAsistencia = new CheckBox();
    private CheckBox checkBoxRiesgo = new CheckBox();

    public Boolean getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Boolean asistencia) {
        this.asistencia = asistencia;
    }

    public Boolean getRiesgo() {
        return riesgo;
    }

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
}
