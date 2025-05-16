/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Viviana
 */

@Getter
@Setter
public class VeterinarianRequest {
    private String name;
    private long document;
    private int age;
    private String userName;
    private String password;
    // Credenciales del administrador
    private String adminUserName;
    private String adminPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDocument() {
        return document;
    }

    public void setDocument(long document) {
        this.document = document;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return "VeterinarianRequest{" + "name=" + name + ", document=" + document + ", age=" + age + ", userName=" + userName + ", password=" + password + ", adminUserName=" + adminUserName + ", adminPassword=" + adminPassword + '}';
    }
    
    
    
    
}
