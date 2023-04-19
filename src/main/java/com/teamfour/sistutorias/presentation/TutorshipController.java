package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.PeriodDAO;
import com.teamfour.sistutorias.bussinesslogic.TeacherDAO;
import com.teamfour.sistutorias.domain.Teacher;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import javafx.util.StringConverter;
import com.teamfour.sistutorias.bussinesslogic.TutorshipDAO;
import com.teamfour.sistutorias.domain.Tutorship;
import com.teamfour.sistutorias.domain.Period;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    private ComboBox cbPeriod;

    @javafx.fxml.FXML
    public void cancelAction(ActionEvent actionEvent) {
        WindowManagement.closeWindow(actionEvent);
    }


    @javafx.fxml.FXML
    public void addAction(ActionEvent actionEvent) throws SQLException {

        TutorshipDAO tutorshipDAO = new TutorshipDAO();

        if (validateDatePickers()) {

            Tutorship tutorship = new Tutorship();
            Period period = (Period) cbPeriod.getSelectionModel().getSelectedItem();
            period.getIdPeriod();

            tutorship.setPeriodId(period.getIdPeriod());
            tutorship.setStart(firstDatePicker.getValue().toString());
            tutorship.setEnd(endFirstDatePicker.getValue().toString());

            tutorshipDAO.addTutorship(tutorship);

            Tutorship tutorship1 = new Tutorship();
            period.getIdPeriod();
            tutorship1.setPeriodId(period.getIdPeriod());
            tutorship1.setStart(secondDatePicker.getValue().toString());
            tutorship1.setEnd(endSecondDatePicker.getValue().toString());
            tutorshipDAO.addTutorship(tutorship1);

            Tutorship tutorship2 = new Tutorship();
            period.getIdPeriod();
            tutorship2.setPeriodId(period.getIdPeriod());
            tutorship2.setStart(thirdDatePicker.getValue().toString());
            tutorship2.setEnd(endThirdDatePicker.getValue().toString());
            tutorshipDAO.addTutorship(tutorship2);
            if (tutorshipDAO.addTutorship(tutorship) && tutorshipDAO.addTutorship(tutorship1) && tutorshipDAO.addTutorship(tutorship2)) {
                WindowManagement.showAlert("Sesiones de tutoría agregadas",
                        "Las sesiones de tutoría se han agregado correctamente",
                        Alert.AlertType.INFORMATION);

            } else {
                WindowManagement.showAlert("Sesiones de tutoría no fueron agregadas",
                        "Las sesiones de tutoría no se han agregado",
                        Alert.AlertType.INFORMATION);
            }
        }else {
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
                if(endFirstDatePicker.getValue().isBefore(secondDatePicker.getValue()) &&
                        endSecondDatePicker.getValue().isBefore(thirdDatePicker.getValue())){
                    return true;
                }else{
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void populateComboBox() throws SQLException {
        PeriodDAO periodDAO = new PeriodDAO();
        ObservableList<Period> periods = FXCollections.observableArrayList();
        periods.addAll(periodDAO.getPeriods());
        this.cbPeriod.setItems(periods);
        this.cbPeriod.getSelectionModel().selectFirst();
        this.cbPeriod.setConverter(new StringConverter<Period>() {
            @Override
            public String toString(Period period) {
                return period == null ? null : period.getStart() + " - " + period.getEnd();
            }
            @Override
            public Period fromString(String s) {
                return null;
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateComboBox();
            TutorshipDAO tutorship = new TutorshipDAO();
            List<Tutorship> tutorships = tutorship.getTutorship(1);
            for (Tutorship tutorship2 : tutorships) {
                System.out.println(tutorship2.getPeriodId());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
       setDatePickers();
    }

    public void dateFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = firstDatePicker.getValue().format(formatter);
        String date2 = endFirstDatePicker.getValue().format(formatter);
        String date3 = secondDatePicker.getValue().format(formatter);
        String date4 = endSecondDatePicker.getValue().format(formatter);
        String date5 = thirdDatePicker.getValue().format(formatter);
        String date6 = endThirdDatePicker.getValue().format(formatter);
    }

    public void setDatePickers() {
        Callback<DatePicker, DateCell> dayCellFactory = (DatePicker picker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate localDate, boolean isEmpty) {
                super.updateItem(localDate, isEmpty);
                if(isEmpty || localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    setDisable(true);
                }
                Period period = (Period) cbPeriod.getSelectionModel().getSelectedItem();
                if (period != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
                    try {
                        Date date = dateFormat.parse(period.getStart());
                        Date date2 = dateFormat.parse(period.getEnd());
                        if (localDate.isBefore(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) || localDate.isAfter(date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                            setDisable(true);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        firstDatePicker.setDayCellFactory(dayCellFactory);
        endFirstDatePicker.setDayCellFactory(dayCellFactory);
        secondDatePicker.setDayCellFactory(dayCellFactory);
        endSecondDatePicker.setDayCellFactory(dayCellFactory);
        thirdDatePicker.setDayCellFactory(dayCellFactory);
        endThirdDatePicker.setDayCellFactory(dayCellFactory);
    }
}
