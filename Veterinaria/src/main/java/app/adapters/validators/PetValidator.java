package app.adapters.validators;

import org.springframework.stereotype.Component;
import app.Exceptions.InputsException;

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
    public int ageValidator(String value) throws Exception {
        try {
            int age = Integer.parseInt(value);
            if (age < 0 || age > 300) {
                throw new InputsException("La edad de la mascota debe estar entre 0 y 300 años");
            }
            return age;
        } catch (NumberFormatException e) {
            throw new InputsException("La edad debe ser un número válido");
        }
    }
    public double weightValidator(String value) throws Exception {
        try {
            double weight = Double.parseDouble(value);
            if (weight < 0 || weight > 500) {
                throw new InputsException("El peso de la mascota debe estar entre 0 y 500 kg");
            }
            return weight;
        } catch (NumberFormatException e) {
            throw new InputsException("El peso debe ser un número válido");
        }
    }
} 