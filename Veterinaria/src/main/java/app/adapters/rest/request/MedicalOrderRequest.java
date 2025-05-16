package app.adapters.rest.request;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalOrderRequest {
    private long veterinaryDocument;
    private String veterinaryName;
    private Long petId;
    private Long ownerId;
    private String medication;
    private Date entryDate;
} 