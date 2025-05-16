/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapters.inputs.utils;

import app.Exceptions.InputsException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;


@Component
public class SimpleValidator {
    
    public String stringValidator(String value, String element) throws Exception{
        if (value == null || value.trim().isEmpty()){
            throw new InputsException(element + "no hay un valor valido");
        }
        return value;
    }
    public Long longValidator(String value, String element) throws Exception{
        try {
            return Long.parseLong(stringValidator(value, element));
        } catch (NumberFormatException e){
            throw new InputsException(element + "ingresa un numero valido");
        }
    }
    public double doubleValidator(String value, String element) throws Exception{
        try{
            return Double.parseDouble(stringValidator(value, element));
        } catch (NumberFormatException e){
            throw new InputsException(element + "ingresa el numero decimal valido");
        }
    }
    public String dateFormatValidator(String value, String element) throws Exception{ 
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            dateFormat.setLenient(false);
            dateFormat.parse(value);
            return value;
        } catch(ParseException e){
            throw new InputsException(element + "formado debe ser año-mes-dia");
        }
    }
    public Integer integerValidator(String value, String element) throws Exception{
        try{
            return Integer.parseInt(stringValidator(value, element));
        }catch (NumberFormatException e){
            throw new InputsException(element + "debe ser un numero entero");
        }
    }
}
