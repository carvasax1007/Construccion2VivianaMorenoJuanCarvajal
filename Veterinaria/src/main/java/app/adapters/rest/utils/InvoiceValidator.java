/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapters.inputs.utils;

import org.springframework.stereotype.Component;

/**
 *
 * @author Viviana
 */

@Component

public class InvoiceValidator extends SimpleValidator{
 
    public long invoiceIdValidator(String input) throws Exception{
        try {
            long invoiceId = Long.parseLong(input);
            if (invoiceId <=0){
                throw new Exception("recuerda el id de la factura debe ser mayor a 0");
            }
            return invoiceId;
        }catch (NumberFormatException e){
            throw new Exception ("formato no valido");
        }
    }

    public String productNameValidator(String value) throws Exception {
        return stringValidator(value, "Nombre del producto");
    }

    public double priceValidator(String value) throws Exception {
        double price = doubleValidator(value, "Precio");
        if (price <= 0) {
            throw new Exception("El precio debe ser mayor a 0.");
        }
        return price;
    }

    public int amountValidator(String value) throws Exception {
        int amount = integerValidator(value, "Cantidad");
        if (amount <= 0) {
            throw new Exception("La cantidad debe ser mayor a 0.");
        }
        return amount;
    }
}


