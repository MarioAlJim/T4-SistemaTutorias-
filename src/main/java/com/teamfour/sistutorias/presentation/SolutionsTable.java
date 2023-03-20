package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.domain.AcademicProblem;
import javafx.scene.control.ComboBox;

public class SolutionsTable extends AcademicProblem {
    ComboBox<String> cbAcademicProblems;

    public SolutionsTable() {
        this.cbAcademicProblems = new ComboBox<>();
        this.cbAcademicProblems.setPrefWidth(250);
    }

    public ComboBox<String> getCbAcademicProblems() {
        return cbAcademicProblems;
    }

    public void setCbAcademicProblems(ComboBox<String> cbAcademicProblems) {
        this.cbAcademicProblems = cbAcademicProblems;
    }
}
