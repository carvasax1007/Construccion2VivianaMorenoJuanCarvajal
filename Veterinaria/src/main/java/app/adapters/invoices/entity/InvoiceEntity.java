package app.adapters.invoices.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;
    private Long petId;
    private Long ownerId;
    private Long sellerId;
    private Long medicalOrderId;
    private String productName;
    private Double value;
    private Integer quantity;
    private Date date;
}
