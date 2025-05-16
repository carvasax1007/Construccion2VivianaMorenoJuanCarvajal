package app.adapters.pets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.adapters.pets.entity.PetEntity;
import app.adapters.pets.repository.PetRepository;
import app.domain.models.Pet;
import app.ports.PetPort;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Service
public class PetAdapter implements PetPort {
    
    @Autowired
    private PetRepository petRepository;

    @Override 
    public boolean existPet(long petId) {
        return petRepository.existsById(petId);
    }

    @Override
    public void savePet(Pet pet) {
        PetEntity petEntity = toEntity(pet);
        petEntity.setPetId(null);
        petEntity = petRepository.save(petEntity);
        pet.setPetId(petEntity.getPetId());
    }
    
    @Override
    public Pet findByPetId(long petId) {
        PetEntity petEntity = petRepository.findByPetId(petId);
        if (petEntity == null) {
            return null;
        }
        return toModel(petEntity);
    }

    @Override
    public List<Pet> findByOwnerId(long ownerDocument) {
        List<PetEntity> petEntities = petRepository.findByOwnerDocument(ownerDocument);
        List<Pet> pets = new ArrayList<>();
        
        for (PetEntity petEntity : petEntities) {
            pets.add(toModel(petEntity));
        }
        return pets;
    }
    
    private PetEntity toEntity(Pet pet) {
        PetEntity petEntity = new PetEntity();
        petEntity.setAge(pet.getAge());
        petEntity.setColor(pet.getColor());
        petEntity.setName(pet.getName());
        petEntity.setOwnerDocument(pet.getOwnerDocument());
        petEntity.setRace(pet.getRace());
        petEntity.setSize(pet.getSize());
        petEntity.setSpecies(pet.getSpecies());
        petEntity.setWeight(pet.getWeight());
        return petEntity;
    }

    private Pet toModel(PetEntity petEntity) {
        Pet pet = new Pet();
        pet.setPetId(petEntity.getPetId());
        pet.setAge(petEntity.getAge());
        pet.setColor(petEntity.getColor());
        pet.setName(petEntity.getName());
        pet.setOwnerDocument(petEntity.getOwnerDocument());
        pet.setRace(petEntity.getRace());
        pet.setSize(petEntity.getSize());
        pet.setSpecies(petEntity.getSpecies());
        pet.setWeight(petEntity.getWeight());
        return pet;
    }
}
