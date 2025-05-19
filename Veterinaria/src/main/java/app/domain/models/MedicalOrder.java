package app.domain.models;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalOrder {
    
    private long medicalOrderId;
    private long petId;
    private long ownerId;
    private long veterinarianId;
    private String medication;
    private Date entryDate;
    private boolean canceled;
    private Long medicalHistoryId;

    public MedicalOrder(){}
    public MedicalOrder(long medicalOrderId, long petId, long ownerId, long veterinarianId, String medication, Date entryDate, boolean canceled, Long medicalHistoryId) {
        this.medicalOrderId = medicalOrderId;
        this.petId = petId;
        this.ownerId = ownerId;
        this.veterinarianId = veterinarianId;
        this.medication = medication;
        this.entryDate = entryDate;
        this.canceled = canceled;
        this.medicalHistoryId = medicalHistoryId;
    }

    public long getMedicalOrderId() {
        return medicalOrderId;
    }

    public long getPetId() {
        return petId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public long getVeterinarianId() {
        return veterinarianId;
    }

    public String getMedication() {
        return medication;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setMedicalOrderId(long medicalOrderId) {
        this.medicalOrderId = medicalOrderId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public void setVeterinarianId(long veterinarianId) {
        this.veterinarianId = veterinarianId;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
 
    public Long getMedicalHistoryId() {
        return medicalHistoryId;
    }
  
    public void setMedicalHistoryId(Long medicalHistoryId) {
        this.medicalHistoryId = medicalHistoryId;
    }
}
