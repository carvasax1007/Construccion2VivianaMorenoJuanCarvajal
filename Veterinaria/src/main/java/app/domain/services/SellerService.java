
package app.domain.services;

import app.domain.models.Invoice;
import app.domain.models.MedicalOrder;
import app.ports.InvoicePort;
import app.ports.MedicalOrderPort;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class SellerService {
    
   @Autowired
   private InvoicePort invoicePort;
   
   @Autowired
   private MedicalOrderPort medicalOrderPort;
    
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
}
