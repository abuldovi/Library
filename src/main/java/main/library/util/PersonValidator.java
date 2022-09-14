package main.library.util;

import main.library.dao.PersonDao;
import main.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    @Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    private PersonDao personDao;


    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if(personDao.show(person.getName()).isPresent() && personDao.show(person.getName()).get().getId()!=person.getId() ){
            errors.rejectValue("name", "", "Person already exists");
        }
    }
}
