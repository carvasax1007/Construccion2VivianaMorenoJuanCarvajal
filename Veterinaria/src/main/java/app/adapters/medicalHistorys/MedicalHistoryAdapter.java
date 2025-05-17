/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapters.medicalHistorys;

import app.adapters.medicalHistorys.entity.MedicalHistoryEntity;
import app.adapters.medicalHistorys.repository.MedicalHistoryRepository;
import app.adapters.medicalOrders.entity.MedicalOrderEntity;
import app.adapters.pets.entity.PetEntity;
import app.domain.models.MedicalHistory;
import app.domain.models.MedicalOrder;
import app.ports.MedicalHistoryPort;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Viviana
 */
@Service
public class MedicalHistoryAdapter implements MedicalHistoryPort{
    
    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;
    
    @Override
    public void save(MedicalHistory medicalHistory){
        MedicalHistoryEntity medicalHistoryEntity = medicalHistoryEntityAdapter(medicalHistory);
        medicalHistoryRepository.save(medicalHistoryEntity);
        medicalHistory.setMedicalHistoryId(medicalHistoryEntity.getMedicalHistoryId());
    }
    
    @Override
    public void update(MedicalHistory medicalHistory){
        Optional<MedicalHistoryEntity> optionalEntity = medicalHistoryRepository.findById(medicalHistory.getMedicalHistoryId());
        
        if (optionalEntity.isPresent()){
            MedicalHistoryEntity existingEntity = optionalEntity.get();
            
            existingEntity.setRegistrationDate(medicalHistory.getRegistrationDate());
            existingEntity.setVeterinaryDoctor(medicalHistory.getVeterinaryDoctor());
            existingEntity.setReasonConsultation(medicalHistory.getReasonConsultation());
            existingEntity.setSymptomatology(medicalHistory.getSymptomatology());
            existingEntity.setDiagnosis(medicalHistory.getDiagnosis());
            existingEntity.setCancellationOrder(medicalHistory.getCancellationOrder());
            existingEntity.setDetailProcedure(medicalHistory.getDateilprocedure());

            medicalHistoryRepository.save(existingEntity);
        }
    }
    @Override
    public MedicalHistory findById(long medicalHistoryId){
        MedicalHistoryEntity entity = medicalHistoryRepository.findById(medicalHistoryId).orElse(null);
        if (entity!=null){
            return toDomain(entity);
        }else{
            return null;
        } 
    }
    @Override 
    public List<MedicalHistory> findByPetId(long petId){
        List<MedicalHistoryEntity> entities = medicalHistoryRepository.findByPet_PetId(petId);
        List<MedicalHistory> medicalHistories = new ArrayList<>();
        if (entities!= null){
            for (MedicalHistoryEntity entity : entities){
                medicalHistories.add(toDomain(entity));
            }
        }
        return medicalHistories;
    }
    private MedicalHistory toDomain(MedicalHistoryEntity entity){
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setMedicalHistoryId(entity.getMedicalHistoryId());
        medicalHistory.setPetId(entity.getPet().getPetId());
        medicalHistory.setRegistrationDate(entity.getRegistrationDate());
        medicalHistory.setVeterinaryDoctor(entity.getVeterinaryDoctor());
        medicalHistory.setReasonConsultation(entity.getReasonConsultation());
        medicalHistory.setSymptomatology(entity.getSymptomatology());
        medicalHistory.setDiagnosis(entity.getDiagnosis());
        medicalHistory.setCancellationOrder(entity.isCancellationOrder());
        medicalHistory.setDateilprocedure(entity.getDetailProcedure());

        return medicalHistory;
    }
    private MedicalHistoryEntity medicalHistoryEntityAdapter(MedicalHistory medicalHistory){
        MedicalHistoryEntity entity = new MedicalHistoryEntity();
        
        // Configurar la mascota
        PetEntity petEntity = new PetEntity();
        petEntity.setPetId(medicalHistory.getPetId());
        entity.setPet(petEntity);
        
        entity.setRegistrationDate(medicalHistory.getRegistrationDate());
        entity.setVeterinaryDoctor(medicalHistory.getVeterinaryDoctor());
        entity.setReasonConsultation(medicalHistory.getReasonConsultation());
        entity.setSymptomatology(medicalHistory.getSymptomatology());
        entity.setDiagnosis(medicalHistory.getDiagnosis());
        entity.setCancellationOrder(medicalHistory.getCancellationOrder());
        entity.setDetailProcedure(medicalHistory.getDateilprocedure());

        return entity;
    }
    private MedicalOrder toDomain(MedicalOrderEntity entity){
        if (entity == null) return null;
        return new MedicalOrder(
            entity.getMedicalOrderId(),
            entity.getPetId(),
            entity.getOwnerId(),
            entity.getVeterinarianId(),
            entity.getMedication(),
            entity.getEntryDate(),
            entity.isCanceled());
    }
    private MedicalOrderEntity toEntity(MedicalOrder order){
        if (order==null) return null;
        MedicalOrderEntity entity = new MedicalOrderEntity();
        entity.setCanceled(order.isCanceled());
        entity.setEntryDate(order.getEntryDate());
        entity.setMedicalOrderId(order.getMedicalOrderId());
        entity.setMedication(order.getMedication());
        entity.setOwnerId(order.getOwnerId());
        entity.setVeterinarianId(order.getVeterinarianId());
        entity.setPetId(order.getPetId());
        return entity;
    }


}