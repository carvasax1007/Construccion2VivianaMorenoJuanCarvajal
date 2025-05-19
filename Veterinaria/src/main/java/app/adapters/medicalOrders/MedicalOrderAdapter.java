/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapters.medicalOrders;

import app.adapters.medicalOrders.entity.MedicalOrderEntity;
import app.adapters.medicalOrders.repository.MedicalOrderRepository;
import app.adapters.medicalHistorys.entity.MedicalHistoryEntity;
import app.domain.models.MedicalOrder;
import app.ports.MedicalOrderPort;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Viviana
 */
@Service
@NoArgsConstructor
public class MedicalOrderAdapter implements MedicalOrderPort{
    
    @Autowired
    private MedicalOrderRepository medicalOrderRepository;

    @Autowired
    private app.adapters.medicalHistorys.repository.MedicalHistoryRepository medicalHistoryRepository;

    @Override
    public void save(MedicalOrder medicalOrder){
        MedicalOrderEntity medicalOrderEntity = medicalOrderEntityAdapter(medicalOrder);
        
        // Si hay una historia clínica asociada, establecer la relación
        if (medicalOrder.getMedicalHistoryId() != null) {
            MedicalHistoryEntity historyEntity = medicalHistoryRepository.findById(medicalOrder.getMedicalHistoryId())
                .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada"));
            
            medicalOrderEntity.setMedicalHistory(historyEntity);
            historyEntity.getMedicalOrders().add(medicalOrderEntity);
        }
        
        // Guardar la orden médica una sola vez
        medicalOrderRepository.save(medicalOrderEntity);
        medicalOrder.setMedicalOrderId(medicalOrderEntity.getMedicalOrderId());
    }
    @Override
    public MedicalOrder findById(long medicalOrderId){
        MedicalOrderEntity entity = medicalOrderRepository.findById(medicalOrderId).orElse(null);
        if (entity!=null){
            return toDomain(entity);
        }else{
            return null;
        }
    }
    @Override
    public void cancel(long medicalOrderId){
        MedicalOrderEntity entity = medicalOrderRepository.findById(medicalOrderId)
            .orElseThrow(() -> new RuntimeException("Orden médica no encontrada"));
        
            entity.setCanceled(true);
            medicalOrderRepository.save(entity);
        
        // La orden permanece en la historia clínica, solo se marca como cancelada
    }
    @Override
    public List<MedicalOrder> findByPetId(long petId){
        List<MedicalOrderEntity> entities = medicalOrderRepository.findByPetId(petId);
        List<MedicalOrder> medicalOrders = new ArrayList<>();
        if (entities != null){
            for (MedicalOrderEntity entity : entities){
                medicalOrders.add(toDomain(entity));
            }
        }
        return medicalOrders;
    }
    @Override
    public List<MedicalOrder> getAllMedicalOrders(){
        List<MedicalOrderEntity> entities = medicalOrderRepository.findAll();
        List<MedicalOrder> medicalOrders = new ArrayList<>();
        if (entities != null){
            for (MedicalOrderEntity entity : entities){
                medicalOrders.add(toDomain(entity));
            }
        }
        return medicalOrders;
    }
            
    private MedicalOrderEntity medicalOrderEntityAdapter(MedicalOrder medicalOrder){
        if (medicalOrder == null) return null;
        MedicalOrderEntity entity = new MedicalOrderEntity();
        
        // Solo establecer el ID si es una actualización
        if (medicalOrder.getMedicalOrderId() > 0) {
        entity.setMedicalOrderId(medicalOrder.getMedicalOrderId());
        }
        
        entity.setPetId(medicalOrder.getPetId());
        entity.setCanceled(medicalOrder.isCanceled());
        entity.setEntryDate(medicalOrder.getEntryDate());
        entity.setVeterinarianId(medicalOrder.getVeterinarianId());
        entity.setOwnerId(medicalOrder.getOwnerId());
        entity.setMedication(medicalOrder.getMedication());
        
        // Configurar la relación con MedicalHistory si existe el ID
        if (medicalOrder.getMedicalHistoryId() != null) {
            MedicalHistoryEntity historyEntity = medicalHistoryRepository.getReferenceById(medicalOrder.getMedicalHistoryId());
            entity.setMedicalHistory(historyEntity);
        }
        
        return entity;
    }
    private MedicalOrder toDomain(MedicalOrderEntity entity){
        if (entity == null) return null;
        MedicalOrder medicalOrder = new MedicalOrder();
        medicalOrder.setMedicalOrderId(entity.getMedicalOrderId());
        medicalOrder.setPetId(entity.getPetId());
        medicalOrder.setOwnerId(entity.getOwnerId());
        medicalOrder.setVeterinarianId(entity.getVeterinarianId());
        medicalOrder.setMedication(entity.getMedication());
        medicalOrder.setEntryDate(entity.getEntryDate());
        medicalOrder.setCanceled(entity.isCanceled());
        if (entity.getMedicalHistory() != null) {
            medicalOrder.setMedicalHistoryId(entity.getMedicalHistory().getMedicalHistoryId());
        }
        return medicalOrder;
    }
}
