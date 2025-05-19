package app.adapters.invoices;

import app.adapters.invoices.entity.InvoiceEntity;
import app.adapters.invoices.repository.InvoiceRepository;
import app.domain.models.Invoice;
import app.ports.InvoicePort;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Setter
@Getter
@NoArgsConstructor
@Service


public class InvoiceAdapter implements InvoicePort{
    
    @Autowired
    private InvoiceRepository invoiceRepository;
    
    
    @Override 
    public void saveInvoice(Invoice invoice) {
        InvoiceEntity entity = toEntity(invoice);
        invoiceRepository.save(entity);
        invoice.setInvoiceId(entity.getInvoiceId());
    }


    @Override
    public List<Invoice> findByOwnerDocument(long ownerId) {
        List<InvoiceEntity> invoiceEntities = invoiceRepository.findByOwnerId(ownerId);
        List<Invoice> invoices = new ArrayList<>();
        
        for (InvoiceEntity entity : invoiceEntities) {
            invoices.add(toDomain(entity));
        }
        return invoices;
    }
    
    @Override
    public Invoice findByInvoiceId(long invoiceId) {
        InvoiceEntity invoiceEntity = invoiceRepository.findByInvoiceId(invoiceId);
        return toDomain(invoiceEntity);
    }    
    
    @Override
    public List<Invoice> findBySellerId(long sellerId) {
        List<InvoiceEntity> entities = invoiceRepository.findBySellerId(sellerId);
        List<Invoice> invoices = new ArrayList<>();
        if (entities != null) {
            for (InvoiceEntity entity : entities) {
                invoices.add(toDomain(entity));
            }
        }
        return invoices;
    }

    @Override
    public Invoice findById(long invoiceId) {
        InvoiceEntity entity = invoiceRepository.findById(invoiceId).orElse(null);
        if (entity != null) {
            return toDomain(entity);
        }
        return null;
    }
    
    private InvoiceEntity toEntity(Invoice invoice) {
        if (invoice == null) return null;
        
        InvoiceEntity entity = new InvoiceEntity();
        entity.setInvoiceId(invoice.getInvoiceId());
        entity.setPetId(invoice.getPetId());
        entity.setOwnerId(invoice.getOwnerId());
        entity.setSellerId(invoice.getSellerId());
        entity.setMedicalOrderId(invoice.getMedicalOrderId());
        entity.setProductName(invoice.getProductName());
        entity.setValue(invoice.getValue());
        entity.setQuantity(invoice.getQuantity());
        
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(invoice.getDate());
            entity.setDate(parsedDate);
        } catch (ParseException e) {
            throw new RuntimeException("Error al convertir la fecha: " + e.getMessage());
        }
        
        return entity;
    }
    
    private Invoice toDomain(InvoiceEntity entity) {
        if (entity == null) return null;
        
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(entity.getInvoiceId());
        invoice.setPetId(entity.getPetId());
        invoice.setOwnerId(entity.getOwnerId());
        invoice.setSellerId(entity.getSellerId());
        invoice.setMedicalOrderId(entity.getMedicalOrderId());
        invoice.setProductName(entity.getProductName());
        invoice.setValue(entity.getValue());
        invoice.setQuantity(entity.getQuantity());
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        invoice.setDate(dateFormat.format(entity.getDate()));
        
        return invoice;
    }
}
