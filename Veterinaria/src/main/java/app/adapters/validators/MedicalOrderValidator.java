/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapters.validators;

import org.springframework.stereotype.Component;

/**
 *
 * @author Viviana
 */
@Component
public class MedicalOrderValidator extends SimpleValidator{

    public long veterinarianIdValidator(String value) throws Exception{
        return longValidator(value, "Ingresa la cc del veterinario");
    }
    
    public long ownerIdValidator(String value) throws Exception{
        return longValidator(value, "Ingresa la cc del dueño de la mascota");
    }
    
    public String medicationValidator(String value) throws Exception{
        return stringValidator(value, "ingresa nombre del medicamento");
    }
} 