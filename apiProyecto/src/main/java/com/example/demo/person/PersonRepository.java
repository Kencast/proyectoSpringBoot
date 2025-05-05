package com.example.demo.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query(value = "SELECT get_person_password_id_by_email(:email, :password) FROM DUAL", nativeQuery = true)
    Long findUserByLogin(@Param("email") String email, @Param("password") Long userId);
}
