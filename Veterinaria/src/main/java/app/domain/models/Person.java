package app.domain.models;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter


public class Person {
    
    private long document;
    private String name;
    private String role;
    private int age;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
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

    public Person(long document, String name, String role, int age) {
        this.document = document;
        this.name = name;
        this.role = role;
        this.age = age;
    }
    public Person(long document, String name, String role){
        this.document= document;
        this.name = name;
        this.role = role;
    }
    
    public Person(){}

    @Override
    public String toString() {
        return "Person{" + "document=" + document + ", name=" + name + ", role=" + role + ", age=" + age + '}';
    }

   
    
    
}
