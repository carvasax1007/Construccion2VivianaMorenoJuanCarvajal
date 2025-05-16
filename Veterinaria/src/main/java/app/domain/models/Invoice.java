
package app.domain.models;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor

public class Invoice {
    
    private long invoiceId;
    private long petId;
    private long ownerDocument;
    private String productName;
    private double price;
    private int amount;
    private Date invoiceDate;
    private long medicalOrderId;

    public long getInvoiceId() {
        return invoiceId;
    }

    public long getPetId() {
        return petId;
    }

    public long getOwnerDocument() {
        return ownerDocument;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public long getMedicalOrderId() {
        return medicalOrderId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public void setOwnerDocument(long ownerDocument) {
        this.ownerDocument = ownerDocument;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setMedicalOrderId(long medicalOrderId) {
        this.medicalOrderId = medicalOrderId;
    }

}
