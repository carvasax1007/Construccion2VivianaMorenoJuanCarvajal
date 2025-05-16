package app.domain.services;

import app.Exceptions.BusinessException;
import app.Exceptions.AuthenticationException;
import app.ports.UserPort;
import app.domain.models.User;
import app.ports.PersonPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@NoArgsConstructor
@Service
public class AdminService {
    
    @Autowired
    private UserPort userPort;
    
    @Autowired
    private PersonPort personPort;
    
    private void validateAdminCredentials(String adminUserName, String adminPassword) throws AuthenticationException {
        User admin = userPort.findByUserName(adminUserName);
        if (admin == null || !admin.getPassword().equals(adminPassword) || !"ADMIN".equals(admin.getRole())) {
            throw new AuthenticationException("Credenciales de administrador inválidas o usuario no es administrador");
        }
    }
    
    public void registerUser(User user) throws Exception {
        if (personPort.existPerson(user.getDocument())) {
            throw new BusinessException("numero de documento ya en uso");
        }
        if (userPort.existUserName(user.getUserName())){
            throw new BusinessException("Nombre ya en uso");
        }
        personPort.savePerson(user);
        userPort.saveUser(user);
    }
    
    public void registerSeller(User seller, String adminUserName, String adminPassword) throws Exception {
        validateAdminCredentials(adminUserName, adminPassword);
        
        if (personPort.existPerson(seller.getDocument())){
            throw new BusinessException("Ya hay una persona con esta cedula");
        }
        if (userPort.existUserName(seller.getUserName())){
            throw new BusinessException("nombre de usuario ya esta en uso, prueba con otro");
        }
        seller.setRole("seller");
        personPort.savePerson(seller);
        userPort.saveUser(seller);
    }
    
    public void registerVeterinarian(User veterinarian, String adminUserName, String adminPassword) throws Exception {
        validateAdminCredentials(adminUserName, adminPassword);
        
        if(personPort.existPerson(veterinarian.getDocument())){
            throw new BusinessException("numero de cedula ya en uso, revisa la informacion");
        }
        if (userPort.existUserName(veterinarian.getUserName())){
            throw new BusinessException("nombre de usuario ya en uso");
        }
        veterinarian.setRole("veterinarian");
        personPort.savePerson(veterinarian);
        userPort.saveUser(veterinarian);
    }
}
