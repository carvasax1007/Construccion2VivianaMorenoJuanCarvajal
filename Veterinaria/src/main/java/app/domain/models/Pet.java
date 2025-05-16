package app.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Pet {
    private Long petId;
    private String name;
    private int age;
    private String species;
    private String race;
    private String characteristics;
    private long ownerDocument;
    private String color;
    private String size;
    private double weight;

    public Long getPetId() {
        return petId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSpecies() {
        return species;
    }

    public String getRace() {
        return race;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public long getOwnerDocument() {
        return ownerDocument;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public double getWeight() {
        return weight;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public void setOwnerDocument(long ownerDocument) {
        this.ownerDocument = ownerDocument;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
