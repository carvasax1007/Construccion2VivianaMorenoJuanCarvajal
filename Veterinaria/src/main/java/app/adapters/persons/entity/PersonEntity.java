
package app.adapters.persons.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="person")

public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_person") 
    private Long id; 
        
    /*@Column(name = "id_person")
    private long personId;*/
    @Column(name="document")
    private long document;
    @Column (name= "name")
    private String name;
    @Column(name = "role")
    private String role;
    @Column(name = "age")
    private int age;



    public long getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getAge() {
        return age;
    }

    public void setDocument(long document) {
        this.document = document;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    


    /*public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
