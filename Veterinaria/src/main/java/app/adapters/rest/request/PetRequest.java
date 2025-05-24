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

    public long getVeterinaryDocument() { return veterinaryDocument; }
    public String getPetName() { return petName; }
    public int getPetAge() { return petAge; }
    public String getSpecies() { return species; }
    public String getRace() { return race; }
    public String getCharacteristics() { return characteristics; }
    public String getColor() { return color; }
    public String getSize() { return size; }
    public double getWeight() { return weight; }
    public long getOwnerDocument() { return ownerDocument; }
} 