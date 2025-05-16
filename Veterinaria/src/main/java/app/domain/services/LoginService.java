package app.domain.services;

import app.domain.models.User;
import app.ports.UserPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service
public class LoginService {

    @Autowired
    private UserPort userPort;


    public User login(User user) throws Exception {
        User userValidate = userPort.findByUserName(user.getUserName());
        if (userValidate == null) {
            throw new Exception("Usuario  incorrectos.");
        }
        if(!user.getPassword().equals(userValidate.getPassword())){
            throw new Exception("contraseña invalido");
        }
        return userValidate;
    }   
}
