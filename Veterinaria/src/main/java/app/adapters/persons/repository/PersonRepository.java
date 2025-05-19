/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.adapters.persons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.adapters.persons.entity.PersonEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    boolean existsByDocument(long document);
    PersonEntity findByDocument(long document);
    boolean existsByDocumentAndRole(Long document, String role);
}
