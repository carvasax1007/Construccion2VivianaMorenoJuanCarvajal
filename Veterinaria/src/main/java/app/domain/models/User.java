package app.domain.models;

import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author Viviana
 */
@Getter
@Setter


public class User extends Person {
    private String userName;
    private String password;

    
    public User(){     
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   

   
    public User(String userName, String password, long document, String name, String role, int age) {

        super(document, name, role, age);
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "userName=" + userName + ", password=" + password + '}' + super.toString();
    }
    
    

}
