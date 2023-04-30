package com.teamfour.sistutorias.presentation;

import javafx.scene.control.Alert;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {

    public static boolean textValidation(String text) {
        boolean valid = true;
        String regex = "^[\\p{L} .'-]+$";
        if (!text.matches(regex)) {
            valid = false;
        }
        return valid;
    }

    public static int numberValidation(String number) {
        int valid = 0;
        if (number.matches("[+-]?\\d*(\\.\\d+)?")) {
            valid = Integer.parseInt(number);
        } else {
            valid = -1;
            WindowManagement.showAlert("Caracteres invalidos", "Se detectaron caracteres invalidos, ingrese un numero valido", Alert.AlertType.INFORMATION);
        }
        return valid;
    }


}
