package com.teamfour.sistutorias.presentation;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Messages {
    private Alert alert = new Alert(AlertType.INFORMATION);

    public void mostrarAlertaErrorConexionDB(){
        alert.setTitle("Atención");
        alert.setHeaderText("Perdida de conexión");
        alert.setContentText("No se pudo conectar con la base de datos, por favor intente mas tarde.");
        alert.showAndWait();
    }

    public void mostrarAlertaUsuarioIncorrecto(){
        alert.setTitle("Atención");
        alert.setHeaderText("por favor verifique");
        alert.setContentText("No se encontró el usuario ingresado o es invalido");
        alert.showAndWait();
    }

    public void mostrarAlertaCamposVacios(){
        alert.setTitle("Atención");
        alert.setHeaderText("Campos vacíos");
        alert.setContentText("Introduzca los campos necesarios para el registro.");
        alert.showAndWait();
    }

    public void mostrarAlertaRegistroExitoso() {
        alert.setTitle("Completado");
        alert.setHeaderText("Registro completado");
        alert.setContentText("El registro de la ifnormación se a almacenado correctamente.");
        alert.showAndWait();
    }

    public void mostrarAlertaSinTutores() {
        alert.setTitle("Atención");
        alert.setHeaderText("Tutores no encontrados");
        alert.setContentText("No existen tutores registrados en el sistema.");
        alert.showAndWait();
    }

    public void mostrarAlertarSinProblematicas() {
        alert.setTitle("Atención");
        alert.setHeaderText("Problematicas no encontrados");
        alert.setContentText("No existen problematicas registradas en el sistema.");
        alert.showAndWait();
    }

    public void mostrarAlertaNoHayFechasDeTutoriaRegistradas() {
        alert.setTitle("Atención");
        alert.setHeaderText("Fechas de tutoria no encontradas");
        alert.setContentText("No existen fechas de tutorias registradas en el sistema.");
        alert.showAndWait();
    }

    public void mostrarAlertaHorarioExistente() {
        alert.setTitle("Atención");
        alert.setHeaderText("Horario ya registrado");
        alert.setContentText("Usted ya registró un horario para la fecha seleccionada, por favor seleccione otra fecha.");
        alert.showAndWait();
    }

    public void mostrarAlertarHorarioNoRegistrado() {
        alert.setTitle("Atencion");
        alert.setHeaderText("Esta fecha de tutoria no tiene un horario registrado");
        alert.setContentText("Por favor seleccione otra fecha");
        alert.showAndWait();
    }

    public void mostrarAlertaRegistroReporteExitoso(){
        alert.setTitle("Completado");
        alert.setHeaderText("Reporte Enviado");
        alert.setContentText("Su reporte se almaceno con exito");
        alert.showAndWait();
    }

    public void mostrarAlertaNoHayReportes() {
        alert.setTitle("Sin Reportes");
        alert.setHeaderText("No se encontraron reportes registrados");
        alert.showAndWait();
    }

    public void mostrarAlertarHorarioNoValido() {
        alert.setTitle("Informacion");
        alert.setHeaderText("Hora no valida");
        alert.setContentText("El formato de la hora que ingreso no es valido");
        alert.showAndWait();
    }

    public void mostrarAlertaNoHayPeriodosRegistrados() {
        alert.setTitle("Atencion");
        alert.setHeaderText("No existe un periodo");
        alert.setContentText("Actualmente no existen periodos registrados");
        alert.showAndWait();
    }

    public void mostrarCamposInvalidos() {
        alert.setTitle("Atencion");
        alert.setHeaderText("Informacion no valida");
        alert.setContentText("Verifique la informacion ingresada en el formulario");
        alert.showAndWait();
    }

    public void mostrarAlertaRegistroNoCompletado() {
        alert.setTitle("Atencion");
        alert.setHeaderText("Registro no completado");
        alert.setContentText("Verifique la informacion ingresada en el formulario antes de registrar");
        alert.showAndWait();
    }

    public void mostrarAlertaNoHayExperienciasRegistradas() {
        alert.setTitle("Informacion");
        alert.setHeaderText("No se encontraron experiencias");
        alert.setContentText("No se encontraron experiencias registradas, comuniquese con su administrador");
        alert.showAndWait();
    }

    public void mostrarAlertarNoHayDocentesRegistrados() {
        alert.setTitle("Informacion");
        alert.setHeaderText("No se encontraron docentes");
        alert.setContentText("No se encontraron docentes registrados para su seleccion, comuniquese con su administrador");
        alert.showAndWait();
    }

    public void mostrarAlertaNoHayFechaDeTutoriaActiva() {
        alert.setTitle("Informacion");
        alert.setHeaderText("No hay tutorias activas");
        alert.setContentText("Actualmente no hay ningna fecha de tutoria activa, por favor cantacte con su respectivo coordinador de tutorias");
        alert.showAndWait();
    }

    public void mostrarAlertarNoHayOfertaAcademicaRegistrada() {
        alert.setTitle("Informacion");
        alert.setHeaderText("No registrada");
        alert.setContentText("Aun no se registra oferta academica");
        alert.showAndWait();
    }

    public void mostrarAlertaNohayTutorados() {
        alert.setTitle("No hay registros");
        alert.setHeaderText("No se han encontrado tutorados");
        alert.showAndWait();
    }

    public void mostrarAlertaNohayProblematicasRegistradasPorTutor() {
        alert.setTitle("No hay registros");
        alert.setHeaderText("No se registraon problematicas por parte del tutor");
        alert.showAndWait();
    }
}
