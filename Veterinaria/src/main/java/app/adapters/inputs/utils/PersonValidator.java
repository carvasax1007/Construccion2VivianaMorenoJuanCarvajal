package app.adapters.inputs.utils;

import org.springframework.stereotype.Component;

@Component
public class PersonValidator extends SimpleValidator {
    
    public String nameValidator(String value) throws Exception {
        return stringValidator(value, "nombre de la persona");
    }
    
    public long documentValidator(String value) throws Exception {
        return longValidator(value, "numero de documento");
    }
    
    public int ageValidator(String value) throws Exception {
        int age = integerValidator(value, "edad");
        if (age < 18 || age > 90) {
            throw new Exception("La edad debe estar entre 18 y 90 años");
        }
        return age;
    }
} 