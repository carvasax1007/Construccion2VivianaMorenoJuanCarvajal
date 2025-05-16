
package app.ports;

import app.domain.models.Invoice;
import java.util.List;


public interface InvoicePort {

    public void saveInvoice(Invoice invoice);

    public Invoice findByInvoiceId(long invoiceId);

    public List<Invoice> findByOwnerDocument(long ownerDocument);
    
    
}
