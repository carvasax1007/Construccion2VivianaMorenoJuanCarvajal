package app.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerRequest {
    private long veterinaryDocument;
    private String ownerName;
    private long ownerDocument;
    private int ownerAge;

    public long getVeterinaryDocument() { return veterinaryDocument; }
    public String getOwnerName() { return ownerName; }
    public long getOwnerDocument() { return ownerDocument; }
    public int getOwnerAge() { return ownerAge; }
} 