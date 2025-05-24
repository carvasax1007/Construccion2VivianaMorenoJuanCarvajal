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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {
    
    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);
    
    @Autowired
    private SellerService sellerService;
    
    @PostMapping
    public ResponseEntity createInvoice(@RequestBody Invoice invoice) {
        try {
            // Validaciones de campos requeridos
            if (invoice == null) {
                return new ResponseEntity("La factura no puede ser nula", HttpStatus.BAD_REQUEST);
            }
            if (invoice.getPetId() == null) {
                return new ResponseEntity("El ID de la mascota es requerido", HttpStatus.BAD_REQUEST);
            }
            if (invoice.getOwnerId() == null) {
                return new ResponseEntity("El ID del propietario es requerido", HttpStatus.BAD_REQUEST);
            }
            if (invoice.getSellerId() == null) {
                return new ResponseEntity("El ID del vendedor es requerido", HttpStatus.BAD_REQUEST);
            }
            if (invoice.getProductName() == null || invoice.getProductName().trim().isEmpty()) {
                return new ResponseEntity("El nombre del producto es requerido", HttpStatus.BAD_REQUEST);
            }
            if (invoice.getValue() == null || invoice.getValue() <= 0) {
                return new ResponseEntity("El valor debe ser mayor a cero", HttpStatus.BAD_REQUEST);
            }
            if (invoice.getQuantity() == null || invoice.getQuantity() <= 0) {
                return new ResponseEntity("La cantidad debe ser mayor a cero", HttpStatus.BAD_REQUEST);
            }

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
            log.error("Error al crear la factura", e);
            return new ResponseEntity("Error interno del servidor al crear la factura", 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 