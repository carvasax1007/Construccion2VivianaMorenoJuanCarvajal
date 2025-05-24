package app.adapters.rest;

import app.Exceptions.AuthenticationException;
import app.Exceptions.BusinessException;
import app.Exceptions.InputsException;
import app.adapters.validators.MedicalHistoryValidator;
import app.adapters.validators.MedicalOrderValidator;
import app.adapters.validators.PersonValidator;
import app.adapters.validators.PetValidator;
import app.adapters.rest.request.*;
import app.domain.models.*;
import app.domain.services.AuthenticationService;
import app.domain.services.VeterinarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VeterinaryController {

    @Autowired
    private VeterinarianService veterinarianService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MedicalHistoryValidator medicalHistoryValidator;

    @Autowired
    private MedicalOrderValidator medicalOrderValidator;

    @Autowired
    private PersonValidator personValidator;

    @Autowired
    private PetValidator petValidator;

    @PostMapping("/owner")
    public ResponseEntity registerOwner(@RequestBody OwnerRequest request) {
        try {
            // Autenticar veterinario
            Person veterinarian = authenticationService.authenticateVeterinarian(
                request.getVeterinaryDocument()
            );

            // Crear y validar dueño
            Person owner = new Person();
            owner.setName(personValidator.nameValidator(request.getOwnerName()));
            owner.setDocument(request.getOwnerDocument());
            owner.setAge(request.getOwnerAge());

            // Registrar dueño
            veterinarianService.registerOwner(owner);

            return new ResponseEntity("Dueño registrado exitosamente", HttpStatus.CREATED);

        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity("Error al registrar el dueño: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/pet")
    public ResponseEntity registerPet(@RequestBody PetRequest request) {
        try {
            // Autenticar veterinario
            Person veterinarian = authenticationService.authenticateVeterinarian(
                request.getVeterinaryDocument()
            );

            // Crear y validar mascota
            Pet pet = new Pet();
            pet.setName(petValidator.validateName(request.getPetName()));
            pet.setAge(request.getPetAge());
            pet.setSpecies(petValidator.validateSpecies(request.getSpecies()));
            pet.setRace(petValidator.validateRace(request.getRace()));
            pet.setCharacteristics(request.getCharacteristics());
            pet.setColor(request.getColor());
            pet.setSize(request.getSize());
            pet.setWeight(request.getWeight());
            pet.setOwnerDocument(request.getOwnerDocument());

            // Registrar mascota
            veterinarianService.registerPet(pet);

            return new ResponseEntity("Mascota registrada exitosamente", HttpStatus.CREATED);

        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity("Error al registrar la mascota: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/medical-history")
    public ResponseEntity createMedicalHistory(@RequestBody MedicalHistoryRequest request) {
        try {
            // Autenticar veterinario
            Person veterinarian = authenticationService.authenticateVeterinarian(
                request.getVeterinaryDocument()
            );

            // Crear y validar historia clínica
            MedicalHistory medicalHistory = new MedicalHistory();
            medicalHistory.setPetId(request.getPetId());
            medicalHistory.setRegistrationDate(new java.sql.Date(System.currentTimeMillis()));
            medicalHistory.setVeterinaryDoctor(veterinarian.getName());
            medicalHistory.setReasonConsultation(
                medicalHistoryValidator.reasonConsultationValidator(request.getReasonConsultation())
            );
            medicalHistory.setSymptomatology(request.getSymptomatology());
            medicalHistory.setDiagnosis(
                medicalHistoryValidator.diagnosisValidator(request.getDiagnosis())
            );
            medicalHistory.setDateilprocedure(
                medicalHistoryValidator.procedureValidator(request.getDetailProcedure())
            );

            // Guardar historia clínica
            veterinarianService.createMedicalHistory(medicalHistory);

            return new ResponseEntity("Historia clínica creada exitosamente", HttpStatus.CREATED);

        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            // Si la mascota no existe, es un error 404
            if (be.getMessage().contains("no existe")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.NOT_FOUND);
            }
            // Si el historial médico es nulo, es un error 400
            if (be.getMessage().contains("no puede ser nulo")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Solo errores internos del servidor deberían llegar aquí
            return new ResponseEntity("Error interno del servidor al crear la historia clínica", 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/medical-order")
    public ResponseEntity createMedicalOrder(@RequestBody MedicalOrderRequest request) {
        try {
            // Autenticar veterinario
            Person veterinarian = authenticationService.authenticateVeterinarian(
                request.getVeterinaryDocument()
            );

            // Crear y validar orden médica
            MedicalOrder medicalOrder = new MedicalOrder();
            medicalOrder.setPetId(request.getPetId());
            medicalOrder.setOwnerId(request.getOwnerId());
            medicalOrder.setVeterinarianId(veterinarian.getDocument());
            medicalOrder.setMedication(
                medicalOrderValidator.medicationValidator(request.getMedication())
            );
            medicalOrder.setEntryDate(new java.sql.Date(System.currentTimeMillis()));
            medicalOrder.setCanceled(false);
            medicalOrder.setMedicalHistoryId(request.getMedicalHistoryId());

            // Guardar orden médica
            veterinarianService.generateOrder(medicalOrder);

            return new ResponseEntity("Orden médica creada exitosamente", HttpStatus.CREATED);

        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            // Si la mascota no existe, es un error 404
            if (be.getMessage().contains("mascota") && be.getMessage().contains("no existe")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.NOT_FOUND);
            }
            // Si el propietario no existe, es un error 404
            if (be.getMessage().contains("propietario") && be.getMessage().contains("no existe")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.NOT_FOUND);
            }
            // Si la historia clínica no existe, es un error 404
            if (be.getMessage().contains("historia clínica") && be.getMessage().contains("no existe")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.NOT_FOUND);
            }
            // Si la orden médica es nula, es un error 400
            if (be.getMessage().contains("no puede ser nula")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.BAD_REQUEST);
            }
            // Si el propietario no corresponde a la mascota, es un error 409
            if (be.getMessage().contains("no es el dueño")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
            }
            // Si la historia clínica no corresponde a la mascota, es un error 409
            if (be.getMessage().contains("no corresponde")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
            }
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Solo errores internos del servidor deberían llegar aquí
            return new ResponseEntity("Error interno del servidor al crear la orden médica", 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/medical-order/cancel")
    public ResponseEntity cancelMedicalOrder(@RequestBody CancelOrderRequest request) {
        try {
            veterinarianService.cancelMedicalOrder(request.getOrderId(), request.getVeterinaryDocument());
            return new ResponseEntity("Orden médica cancelada exitosamente", HttpStatus.OK);
        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            // Si la orden médica no existe, es un error 404
            if (be.getMessage().contains("no existe")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.NOT_FOUND);
            }
            // Si la orden ya está cancelada, es un error 409
            if (be.getMessage().contains("ya está cancelada")) {
                return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
            }
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Solo errores internos del servidor deberían llegar aquí
            return new ResponseEntity("Error interno del servidor al cancelar la orden médica", 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/medical-history")
    public ResponseEntity getMedicalHistory(
            @RequestParam long petId,
            @RequestParam long veterinaryDocument) {
        try {
            MedicalHistory history = veterinarianService.getMedicalHistoryByPetId(
                petId, 
                veterinaryDocument
            );
            return new ResponseEntity(history, HttpStatus.OK);
        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Error al obtener la historia clínica: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/medical-order/{orderId}")
    public ResponseEntity getMedicalOrder(
            @PathVariable long orderId,
            @RequestParam long veterinaryDocument) {
        try {
            // Autenticar veterinario
            authenticationService.authenticateVeterinarian(veterinaryDocument);

            // Obtener orden médica
            MedicalOrder order = veterinarianService.getMedicalOrderById(orderId);
            if (order == null) {
                return new ResponseEntity("No se encontró la orden médica", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(order, HttpStatus.OK);

        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Error al obtener la orden médica: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pet")
    public ResponseEntity getPet(
            @RequestParam long petId,
            @RequestParam long veterinaryDocument) {
        try {
            Pet pet = veterinarianService.getPetById(petId, veterinaryDocument);
            return new ResponseEntity(pet, HttpStatus.OK);
        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Error al obtener los datos de la mascota: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/owner")
    public ResponseEntity getOwner(
            @RequestParam long ownerDocument,
            @RequestParam long veterinaryDocument) {
        try {
            Person owner = veterinarianService.getOwnerByDocument(ownerDocument, veterinaryDocument);
            return new ResponseEntity(owner, HttpStatus.OK);
        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Error al obtener los datos del propietario: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/medical-orders")
    public ResponseEntity getMedicalOrders(
            @RequestParam long petId,
            @RequestParam long userDocument) {
        try {
            List<MedicalOrder> orders = veterinarianService.getMedicalOrdersByPetId(petId, userDocument);
            return new ResponseEntity(orders, HttpStatus.OK);
        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Error al obtener las órdenes médicas: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 