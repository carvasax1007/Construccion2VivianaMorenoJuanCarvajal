/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapters.inputs.utils;

import org.springframework.stereotype.Component;


@Component
public class PersonValidator extends SimpleValidator{
    
    public String nameValidator(String value) throws Exception{
        return stringValidator(value, "nombre de la persona");
    }
    
    public long documentValidator(String value) throws Exception{
        return longValidator(value, "numero de documento");
    }
    
    public int ageValidator(String value) throws Exception{
        int age = integerValidator(value, "edad");
        if (age < 1 || age >100){
            throw new Exception("la edad debe ser entre 1 a 100 años");
        }
        return age;
    }
}
