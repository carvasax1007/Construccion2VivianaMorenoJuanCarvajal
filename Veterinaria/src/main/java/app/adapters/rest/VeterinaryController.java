package app.adapters.rest;

import app.Exceptions.AuthenticationException;
import app.Exceptions.BusinessException;
import app.Exceptions.InputsException;
import app.adapters.inputs.utils.MedicalHistoryValidator;
import app.adapters.inputs.utils.MedicalOrderValidator;
import app.adapters.inputs.utils.PersonValidator;
import app.adapters.inputs.utils.PetValidator;
import app.adapters.rest.request.*;
import app.domain.models.*;
import app.domain.services.AuthenticationService;
import app.domain.services.VeterinarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            medicalHistory.setRegistrationDate(request.getRegistrationDate());
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
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity("Error al crear la historia clínica: " + e.getMessage(), 
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
            medicalOrder.setEntryDate(request.getEntryDate());
            medicalOrder.setCanceled(false);
            medicalOrder.setMedicalHistoryId(request.getMedicalHistoryId());

            // Guardar orden médica
            veterinarianService.generateOrder(medicalOrder);

            return new ResponseEntity("Orden médica creada exitosamente", HttpStatus.CREATED);

        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity("Error al crear la orden médica: " + e.getMessage(), 
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
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity("Error al cancelar la orden médica: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/medical-history/query")
    public ResponseEntity getMedicalHistory(@RequestBody MedicalHistoryQueryRequest request) {
        try {
            MedicalHistory history = veterinarianService.getMedicalHistoryByPetId(
                request.getPetId(), 
                request.getVeterinaryDocument()
            );
            return new ResponseEntity(history, HttpStatus.OK);
        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
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
            return new ResponseEntity(veterinarianService.getMedicalOrderById(orderId), 
                HttpStatus.OK);

        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 