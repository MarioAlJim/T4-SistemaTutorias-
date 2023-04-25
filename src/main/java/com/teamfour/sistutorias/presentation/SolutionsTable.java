package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.domain.AcademicProblem;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

public class SolutionsTable extends AcademicProblem {
    private ComboBox<String> cbAcademicProblems;
    private ArrayList<Integer> relatedAcademicProblems = new ArrayList<>();

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

    public ArrayList<Integer> getRelatedAcademicProblems() {
        return relatedAcademicProblems;
    }

    public void addRelatedAcademicProblems(Integer relatedAcademicProblem) {
        this.relatedAcademicProblems.add(relatedAcademicProblem);
    }
}
