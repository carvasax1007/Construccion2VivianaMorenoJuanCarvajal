package app.adapters.invoices.controller;

import app.adapters.invoices.InvoiceAdapter;
import app.adapters.persons.repository.PersonRepository;
import app.adapters.medicalOrders.repository.MedicalOrderRepository;
import app.domain.models.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import app.Exceptions.AuthenticationException;
import app.Exceptions.BusinessException;
import app.domain.services.SellerService;

@RestController
@RequestMapping("/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {
    
    @Autowired
    private SellerService sellerService;
    
    @PostMapping
    public ResponseEntity createInvoice(@RequestBody Invoice invoice) {
        try {
            // Establecer la fecha automáticamente y convertirla a String
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = dateFormat.format(new java.util.Date());
            invoice.setDate(currentDate);

            // Guardar factura
            sellerService.sellProduct(invoice);

            return new ResponseEntity("Factura creada exitosamente", HttpStatus.CREATED);

        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            // Si la mascota no existe, es un error 404
            if (be.getMessage().contains("mascota") && be.getMessage().contains("no existe")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.NOT_FOUND);
            }
            // Si el propietario no existe, es un error 404
            if (be.getMessage().contains("dueño") && be.getMessage().contains("no existe")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.NOT_FOUND);
            }
            // Si la orden médica no existe, es un error 404
            if (be.getMessage().contains("orden médica") && be.getMessage().contains("no existe")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.NOT_FOUND);
            }
            // Si la factura es nula, es un error 400
            if (be.getMessage().contains("no puede ser nula")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.BAD_REQUEST);
            }
            // Si la mascota no pertenece al dueño, es un error 409
            if (be.getMessage().contains("no pertenece al dueño")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
            }
            // Si la orden médica no corresponde a la mascota, es un error 409
            if (be.getMessage().contains("no corresponde a la mascota")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
            }
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Solo errores internos del servidor deberían llegar aquí
            return new ResponseEntity("Error interno del servidor al crear la factura", 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 