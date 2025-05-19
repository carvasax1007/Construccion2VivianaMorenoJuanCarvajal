package app.adapters.invoices.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class InvoiceEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false, updatable = false)
    private Long invoiceId;
    
    @Column(name = "pet_id", nullable = false)
    private Long petId;
    
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;
    
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;
    
    @Column(name = "medical_order_id")
    private Long medicalOrderId;
    
    @Column(name = "product_name", nullable = false)
    private String productName;
    
    @Column(name = "value", nullable = false)
    private Double value;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
