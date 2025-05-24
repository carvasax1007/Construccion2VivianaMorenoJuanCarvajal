package app.domain.models;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor

public class Invoice {
    
    private Long invoiceId;
    private Long petId;
    private Long ownerId;
    private Long sellerId;
    private Long medicalOrderId;
    private String productName;
    private Double value;
    private Integer quantity;
    private String date;

}
