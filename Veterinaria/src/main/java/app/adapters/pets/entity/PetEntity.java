
package app.adapters.pets.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="pet")
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private long petId;
    @Column(name= "name")
    private String name;
    @Column(name = "owner_document")
    private long ownerDocument;
    @Column(name = "species")
    private String species;
    @Column(name = "age")
    private int age;
    @Column(name = "color")
    private String color;
    @Column(name = "race")
    private String race;
    @Column(name = "size")
    private String size;
    @Column(name = "weight")
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
