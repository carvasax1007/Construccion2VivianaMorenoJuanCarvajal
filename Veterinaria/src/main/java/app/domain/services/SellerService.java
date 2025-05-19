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
    
    public List<MedicalOrder> getMedicalOrders() {
        return medicalOrderPort.getAllMedicalOrders();
    }
    public void sellProduct(Invoice invoice) throws Exception{
        if (invoice.getMedicalOrderId() !=0){
            MedicalOrder order = medicalOrderPort.findById(invoice.getMedicalOrderId());
            if (order == null) {
                throw new Exception("No hay una orden medica");
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
