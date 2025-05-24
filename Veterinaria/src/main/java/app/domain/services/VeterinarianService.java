package app.domain.services;

import app.domain.models.MedicalHistory;
import app.domain.models.MedicalOrder;
import app.domain.models.Person;
import app.domain.models.Pet;
import app.ports.MedicalHistoryPort;
import app.ports.MedicalOrderPort;
import app.ports.PersonPort;
import app.ports.PetPort;
import app.adapters.validators.PersonValidator;
import app.adapters.validators.PetValidator;
import app.Exceptions.InputsException;
import app.Exceptions.AuthenticationException;
import app.Exceptions.BusinessException;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service
public class VeterinarianService {

    @Autowired
    private MedicalHistoryPort medicalHistoryPort;

    @Autowired
    private PetPort petPort;

    @Autowired
    private PersonPort personPort;

    @Autowired
    private MedicalOrderPort medicalOrderPort;

    @Autowired
    private PersonValidator personValidator;

    @Autowired
    private PetValidator petValidator;

    public void registerPet(Pet pet) throws Exception {
        // Verificar que el dueño exista
        if (!personPort.existPerson(pet.getOwnerDocument())) {
            throw new Exception("El dueño con documento " + pet.getOwnerDocument() + " no existe");
        }

        // Validar la edad de la mascota
        try {
            pet.setAge(petValidator.ageValidator(String.valueOf(pet.getAge())));
        } catch (Exception e) {
            throw new InputsException("Error en la validación de edad: " + e.getMessage());
        }

        // Validar el peso de la mascota
        try {
            pet.setWeight(petValidator.weightValidator(String.valueOf(pet.getWeight())));
        } catch (Exception e) {
            throw new InputsException("Error en la validación del peso: " + e.getMessage());
        }

         petPort.savePet(pet);
    }

    public void registerOwner(Person owner) throws Exception {
        if (personPort.existPerson(owner.getDocument())) {
            throw new Exception("Ya existe persona con este documento");
        }
        
        // Validar la edad del propietario
        try {
            owner.setAge(personValidator.ageValidator(String.valueOf(owner.getAge())));
        } catch (Exception e) {
            throw new InputsException("Error en la validación de edad: " + e.getMessage());
        }
        
        personPort.savePerson(owner);
    }

    public void createMedicalHistory(MedicalHistory medicalHistory) throws Exception {
        if (medicalHistory == null) {
            throw new BusinessException("El historial médico no puede ser nulo.");
        }
        
        // Verificar que la mascota exista
        if (!petPort.existPet(medicalHistory.getPetId())) {
            throw new BusinessException("La mascota con ID " + medicalHistory.getPetId() + " no existe");
        }
        
        medicalHistoryPort.save(medicalHistory);
    }

    public List<MedicalHistory> getMedicalHistoryByPetId(String petId) throws Exception {
        List<MedicalHistory> historyList = medicalHistoryPort.findByPetId(Long.parseLong(petId));

        if (historyList.isEmpty()) {
            throw new Exception("No se encontró un historial médico para esta mascota.");
        }
        return historyList;
}

    public void updateMedicalHistory(MedicalHistory medicalHistory) throws Exception {
        if (medicalHistory == null) {
            throw new Exception("El historial médico no puede ser nulo.");
        }
        medicalHistoryPort.update(medicalHistory);
    }

    public void generateOrder(MedicalOrder medicalOrder) throws Exception {
        if (medicalOrder == null) {
            throw new BusinessException("La orden médica no puede ser nula");
        }
        
        // Verificar que la mascota exista
        if (!petPort.existPet(medicalOrder.getPetId())) {
            throw new BusinessException("La mascota con ID " + medicalOrder.getPetId() + " no existe");
        }

        // Verificar que el propietario exista
        if (!personPort.existPerson(medicalOrder.getOwnerId())) {
            throw new BusinessException("El propietario con documento " + medicalOrder.getOwnerId() + " no existe");
        }

        // Verificar que el propietario corresponda a la mascota
        Pet pet = petPort.findById(medicalOrder.getPetId());
        if (pet == null) {
            throw new BusinessException("Error al obtener información de la mascota");
        }
        if (pet.getOwnerDocument() != medicalOrder.getOwnerId()) {
            throw new BusinessException("El propietario con documento " + medicalOrder.getOwnerId() + 
                " no es el dueño de la mascota con ID " + medicalOrder.getPetId());
        }

        // Verificar que la historia clínica exista
        MedicalHistory medicalHistory = medicalHistoryPort.findById(medicalOrder.getMedicalHistoryId());
        if (medicalHistory == null) {
            throw new BusinessException("La historia clínica especificada no existe");
        }

        // Verificar que la historia clínica corresponda a la mascota
        if (!medicalHistory.getPetId().equals(medicalOrder.getPetId())) {
            throw new BusinessException("La historia clínica no corresponde a la mascota especificada");
        }

        medicalOrderPort.save(medicalOrder);
    }

