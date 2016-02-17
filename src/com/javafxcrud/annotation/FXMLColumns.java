/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafxcrud.annotation;

import java.lang.reflect.Field;
import java.util.regex.Pattern;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 *
 * @author c1278778
 */
public class FXMLColumns {

    public static Boolean validColumn(Class classe, TextField textField) {
        Boolean status = null;

        // Ler todos os campos da class
        for (Field field : classe.getDeclaredFields()) {
            
            //Verificar se o metodo possui Annotation            
            if (field.isAnnotationPresent(FXMLColumn.class)) {
                //Coletar Annotation do field
                FXMLColumn annotation = field.getAnnotation(FXMLColumn.class);
                //Status da validação
                status = Pattern.matches(annotation.regex(), textField.getText());
                //Aplicar validação de campo
                if (status) {
                    tooltipClose(textField);
                } else {
                    tooltipShow(textField, annotation.messageErro());
                }
            }

            if (field.isAnnotationPresent(FXMLColumnSize.class)) {
                //Coletar Annotation do field
                FXMLColumnSize annotation = field.getAnnotation(FXMLColumnSize.class);
                //Status da validação
                status = (annotation.size() == textField.getText().length())? Boolean.TRUE : Boolean.FALSE;
                //Aplicar validação de campo
                if (status) {
                    tooltipClose(textField);
                } else {
                    tooltipShow(textField, annotation.messageErro());
                }
            }
        }

        return status;
    }

    private static void tooltipShow(TextField textField, String messageErro) {
        // Deixar vermelhor se o campo não atender a condição
        textField.setStyle(" -fx-border-color:red; ");
        Tooltip tooltip = new Tooltip(messageErro);
        tooltip.setStyle(""
                + " -fx-text-fill: rgba(17, 145,  213); "
                + " -fx-font: 16px \"Serif\"; "
                + " -fx-background-color: yellow; "
                + " -fx-background-radius: 5; ");

        textField.setTooltip(tooltip);
    }

    private static void tooltipClose(TextField textField) {
        //Tornar padrão
        textField.setStyle("");
    }
}
