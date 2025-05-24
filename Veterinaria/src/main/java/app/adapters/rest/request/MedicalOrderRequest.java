package app.adapters.rest.request;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalOrderRequest {
    private long veterinaryDocument;
    private Long petId;
    private Long ownerId;
    private String medication;
    private Date entryDate;
    private Long medicalHistoryId;

    public long getVeterinaryDocument() { return veterinaryDocument; }
    public Long getPetId() { return petId; }
    public Long getOwnerId() { return ownerId; }
    public String getMedication() { return medication; }
    public Long getMedicalHistoryId() { return medicalHistoryId; }
} 