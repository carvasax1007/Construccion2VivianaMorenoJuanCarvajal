package app.adapters.inputs.utils;

import org.springframework.stereotype.Component;

@Component
public class PetValidator extends SimpleValidator {
    
    public String validateName(String value) throws Exception {
        return stringValidator(value, "nombre de la mascota");
    }
    
    public String validateSpecies(String value) throws Exception {
        return stringValidator(value, "especie de la mascota");
    }
    
    public String validateRace(String value) throws Exception {
        return stringValidator(value, "raza de la mascota");
    }
} 