
package app.adapters.invoices.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;


@Entity
@Table (name = "invoice")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private long invoiceId;
    @Column (name="id_pet")
    private long petId;
    @Column (name = "owner_document")
    private long ownerDocument;
    @Column (name="product_name")
    private String productName;
    @Column (name = "invoice_date")
    private Date invoiceDate;
    @Column (name = "medical_order_ffk")
    private long medicalOrderId;
    @Column (name="price")
    private double price;
    @Column (name="amount")
    private int amount;

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    

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

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setMedicalOrderId(long medicalOrderId) {
        this.medicalOrderId = medicalOrderId;
    }
    

    
}
