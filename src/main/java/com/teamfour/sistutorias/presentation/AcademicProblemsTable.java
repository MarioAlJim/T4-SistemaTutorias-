package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.domain.AcademicProblem;
import javafx.scene.control.CheckBox;

public class AcademicProblemsTable extends AcademicProblem {
    private CheckBox checkBox;

    public AcademicProblemsTable() {
        this.checkBox = new CheckBox();
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
