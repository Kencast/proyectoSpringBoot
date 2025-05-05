package com.example.demo.person;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.StoredProcedureQuery;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EntityManager entityManager;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getUserById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
    }

    public void insertPerson(Person person) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("new_person");
        query.setParameter("new_name", person.getPersonName());
        query.setParameter("new_first_name", person.getFirstName());
        query.setParameter("new_second_name", person.getSecondName());
        query.setParameter("new_user_name", person.getUserName());
        query.setParameter("new_birthdate", (person.getBirthdate()));
        query.setParameter("new_country", person.getCountry());
        query.setParameter("new_email", person.getEmail());
        query.setParameter("new_password", person.getPersonPassword());
        query.setParameter("new_image_link", person.getImageLink());
        query.execute();
    }

    public Long validatePerson(LoginInfo loginInfo) {
        return personRepository.findUserByLogin(loginInfo.getEmail(), loginInfo.getHashPassword());
    }

    public void deletePerson(Long id){
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("deletePerson");
        query.setParameter("d_person_id", id);
        query.execute();
    }

    public void updatePerson(Person person){
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("updatePerson");
        query.setParameter("u_person_id", person.getId());
        query.setParameter("u_person_name", person.getPersonName());
        query.setParameter("u_first_name", person.getFirstName());
        query.setParameter("u_second_name", person.getSecondName());
        query.setParameter("u_user_name", person.getUserName());
        query.setParameter("u_country", person.getCountry());
        query.setParameter("u_email", person.getEmail());
        query.setParameter("u_person_password", person.getPersonPassword());
        query.setParameter("u_image_link", person.getImageLink());
        query.execute();
    }
//ads

}
