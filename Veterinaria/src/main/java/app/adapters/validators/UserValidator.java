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
public class UserValidator extends SimpleValidator{
    
    public String userNameValidator(String value) throws Exception{
        return stringValidator(value, " nombre de usuario ");
    }
    public String passwordValidator(String value) throws Exception{
        return stringValidator(value, " contraseña de usuario ");
    }
} 