package app.domain.services;

import app.domain.models.MedicalHistory;
import app.domain.models.MedicalOrder;
import app.domain.models.Person;
import app.domain.models.Pet;
import app.ports.MedicalHistoryPort;
import app.ports.MedicalOrderPort;
import app.ports.PersonPort;
import app.ports.PetPort;

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

    public void registerPet(Pet pet) throws Exception {
        // Verificar que el dueño exista
        if (!personPort.existPerson(pet.getOwnerDocument())) {
            throw new Exception("El dueño con documento " + pet.getOwnerDocument() + " no existe");
        }
         petPort.savePet(pet);
    }

    public void registerOwner(Person owner) throws Exception {
        if (personPort.existPerson(owner.getDocument())) {
            throw new Exception("Ya existe persona con este documento");
        }
        personPort.savePerson(owner);
    }

    public void createMedicalHistory(MedicalHistory medicalHistory) throws Exception {
        if (medicalHistory == null) {
            throw new Exception("El historial médico no puede ser nulo.");
        }
        
        // Verificar que la mascota exista
        if (!petPort.existPet(medicalHistory.getPetId())) {
            throw new Exception("La mascota con ID " + medicalHistory.getPetId() + " no existe");
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
            throw new Exception("La orden medica no puede ser vacia");
        }
        
        // Verificar que la mascota exista
        if (!petPort.existPet(medicalOrder.getPetId())) {
            throw new Exception("La mascota no está registrada.");
        }

        // Verificar que el propietario exista
        if (!personPort.existPerson(medicalOrder.getOwnerId())) {
            throw new Exception("El propietario con documento " + medicalOrder.getOwnerId() + " no existe.");
        }

        // Verificar que la historia clínica exista
        MedicalHistory medicalHistory = medicalHistoryPort.findById(medicalOrder.getMedicalHistoryId());
        if (medicalHistory == null) {
            throw new Exception("La historia clínica especificada no existe.");
        }

        // Verificar que la historia clínica corresponda a la mascota
        if (!medicalHistory.getPetId().equals(medicalOrder.getPetId())) {
            throw new Exception("La historia clínica no corresponde a la mascota especificada.");
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
        if (veterinarian == null || !"veterinarian".equalsIgnoreCase(veterinarian.getRole())) {
            throw new Exception("El veterinario con documento " + veterinaryDocument + " no existe o no tiene permisos.");
        }

        // Obtener la orden médica
        MedicalOrder medicalOrder = medicalOrderPort.findById(medicalOrderId);
        if (medicalOrder == null) {
            throw new Exception("La orden médica con ID " + medicalOrderId + " no existe.");
        }

        // Verificar que la orden no esté ya cancelada
        if (medicalOrder.isCanceled()) {
            throw new Exception("La orden médica ya está cancelada.");
        }

        // Cancelar la orden
        medicalOrderPort.cancel(medicalOrderId);
    }

    public MedicalHistory getMedicalHistoryByPetId(long petId, long veterinaryDocument) throws Exception {
        // Verificar que el veterinario exista y sea válido
        Person veterinarian = personPort.findByDocument(veterinaryDocument);
        if (veterinarian == null || !"veterinarian".equalsIgnoreCase(veterinarian.getRole())) {
            throw new Exception("El veterinario con documento " + veterinaryDocument + " no existe o no tiene permisos.");
        }

        // Obtener la historia clínica
        List<MedicalHistory> histories = medicalHistoryPort.findByPetId(petId);
        if (histories.isEmpty()) {
            throw new Exception("No se encontró historia clínica para la mascota con ID " + petId);
        }

        // Retornar la historia clínica más reciente
        return histories.get(0);
    }
}

