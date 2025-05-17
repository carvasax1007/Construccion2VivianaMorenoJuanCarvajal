package app.domain.services;

import app.Exceptions.AuthenticationException;
import app.domain.models.Person;
import app.ports.PersonPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private PersonPort personPort;

    public Person authenticateVeterinarian(long document) throws AuthenticationException {
        Person person = personPort.findByDocument(document);
        
        if (person == null) {
            throw new AuthenticationException("Veterinario no encontrado");
        }

        // Verificar que sea un veterinario
        if (!"veterinarian".equals(person.getRole().toLowerCase())) {
            throw new AuthenticationException("El usuario no es un veterinario");
        }

        return person;
    }
} 