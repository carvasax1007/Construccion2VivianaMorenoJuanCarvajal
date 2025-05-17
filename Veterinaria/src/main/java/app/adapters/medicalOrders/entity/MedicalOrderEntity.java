/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapters.medicalOrders.entity;

import app.adapters.medicalHistorys.entity.MedicalHistoryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.FetchType;

/**
 *
 * @author Viviana
 */
@Getter
@Setter
@Entity
@Table(name="medical_order")
public class MedicalOrderEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="medical_id", nullable = false, updatable = false)
    private Long medicalOrderId;
    
    @Column(name="id_pet")
    private long petId;
    
    @Column(name="owner_document")
    private long ownerId;
    
    @Column(name="veterinarian_document")
    private long veterinarianId;
    
    @Column(name="medicaction")
    private String medication;
    
    @Column(name="entry_date_medical")
    private Date entryDate;
    
    @Column(name="canceled")
    private boolean canceled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_history_id")
    private MedicalHistoryEntity medicalHistory;

    public Long getMedicalOrderId() {
        return medicalOrderId;
    }

    public void setMedicalOrderId(Long medicalOrderId) {
        this.medicalOrderId = medicalOrderId;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getVeterinarianId() {
        return veterinarianId;
    }

    public void setVeterinarianId(long veterinarianId) {
        this.veterinarianId = veterinarianId;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public MedicalHistoryEntity getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(MedicalHistoryEntity medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
}
