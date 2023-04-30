package com.teamfour.sistutorias.presentation;

import javafx.scene.control.Alert;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {

    public static boolean textValidation(String text, int length) {
        boolean valid = true;
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            int valorASCII = (int) character;
            if (!((valorASCII >= 65 || valorASCII <= 90) || valorASCII != 32 || (valorASCII >= 97 || valorASCII <=122)))
                valid = false; //Se ha encontrado un caracter que no es letra
        }
        return valid;
    }

    public static boolean textValidation(String text, int length, boolean spaces) {
        text = text.trim();
        boolean valid = true;
        boolean lastCharIsSpace = false;
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            int valorASCII = (int) character;
            if (character == ' ') {
                if(!spaces){
                    valid = false;
                    break;
                } else {
                    if(lastCharIsSpace){
                        valid = false;
                        break;
                    }
                    lastCharIsSpace = true;
                }
            } else {
                lastCharIsSpace = false;
            }
            if ((character < 'A' || character > 'Z') && (character < 'a' || character > 'z')) {
                valid = false;
                break;
            }
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
