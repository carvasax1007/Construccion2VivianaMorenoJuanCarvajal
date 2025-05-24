package app.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalHistoryRequest {
    private long veterinaryDocument;
    private String reasonConsultation;
    private String symptomatology;
    private String diagnosis;
    private String detailProcedure;
    private Long petId;

    public long getVeterinaryDocument() { return veterinaryDocument; }
    public String getReasonConsultation() { return reasonConsultation; }
    public String getSymptomatology() { return symptomatology; }
    public String getDiagnosis() { return diagnosis; }
    public String getDetailProcedure() { return detailProcedure; }
    public Long getPetId() { return petId; }
} 