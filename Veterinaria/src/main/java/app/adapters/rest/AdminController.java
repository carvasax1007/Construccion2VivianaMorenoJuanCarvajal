/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapters.rest;

import app.Exceptions.BusinessException;
import app.Exceptions.InputsException;
import app.Exceptions.AuthenticationException;
import app.adapters.validators.PersonValidator;
import app.adapters.validators.UserValidator;
import app.adapters.rest.request.SellerRequest;
import app.adapters.rest.request.VeterinarianRequest;
import app.domain.models.User;
import app.domain.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Viviana
 */
@RestController
public class AdminController {
    
    @Autowired
    private AdminService adminservice;
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private UserValidator userValidator;
    
    @GetMapping("/")
    public String itsAlive(){
        return"i'm alive";
    }
    
    @GetMapping("/ping")
    public String ping(){
        return"pong";
    }
    
    @PostMapping("/veterinarian")
    public ResponseEntity registerVeterinarian(@RequestBody VeterinarianRequest request) {
        try {
            User veterinarian = new User();
            veterinarian.setName(personValidator.nameValidator(request.getName()));
            veterinarian.setDocument(request.getDocument());
            veterinarian.setAge(personValidator.ageValidator(String.valueOf(request.getAge())));
            veterinarian.setPassword(userValidator.passwordValidator(request.getPassword()));
            veterinarian.setUserName(userValidator.userNameValidator(request.getUserName()));
            adminservice.registerVeterinarian(veterinarian, request.getAdminUserName(), request.getAdminPassword());
            return new ResponseEntity("Se ha registrado el veterinario", HttpStatus.CREATED);
        
        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Capturamos cualquier otra excepción y la manejamos como un error de validación
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/seller")
    public ResponseEntity registerSeller(@RequestBody SellerRequest request) {
        try {
            User seller = new User();
            seller.setName(personValidator.nameValidator(request.getName()));
            seller.setDocument(request.getDocument());
            seller.setAge(personValidator.ageValidator(String.valueOf(request.getAge())));
            seller.setPassword(userValidator.passwordValidator(request.getPassword()));
            seller.setUserName(userValidator.userNameValidator(request.getUserName()));
            adminservice.registerSeller(seller, request.getAdminUserName(), request.getAdminPassword());
            return new ResponseEntity("Se ha registrado el vendedor", HttpStatus.CREATED);
        
        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Capturamos cualquier otra excepción y la manejamos como un error de validación
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
