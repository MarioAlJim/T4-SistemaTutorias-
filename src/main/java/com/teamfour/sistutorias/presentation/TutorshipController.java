package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.PeriodDAO;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import com.teamfour.sistutorias.bussinesslogic.TutorshipDAO;
import com.teamfour.sistutorias.domain.Tutorship;
import com.teamfour.sistutorias.domain.Period;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TutorshipController implements Initializable {
    @javafx.fxml.FXML
    private Button cancelButton;
    @javafx.fxml.FXML
    private Button addButton;
    @javafx.fxml.FXML
    private Label periodLabel;
    @javafx.fxml.FXML
    private Label firstSessionLabel;
    @javafx.fxml.FXML
    private Label endFirstSessionLabel;
    @javafx.fxml.FXML
    private Label instructionsLabel;
    @javafx.fxml.FXML
    private DatePicker firstDatePicker;
    @javafx.fxml.FXML
    private DatePicker endFirstDatePicker;
    @javafx.fxml.FXML
    private Label secondSessionLabel;
    @javafx.fxml.FXML
    private Label endSecondSessionLabel;
    @javafx.fxml.FXML
    private DatePicker secondDatePicker;
    @javafx.fxml.FXML
    private DatePicker endSecondDatePicker;
    @javafx.fxml.FXML
    private Label thirdSessionLabel;
    @javafx.fxml.FXML
    private Label endThirdSessionLabel;
    @javafx.fxml.FXML
    private DatePicker thirdDatePicker;
    @javafx.fxml.FXML
    private DatePicker endThirdDatePicker;
    @javafx.fxml.FXML
    private Label currentPeriod;


    @javafx.fxml.FXML
    public void cancelAction(ActionEvent actionEvent) {
        WindowManagement.closeWindow(actionEvent);
    }


    @javafx.fxml.FXML
    public void addAction(ActionEvent actionEvent) throws SQLException {
        TutorshipDAO tutorshipDAO = new TutorshipDAO();
        Period period = getCurrentPeriod();
        List<Tutorship> tutorships = tutorshipDAO.getTutorship(period.getIdPeriod());

        if (validateDatePickers()) {
            boolean isCorrect = true;
            if (tutorships.size() >= 1) {
                Tutorship tutorship = tutorships.get(0);
                tutorship.setStart(firstDatePicker.getValue().toString());
                tutorship.setEnd(endFirstDatePicker.getValue().toString());
                isCorrect = isCorrect && tutorshipDAO.updateTutorship(tutorship);
            } else {
                Tutorship tutorship = new Tutorship();
                tutorship.setPeriodId(period.getIdPeriod());
                tutorship.setStart(firstDatePicker.getValue().toString());
                tutorship.setEnd(endFirstDatePicker.getValue().toString());
                isCorrect = isCorrect && tutorshipDAO.addTutorship(tutorship);
            }
            if (tutorships.size() >= 2) {
                Tutorship tutorship1 = tutorships.get(1);
                tutorship1.setStart(secondDatePicker.getValue().toString());
                tutorship1.setEnd(endSecondDatePicker.getValue().toString());
                isCorrect = isCorrect && tutorshipDAO.updateTutorship(tutorship1);
            } else {
                Tutorship tutorship1 = new Tutorship();
                tutorship1.setPeriodId(period.getIdPeriod());
                tutorship1.setStart(secondDatePicker.getValue().toString());
                tutorship1.setEnd(endSecondDatePicker.getValue().toString());
                isCorrect = isCorrect && tutorshipDAO.addTutorship(tutorship1);
            }
            if (tutorships.size() >= 3) {
                Tutorship tutorship2 = tutorships.get(2);
                tutorship2.setStart(thirdDatePicker.getValue().toString());
                tutorship2.setEnd(endThirdDatePicker.getValue().toString());
                isCorrect = isCorrect && tutorshipDAO.updateTutorship(tutorship2);
            } else {
                Tutorship tutorship2 = new Tutorship();
                tutorship2.setPeriodId(period.getIdPeriod());
                tutorship2.setStart(thirdDatePicker.getValue().toString());
                tutorship2.setEnd(endThirdDatePicker.getValue().toString());
                isCorrect = isCorrect && tutorshipDAO.addTutorship(tutorship2);
            }
            if (isCorrect) {
                WindowManagement.showAlert("Sesiones de tutoría agregadas",
                        "Las sesiones de tutoría se han guardado correctamente",
                        Alert.AlertType.INFORMATION);

            } else {
                WindowManagement.showAlert("Sesiones de tutoría no fueron agregadas",
                        "Las sesiones de tutoría no se han guardado",
                        Alert.AlertType.INFORMATION);
            }
        } else {
            WindowManagement.showAlert("Campos invalidos",
                    "Por favor, ingrese datos validos",
                    Alert.AlertType.INFORMATION);
        }
    }


    public boolean validateDatePickers() {
        if (firstDatePicker.getValue() != null && endFirstDatePicker.getValue() != null &&
                secondDatePicker.getValue() != null && endSecondDatePicker.getValue() != null &&
                thirdDatePicker.getValue() != null && endThirdDatePicker.getValue() != null) {
            if (firstDatePicker.getValue().isBefore(endFirstDatePicker.getValue()) &&
                    secondDatePicker.getValue().isBefore(endSecondDatePicker.getValue()) &&
                    thirdDatePicker.getValue().isBefore(endThirdDatePicker.getValue())) {
                if (endFirstDatePicker.getValue().isBefore(secondDatePicker.getValue()) &&
                        endSecondDatePicker.getValue().isBefore(thirdDatePicker.getValue())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Period period = getCurrentPeriod();
            currentPeriod.setText(period.toString());
            populateDatePicker();
            Callback<DatePicker, DateCell> dayCellFactory = getDayCellFactory();
            firstDatePicker.setDayCellFactory(dayCellFactory);
            endFirstDatePicker.setDayCellFactory(dayCellFactory);
            secondDatePicker.setDayCellFactory(dayCellFactory);
            endSecondDatePicker.setDayCellFactory(dayCellFactory);
            thirdDatePicker.setDayCellFactory(dayCellFactory);
            endThirdDatePicker.setDayCellFactory(dayCellFactory);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && (item.getDayOfWeek() == DayOfWeek.SATURDAY ||
                                item.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #cccccc;");
                        }
                    }
                };
            }
        };
    }

    public Period getCurrentPeriod() throws SQLException {
        PeriodDAO periodDAO = new PeriodDAO();
        ObservableList<Period> periods = FXCollections.observableArrayList();
        periods.addAll(periodDAO.getPeriods());
        Period period = periods.get(periods.size() - 1);
        return period;
    }

    public void populateDatePicker() {
        try {
        Period period = getCurrentPeriod();
        TutorshipDAO tutorshipDAO = new TutorshipDAO();
        List<Tutorship> tutorships = tutorshipDAO.getTutorship(period.getIdPeriod());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
            if(tutorships.size() >= 1) {
                Tutorship tutorship1 = tutorships.get(0);
                date = dateFormat.parse(tutorship1.getStart());
                firstDatePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                date = dateFormat.parse(tutorship1.getEnd());
                endFirstDatePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } else {
                firstDatePicker.setValue(null);
                endFirstDatePicker.setValue(null);
            }
            if (tutorships.size() >= 2) {
                Tutorship tutorship2 = tutorships.get(1);
                date = dateFormat.parse(tutorship2.getStart());
                secondDatePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                date = dateFormat.parse(tutorship2.getEnd());
                endSecondDatePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } else {
                secondDatePicker.setValue(null);
                endSecondDatePicker.setValue(null);
            }
            if(tutorships.size() >= 3) {
                Tutorship tutorship3 = tutorships.get(2);
                date = dateFormat.parse(tutorship3.getStart());
                thirdDatePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                date = dateFormat.parse(tutorship3.getEnd());
                endThirdDatePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } else {
                thirdDatePicker.setValue(null);
                endThirdDatePicker.setValue(null);
            }
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }
    }
}
