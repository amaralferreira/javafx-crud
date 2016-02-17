package com.javafxcrud.controller;

import com.javafxcrud.model.Person;
import com.javafxcrud.utils.CalendarUtil;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.javafxcrud.annotation.FXMLColumn;
import com.javafxcrud.annotation.FXMLColumnSize;
import com.javafxcrud.annotation.FXMLColumns;

public class PersonEditDialogController {

    @FXML
    @FXMLColumn(messageErro = "Digite a primeira letra do nome:")
    private TextField firstNameField;
    @FXML
    @FXMLColumn(messageErro = "Digite apenas uma letra do nome:")
    @FXMLColumnSize(size=15,messageErro = "Quantidade não e valida:")
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

        FXMLColumns.validColumn(getClass(), firstNameField);
        FXMLColumns.validColumn(getClass(), lastNameField);
        
        
        
        
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
        

        if (errorMessage.length()  == 0) {
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
