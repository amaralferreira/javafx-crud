package com.javafxcrud.controller;

import com.javafxcrud.annotation.ColumnAnnotationType;
import com.javafxcrud.model.Person;
import com.javafxcrud.utils.CalendarUtil;
import com.javafxcrud.view.MainApp;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class PersonEditDialogController {

    @FXML
    @ColumnAnnotationType(messageErro = "Digite apenas uma letra do nome:")
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;
    @FXML
    private DatePicker dataPicker;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
        dataPicker = new DatePicker(LocalDate.MAX);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPerson(Person person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(CalendarUtil.format(person.getBirthday()));
        birthdayField.setPromptText("yyyy-mm-dd");
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(CalendarUtil.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "No valid street!\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        } else {
            // try to parse the postal code into an int
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else if (!CalendarUtil.validString(birthdayField.getText())) {
            errorMessage += "No valid birthday. Use the format yyyy-mm-dd!\n";
        }

        //Person person = new Person();
        //person.setFirstName(firstNameField.getText());
        Class<?> personClass = getClass();

        // Ler todos os campos da class
        for (Field field : personClass.getDeclaredFields()) {
            //SVerificar se o metodo possui Annotation            
            if (field.isAnnotationPresent(ColumnAnnotationType.class)) {
                //Coletar Annotation do field
                ColumnAnnotationType anotacao = field.getAnnotation(ColumnAnnotationType.class);                
                //Aplicar validação de campo
                if (Pattern.matches(anotacao.regex(), firstNameField.getText())) {
                    //Tornar padrão
                    firstNameField.setStyle("");
                } else {
                    System.err.println("Regex inválida");
                    
                    // Deixar vermelhor se o campo não atender a condição
                    firstNameField.setStyle(" -fx-border-color:red; ");
                    Tooltip tooltip = new Tooltip(anotacao.messageErro());
                    tooltip.setStyle(""
                            + " -fx-text-fill: rgba(17, 145,  213); "
                            + " -fx-font: 16px \"Serif\"; "
                            + " -fx-background-color: yellow; "
                            + " -fx-background-radius: 5; ");
                    
                    firstNameField.setTooltip(tooltip);
                }
            }
        }

        if (errorMessage.length()
                == 0) {
            //return true;
            return false;
        } else {
            System.err.println("Dialogs.showErrorDialog");
            // Show the error message
            //Dialogs.showErrorDialog(dialogStage, errorMessage, "Please correct invalid fields", "Invalid Fields");
            return false;
        }

    }
}
