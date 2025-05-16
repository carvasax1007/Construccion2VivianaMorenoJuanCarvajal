
package app.ports;

import app.domain.models.Person;

public interface PersonPort {

    /**
     *
     * @param Document
     * @return
     */
    public boolean existPerson(long Document);
    public void savePerson(Person person);
    public Person findByDocument(long Document);
    
}
