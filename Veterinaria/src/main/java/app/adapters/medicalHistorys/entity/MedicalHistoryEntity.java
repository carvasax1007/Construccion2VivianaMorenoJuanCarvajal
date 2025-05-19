/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapters.medicalHistorys.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import app.adapters.medicalOrders.entity.MedicalOrderEntity;
import app.adapters.pets.entity.PetEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Viviana
 */
@Entity
@Table(name = "medical_history")
@Getter
@Setter
@NoArgsConstructor
public class MedicalHistoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="medical_history_id")
    private Long medicalHistoryId;
    
    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private PetEntity pet;

    
    @Column(name="registration_date")
    private Date registrationDate;
    
    @Column(name="veterinary_doctor")
    private String veterinaryDoctor;
    
    @Column(name= "reason_consultation")
    private String reasonConsultation;
    
    @Column(name="symptomatology")
    private String symptomatology;
    
    @Column(name="diagnosis")
    private String diagnosis;
    
    /*@ElementCollection
    @CollectionTable(name="medical_procedures", joinColumns= @JoinColumn(name="medical_history_id"))
    @Column(name="procedures")
    private List<String> procedures;
    
   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name="medical_order_id", referencedColumnName = "medical_order_id")
   private MedicalOrder medicalOrder;
   
   @ElementCollection
   @CollectionTable(name="vaccination_history", joinColumns = @JoinColumn(name="medical_history_id"))
   @Column (name="vaccine")
   private List<String> vaccinationHistory;
   
   @ElementCollection
   @CollectionTable(name="medications_allergic", joinColumns= @JoinColumn(name="medical_history_name"))
   @Column(name="medication")
   private List<String> medicationAllergic;*/
   
   @Column(name= "detail_procedure")
   private String detailProcedure;
   
   @Column(name="cancellation_order")
   private boolean cancellationOrder;

    @OneToMany(mappedBy = "medicalHistory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalOrderEntity> medicalOrders = new ArrayList<>();

    public Long getMedicalHistoryId() {
        return medicalHistoryId;
    }

    public void setMedicalHistoryId(Long medicalHistoryId) {
        this.medicalHistoryId = medicalHistoryId;
    }

    public PetEntity getPet() {
        return pet;
    }

    public void setPet(PetEntity pet) {
        this.pet = pet;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getVeterinaryDoctor() {
        return veterinaryDoctor;
    }

    public void setVeterinaryDoctor(String veterinaryDoctor) {
        this.veterinaryDoctor = veterinaryDoctor;
    }

    public String getReasonConsultation() {
        return reasonConsultation;
    }

    public void setReasonConsultation(String reasonConsultation) {
        this.reasonConsultation = reasonConsultation;
    }

    public String getSymptomatology() {
        return symptomatology;
    }

    public void setSymptomatology(String symptomatology) {
        this.symptomatology = symptomatology;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDetailProcedure() {
        return detailProcedure;
    }

    public void setDetailProcedure(String detailProcedure) {
        this.detailProcedure = detailProcedure;
    }

    public boolean isCancellationOrder() {
        return cancellationOrder;
    }

    public void setCancellationOrder(boolean cancellationOrder) {
        this.cancellationOrder = cancellationOrder;
    }

    public List<MedicalOrderEntity> getMedicalOrders() {
        return medicalOrders;
    }

    public void setMedicalOrders(List<MedicalOrderEntity> medicalOrders) {
        this.medicalOrders = medicalOrders;
    }


 
   
}
