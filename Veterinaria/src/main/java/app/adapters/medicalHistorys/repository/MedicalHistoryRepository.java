

package app.adapters.medicalHistorys.repository;

import app.adapters.medicalHistorys.entity.MedicalHistoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MedicalHistoryRepository extends JpaRepository<MedicalHistoryEntity, Long>{
    
    List<MedicalHistoryEntity> findByPet_PetId(long petId);

    
}
