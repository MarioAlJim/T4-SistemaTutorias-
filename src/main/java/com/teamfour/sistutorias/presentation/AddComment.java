package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.domain.Comment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddComment {

    @FXML
    private TextArea taComment;

    private Comment comment;

    @FXML
    void closeWindow(ActionEvent event) {
        exitWindow();
    }

    @FXML
    void saveComment(ActionEvent event) {
        if(!taComment.getText().isEmpty()) {
            comment = new Comment(taComment.getText());
        }
        exitWindow();
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    private void exitWindow() {
        Stage stage = (Stage) taComment.getScene().getWindow();
        stage.close();
    }
}
