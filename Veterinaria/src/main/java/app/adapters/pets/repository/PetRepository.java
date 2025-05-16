package app.adapters.pets.repository;

import app.adapters.pets.entity.PetEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<PetEntity, Long> {
    List<PetEntity> findByOwnerDocument(long ownerDocument);
    PetEntity findByPetId(Long petId);
}
