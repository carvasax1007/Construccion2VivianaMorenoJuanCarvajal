/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.ports;
import app.domain.models.User;
import app.domain.models.Person;


/**
 *
 * @author Viviana
 */
public interface UserPort {
      
    public void saveUser(User user);

    public boolean existUserName(String username);


    public User findById(long userId);

    public User findByUserName(String username);

    public User findByPersonId(Person person);
}
