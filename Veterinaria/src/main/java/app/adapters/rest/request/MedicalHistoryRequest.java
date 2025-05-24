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
} 