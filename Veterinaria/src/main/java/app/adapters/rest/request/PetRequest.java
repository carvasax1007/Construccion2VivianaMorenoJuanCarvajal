package app.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetRequest {
    private long veterinaryDocument;
    private String petName;
    private int petAge;
    private String species;
    private String race;
    private String characteristics;
    private String color;
    private String size;
    private double weight;
    private long ownerDocument;
} 