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

    public Long getInvoiceId() {
        return invoiceId;
    }

    public Long getPetId() {
        return petId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public Long getMedicalOrderId() {
        return medicalOrderId;
    }

    public String getProductName() {
        return productName;
    }

    public Double getValue() {
        return value;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public void setMedicalOrderId(Long medicalOrderId) {
        this.medicalOrderId = medicalOrderId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
