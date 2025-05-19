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

@RestController
@RequestMapping("/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {
    
    @Autowired
    private InvoiceAdapter invoiceAdapter;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private MedicalOrderRepository medicalOrderRepository;
    
    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice) {
        try {
            // Validar que el vendedor existe y es un vendedor
            if (!personRepository.existsByDocumentAndRole(invoice.getSellerId(), "seller")) {
                return ResponseEntity.badRequest().body("El vendedor no existe o no tiene el rol correcto");
            }
            
            // Validar que la mascota existe
            if (!personRepository.existsById(invoice.getPetId())) {
                return ResponseEntity.badRequest().body("La mascota no existe");
            }
            
            // Validar que el dueño existe
            if (!personRepository.existsByDocument(invoice.getOwnerId())) {
                return ResponseEntity.badRequest().body("El dueño no existe");
            }
            
            // Validar que la orden médica existe si se proporciona
            if (invoice.getMedicalOrderId() != null && invoice.getMedicalOrderId() > 0) {
                if (!medicalOrderRepository.existsById(invoice.getMedicalOrderId())) {
                    return ResponseEntity.badRequest().body("La orden médica no existe");
                }
            }
            
            // Validar el formato de la fecha
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false); // Esto hace que la validación sea más estricta
                dateFormat.parse(invoice.getDate()); // Solo validamos el formato
            } catch (ParseException e) {
                return ResponseEntity.badRequest().body("Formato de fecha inválido. Use el formato yyyy-MM-dd");
            }
            
            invoiceAdapter.saveInvoice(invoice);
            return ResponseEntity.ok("Se ha registrado la factura exitosamente");
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la factura: " + e.getMessage());
        }
    }
} 