package app.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalHistoryQueryRequest {
    private long veterinaryDocument;
    private long petId;
} 