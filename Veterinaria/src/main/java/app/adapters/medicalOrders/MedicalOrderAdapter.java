/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapters.medicalOrders;

import app.adapters.medicalOrders.entity.MedicalOrderEntity;
import app.adapters.medicalOrders.repository.MedicalOrderRepository;
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
    @Override
    public void save(MedicalOrder medicalOrder){
        MedicalOrderEntity medicalOrderEntity = medicalOrderEntityAdapter(medicalOrder);
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
        MedicalOrderEntity entity = medicalOrderRepository.findById(medicalOrderId).orElse(null);
        if (entity!=null){
            entity.setCanceled(true);
            medicalOrderRepository.save(entity);
        }
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
        entity.setMedicalOrderId(medicalOrder.getMedicalOrderId());
        entity.setPetId(medicalOrder.getPetId());
        entity.setCanceled(medicalOrder.isCanceled());
        entity.setEntryDate(medicalOrder.getEntryDate());
        entity.setVeterinarianId(medicalOrder.getVeterinarianId());
        entity.setOwnerId(medicalOrder.getOwnerId());
        entity.setMedication(medicalOrder.getMedication());
        return entity;
    }
    private MedicalOrder toDomain(MedicalOrderEntity entity){
        if (entity == null ) return null;
        return new MedicalOrder(
        entity.getMedicalOrderId(),
        entity.getPetId(),
        entity.getOwnerId(),
        entity.getVeterinarianId(),
        entity.getMedication(),
        entity.getEntryDate(),
        entity.isCanceled());
    }
    private MedicalOrder medicalOrderEntityAdapter(MedicalOrderEntity entity){
        if (entity == null) return null;
        MedicalOrder medicalOrder = new MedicalOrder(0,0,0,0,"", new Date(), false);
        medicalOrder.setCanceled(entity.isCanceled());
        medicalOrder.setEntryDate(entity.getEntryDate());
        medicalOrder.setMedicalOrderId(entity.getMedicalOrderId());
        medicalOrder.setMedication(entity.getMedication());
        medicalOrder.setOwnerId(entity.getOwnerId());
        medicalOrder.setPetId(entity.getPetId());
        medicalOrder.setVeterinarianId(entity.getVeterinarianId());
        return medicalOrder;
    }
                
}
