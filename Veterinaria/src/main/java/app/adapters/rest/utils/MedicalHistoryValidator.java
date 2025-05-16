/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapters.inputs.utils;

import org.springframework.stereotype.Component;


@Component

public class MedicalHistoryValidator extends SimpleValidator{
 
    public String dateValidator(String value) throws Exception{
        return dateFormatValidator(value, "fecha de la historia");
    }
    public String reasonConsultationValidator(String value) throws Exception{
        return stringValidator(value, "motivo de consulta");
        
    }
    
    public String diagnosisValidator (String value) throws Exception{
        return stringValidator(value, "ingresa diagnostico");
    }
    
    public String procedureValidator(String value) throws Exception{
        return stringValidator (value, "Procedimiento realizado");
    }
    
}