    public void cancelOrder(MedicalOrder medicalOrder) throws Exception {
        if (medicalOrder == null) {
            throw new Exception("La orden medica no existe");
        }
        MedicalOrder existingOrder = medicalOrderPort.findById(medicalOrder.getMedicalOrderId());
        if (existingOrder == null) {
        throw new Exception("La orden médica no existe.");
        }
        existingOrder.setCanceled(true); 
        medicalOrderPort.save(existingOrder);
    }

    public MedicalOrder getMedicalOrderById(long medicalOrderId) throws Exception {
    MedicalOrder order = medicalOrderPort.findById(medicalOrderId);
    
    if (order == null) {
        throw new Exception("No se encontró una orden médica con el ID: " + medicalOrderId);
    }
    return order;
}

    public void cancelMedicalOrder(long medicalOrderId, long veterinaryDocument) throws Exception {
        // Verificar que el veterinario exista y sea válido
        Person veterinarian = personPort.findByDocument(veterinaryDocument);
        if (veterinarian == null) {
            throw new AuthenticationException("El veterinario con documento " + veterinaryDocument + " no existe");
        }
        if (!"veterinarian".equalsIgnoreCase(veterinarian.getRole())) {
            throw new AuthenticationException("El usuario con documento " + veterinaryDocument + " no tiene permisos de veterinario");
        }

        // Obtener la orden médica
        MedicalOrder medicalOrder = medicalOrderPort.findById(medicalOrderId);
        if (medicalOrder == null) {
            throw new BusinessException("La orden médica con ID " + medicalOrderId + " no existe");
        }

        // Verificar que la orden no esté ya cancelada
        if (medicalOrder.isCanceled()) {
            throw new BusinessException("La orden médica ya está cancelada");
        }

        // Cancelar la orden
        medicalOrderPort.cancel(medicalOrderId);
    }

    public MedicalHistory getMedicalHistoryByPetId(long petId, long veterinaryDocument) throws Exception {
        // Verificar que el veterinario exista y sea válido
        Person veterinarian = personPort.findByDocument(veterinaryDocument);
        if (veterinarian == null) {
            throw new AuthenticationException("El veterinario con documento " + veterinaryDocument + " no existe");
        }
        if (!"veterinarian".equalsIgnoreCase(veterinarian.getRole())) {
            throw new AuthenticationException("El usuario con documento " + veterinaryDocument + " no tiene permisos de veterinario");
        }

        // Obtener la historia clínica
        List<MedicalHistory> histories = medicalHistoryPort.findByPetId(petId);
        if (histories.isEmpty()) {
            throw new BusinessException("No se encontró historia clínica para la mascota con ID " + petId);
        }

        // Retornar la historia clínica más reciente
        return histories.get(0);
    }

    public Pet getPetById(long petId, long veterinaryDocument) throws Exception {
        // Verificar que el veterinario exista y sea válido
        Person veterinarian = personPort.findByDocument(veterinaryDocument);
        if (veterinarian == null) {
            throw new AuthenticationException("El veterinario con documento " + veterinaryDocument + " no existe");
        }
        if (!"veterinarian".equalsIgnoreCase(veterinarian.getRole())) {
            throw new AuthenticationException("El usuario con documento " + veterinaryDocument + " no tiene permisos de veterinario");
        }

        // Obtener la mascota
        Pet pet = petPort.findById(petId);
        if (pet == null) {
            throw new BusinessException("No se encontró la mascota con ID " + petId);
        }

        return pet;
    }

    public Person getOwnerByDocument(long ownerDocument, long veterinaryDocument) throws Exception {
        // Verificar que el veterinario exista y sea válido
        Person veterinarian = personPort.findByDocument(veterinaryDocument);
        if (veterinarian == null) {
            throw new AuthenticationException("El veterinario con documento " + veterinaryDocument + " no existe");
        }
        if (!"veterinarian".equalsIgnoreCase(veterinarian.getRole())) {
            throw new AuthenticationException("El usuario con documento " + veterinaryDocument + " no tiene permisos de veterinario");
        }

        // Obtener el dueño
        Person owner = personPort.findByDocument(ownerDocument);
        if (owner == null) {
            throw new BusinessException("No se encontró el propietario con documento " + ownerDocument);
        }

        return owner;
    }

    public List<MedicalOrder> getMedicalOrdersByPetId(long petId, long userDocument) throws Exception {
        // Verificar que el usuario exista y tenga el rol correcto
        Person user = personPort.findByDocument(userDocument);
        if (user == null) {
            throw new AuthenticationException("El usuario con documento " + userDocument + " no existe");
        }
        if (!"veterinarian".equalsIgnoreCase(user.getRole()) && !"seller".equalsIgnoreCase(user.getRole())) {
            throw new AuthenticationException("El usuario con documento " + userDocument + " no tiene permisos");
        }

        // Verificar que la mascota exista
        if (!petPort.existPet(petId)) {
            throw new BusinessException("La mascota con ID " + petId + " no existe");
        }

        // Obtener las órdenes médicas
        List<MedicalOrder> orders = medicalOrderPort.findByPetId(petId);
        if (orders.isEmpty()) {
            throw new BusinessException("No se encontraron órdenes médicas para la mascota con ID " + petId);
        }

        return orders;
    }
}

