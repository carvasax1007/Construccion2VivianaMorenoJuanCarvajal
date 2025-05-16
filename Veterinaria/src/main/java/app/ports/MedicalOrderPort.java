/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.ports;

import app.domain.models.MedicalOrder;
import java.util.List;

/**
 *
 * @author Viviana
 */
public interface MedicalOrderPort {

    public void save(MedicalOrder medicalOrder);

    public List<MedicalOrder> findByPetId(long petId);
    
    public void cancel(long medicalOrder);
    public MedicalOrder findById(long orderId);

    public List<MedicalOrder> getAllMedicalOrders();

    
}
