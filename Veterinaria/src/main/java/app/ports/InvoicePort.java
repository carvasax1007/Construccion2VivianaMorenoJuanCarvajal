package app.ports;

import app.domain.models.Invoice;
import java.util.List;

public interface InvoicePort {
    void saveInvoice(Invoice invoice);
    Invoice findByInvoiceId(long invoiceId);
    List<Invoice> findByOwnerDocument(long ownerDocument);
}
