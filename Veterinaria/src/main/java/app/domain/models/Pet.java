package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor

public class Pet {
    private long petId;
    private String name;
    private long ownerDocument;
    private String species;
    private int age;
    private String color;
    private String race;
    private String size;
    private double weight;

    
    public long getPetId() {
        return petId;
    }

    public String getName() {
        return name;
    }

    public long getOwnerDocument() {
        return ownerDocument;
    }

    public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public String getColor() {
        return color;
    }

    public String getRace() {
        return race;
    }

    public String getSize() {
        return size;
    }

    public double getWeight() {
        return weight;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerDocument(long ownerDocument) {
        this.ownerDocument = ownerDocument;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


   

}
