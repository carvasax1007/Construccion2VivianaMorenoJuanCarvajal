/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.adapters.medicalOrders.repository;

import app.adapters.medicalOrders.entity.MedicalOrderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Viviana
 */
public interface MedicalOrderRepository extends JpaRepository<MedicalOrderEntity, Long>{
    
    public List<MedicalOrderEntity> findByPetId(long petId);
    public List<MedicalOrderEntity> findByOwnerId(long ownerId);
    public List<MedicalOrderEntity> findByVeterinarianId(long veterinarianId);
}
