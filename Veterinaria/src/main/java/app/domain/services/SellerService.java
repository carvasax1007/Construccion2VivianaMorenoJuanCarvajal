package app.domain.services;

import app.domain.models.Invoice;
import app.domain.models.MedicalOrder;
import app.domain.models.Person;
import app.ports.InvoicePort;
import app.ports.MedicalOrderPort;
import app.ports.PersonPort;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import app.Exceptions.BusinessException;
import app.Exceptions.AuthenticationException;
import app.domain.models.Pet;
import app.ports.PetPort;

@Getter
@Setter
@NoArgsConstructor
@Service
public class SellerService {
    
   @Autowired
   private InvoicePort invoicePort;
   
   @Autowired
   private MedicalOrderPort medicalOrderPort;
   
   @Autowired
   private PersonPort personPort;
   
   @Autowired
   private PetPort petPort;
    
    public List<MedicalOrder> getMedicalOrders() {
        return medicalOrderPort.getAllMedicalOrders();
    }
    public void sellProduct(Invoice invoice) throws Exception {
        if (invoice == null) {
            throw new BusinessException("La factura no puede ser nula");
        }

        // Verificar que el vendedor exista y sea válido
        Person seller = personPort.findByDocument(invoice.getSellerId());
        if (seller == null) {
            throw new AuthenticationException("El vendedor con documento " + invoice.getSellerId() + " no existe");
        }
        if (!"seller".equalsIgnoreCase(seller.getRole())) {
            throw new AuthenticationException("El usuario con documento " + invoice.getSellerId() + " no tiene permisos de vendedor");
        }

        // Verificar que la mascota exista
        if (!petPort.existPet(invoice.getPetId())) {
            throw new BusinessException("La mascota con ID " + invoice.getPetId() + " no existe");
        }

        // Verificar que el dueño exista
        Person owner = personPort.findByDocument(invoice.getOwnerId());
        if (owner == null) {
            throw new BusinessException("El dueño con documento " + invoice.getOwnerId() + " no existe");
        }

        // Verificar que la mascota corresponda al dueño
        Pet pet = petPort.findById(invoice.getPetId());
        if (pet == null) {
            throw new BusinessException("Error al obtener información de la mascota");
        }
        if (pet.getOwnerDocument() != invoice.getOwnerId()) {
            throw new BusinessException("La mascota con ID " + invoice.getPetId() + 
                " no pertenece al dueño con documento " + invoice.getOwnerId());
        }

        // Verificar que la orden médica exista si se proporciona
        if (invoice.getMedicalOrderId() != null && invoice.getMedicalOrderId() > 0) {
            MedicalOrder order = medicalOrderPort.findById(invoice.getMedicalOrderId());
            if (order == null) {
                throw new BusinessException("La orden médica con ID " + invoice.getMedicalOrderId() + " no existe");
            }
            // Verificar que la orden médica corresponda a la mascota
            if (order.getPetId() != invoice.getPetId()) {
                throw new BusinessException("La orden médica no corresponde a la mascota especificada");
            }
        }

        invoicePort.saveInvoice(invoice);
    }
    public Invoice getInvoiceById(long invoiceId) throws Exception{
        Invoice invoice = invoicePort.findByInvoiceId(invoiceId);
        if (invoice == null){
            throw new Exception("no hay una factura");
        }
        return invoice;
    }
    public List<Invoice> getInvoicesByOwner(long ownerDocument) throws Exception {
        List<Invoice> invoices = invoicePort.findByOwnerDocument(ownerDocument);
        if (invoices.isEmpty()) {
            throw new Exception("No hay facturas registradas para este dueño");
        }
        return invoices;
    }

    public List<Invoice> getInvoicesBySeller(long sellerDocument) throws Exception {
        // Verificar que el vendedor exista y sea válido
        Person seller = personPort.findByDocument(sellerDocument);
        if (seller == null || !"seller".equalsIgnoreCase(seller.getRole())) {
            throw new Exception("El vendedor con documento " + sellerDocument + " no existe o no tiene permisos.");
        }

        // Obtener las facturas
        List<Invoice> invoices = invoicePort.findBySellerId(sellerDocument);
        if (invoices.isEmpty()) {
            throw new Exception("No se encontraron facturas para el vendedor con documento " + sellerDocument);
        }

        return invoices;
    }

    public Invoice getInvoiceById(long invoiceId, long sellerDocument) throws Exception {
        // Verificar que el vendedor exista y sea válido
        Person seller = personPort.findByDocument(sellerDocument);
        if (seller == null || !"seller".equalsIgnoreCase(seller.getRole())) {
            throw new Exception("El vendedor con documento " + sellerDocument + " no existe o no tiene permisos.");
        }

        // Obtener la factura
        Invoice invoice = invoicePort.findById(invoiceId);
        if (invoice == null) {
            throw new Exception("No se encontró la factura con ID " + invoiceId);
        }

        // Verificar que la factura pertenezca al vendedor
        if (invoice.getSellerId() != sellerDocument) {
            throw new Exception("La factura no pertenece al vendedor especificado");
        }

        return invoice;
    }
}
