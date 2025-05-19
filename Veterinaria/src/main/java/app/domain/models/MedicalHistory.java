package app.domain.models;
import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor


public class MedicalHistory {
    private Long petId;
    private Date registrationDate;
    private String veterinaryDoctor;
    private String reasonConsultation;
    private String symptomatology;
    private String diagnosis;
    private List<String> procedures;
    private List<MedicalOrder> medicalOrders;
    private List<String> vaccinationHistory;
    private List<String> medicationsAllergic;
    private String dateilprocedure;
    private boolean cancellationOrder;
    private Long medicalHistoryId;

    public Long getMedicalHistoryId() {
        return medicalHistoryId;
    }

    public void setMedicalHistoryId(Long medicalHistoryId) {
        this.medicalHistoryId = medicalHistoryId;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }
    
    

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getVeterinaryDoctor() {
        return veterinaryDoctor;
    }

    public String getReasonConsultation() {
        return reasonConsultation;
    }

    public String getSymptomatology() {
        return symptomatology;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public List<String> getProcedures() {
        return procedures;
    }

    public List<MedicalOrder> getMedicalOrders() {
        return medicalOrders;
        }

    public List<String> getVaccinationHistory() {
        return vaccinationHistory;
    }

    public List<String> getMedicationsAllergic() {
        return medicationsAllergic;
    }

    public String getDateilprocedure() {
        return dateilprocedure;
    }

    public boolean getCancellationOrder() {
        return cancellationOrder;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setVeterinaryDoctor(String veterinaryDoctor) {
        this.veterinaryDoctor = veterinaryDoctor;
    }

    public void setReasonConsultation(String reasonConsultation) {
        this.reasonConsultation = reasonConsultation;
    }

    public void setSymptomatology(String symptomatology) {
        this.symptomatology = symptomatology;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setProcedures(List<String> procedures) {
        this.procedures = procedures;
    }

    public void setMedicalOrders(List<MedicalOrder> medicalOrders) {
        this.medicalOrders = medicalOrders;
    }

    public void setVaccinationHistory(List<String> vaccinationHistory) {
        this.vaccinationHistory = vaccinationHistory;
    }

    public void setMedicationsAllergic(List<String> medicationsAllergic) {
        this.medicationsAllergic = medicationsAllergic;
    }

    public void setDateilprocedure(String dateilprocedure) {
        this.dateilprocedure = dateilprocedure;
    }

    public void setCancellationOrder(boolean cancellationOrder) {
        this.cancellationOrder = cancellationOrder;
    }

   
}