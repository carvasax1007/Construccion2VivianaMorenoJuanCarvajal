package app.adapters.rest.request;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalHistoryRequest {
    private long veterinaryDocument;
    private Date registrationDate;
    private String reasonConsultation;
    private String symptomatology;
    private String diagnosis;
    private String detailProcedure;
    private Long petId;
} 