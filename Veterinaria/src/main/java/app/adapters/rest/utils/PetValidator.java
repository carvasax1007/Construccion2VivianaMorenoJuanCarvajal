/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapters.inputs.utils;

import org.springframework.stereotype.Component;

@Component

public class PetValidator extends SimpleValidator{
    
    public String petNameValidator(String value) throws Exception{
        return stringValidator( value, "nombre de la mascota");
    }
    
    public String speciesValidator(String value) throws Exception{
        value = stringValidator(value, "especies"); 
        if(!value.equalsIgnoreCase("perro") && !value.equalsIgnoreCase("gato") && !value.equalsIgnoreCase("pez") && !value.equalsIgnoreCase("ave")){
            throw new Exception("ingresa una especia valida, solo es pez, gato, perro o ave");
        }
        return value;
    }
    
    public int ageValidator(String value) throws Exception{
        return integerValidator(value, "edad de la mascota");
        
    }
    
    public double weightValidator(String value) throws Exception{
        double weight = doubleValidator(value, "perso de la mascota");
        if (weight <=0){
            throw new Exception ("peso invalido");
        }
        return weight;
    }
    public long petIdValidator(String value) throws Exception {
        return longValidator(value, "Incresa el ID de la mascota");
    }
    
    public long ownerIdValidator(String value) throws Exception {
        return longValidator(value, "Ingresa la cc del dueño de la mascota");
    }

}
