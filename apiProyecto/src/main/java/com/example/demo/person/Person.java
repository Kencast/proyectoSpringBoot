package com.example.demo.person;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@NamedStoredProcedureQuery(name = "new_person", procedureName = "new_person", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_name", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_first_name", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_second_name", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_user_name", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_birthdate", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_country", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_email", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_password", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_image_link", type = String.class)
})

@NamedStoredProcedureQuery(name = "deletePerson", procedureName = "delete_person", parameters ={
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "d_person_id", type = Long.class)
})

@NamedStoredProcedureQuery(name="updatePerson", procedureName = "update_person", parameters ={
        @StoredProcedureParameter(mode=ParameterMode.IN, name = "u_person_id", type=Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "u_person_name", type=String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "u_first_name", type=String.class),
        @StoredProcedureParameter(mode= ParameterMode.IN, name = "u_second_name", type=String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "u_user_name", type=String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "u_country", type=String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "u_email",type=String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "u_person_password", type=Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "u_image_link",type = String.class)
})

public class Person {
    @Id
    @Column(name = "PERSON_ID", nullable = false)
    private Long id;

    @Column(name = "PERSON_NAME", length = 20)
    private String personName;

    @Column(name = "FIRST_NAME", length = 20)
    private String firstName;

    @Column(name = "SECOND_NAME", length = 20)
    private String secondName;

    @Column(name = "USER_NAME", length = 40)
    private String userName;

    @Column(name = "BIRTHDATE")
    private LocalDate birthdate;

    @Column(name = "COUNTRY", length = 30)
    private String country;

    @Column(name = "EMAIL", length = 50)
    private String email;

    @Column(name = "PERSON_PASSWORD")
    private Long personPassword;

    @Column(name = "POST_AMOUNT")
    private Long postAmount;

    @Column(name = "IMAGE_LINK", length = 500)
    private String imageLink;

    public Person(String name, String fName, String sName, String uName, String country,
            LocalDate birthDate, String email, String password) {
        this.birthdate = birthDate;
        this.country = country;
        this.email = email;
        this.firstName = fName;
        this.secondName = sName;
        this.personPassword = Long.valueOf((long) password.hashCode());
        this.userName = uName;
        this.personName = name;
        this.imageLink = "https://firebasestorage.googleapis.com/v0/b/nature-d45bb.appspot.com/o/imagenes%2Fprofile.jpg?alt=media&token=5d28db5a-9fca-4181-8449-c081827700cd";
        this.postAmount = 0L;
    }



    public Person() {

    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Long getPostAmount() {
        return postAmount;
    }

    public void setPostAmount(Long postAmount) {
        this.postAmount = postAmount;
    }

    public Long getPersonPassword() {
        return personPassword;
    }

    public void setPersonPassword(Long personPassword) {
        this.personPassword = personPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthdate() {
        return "" + this.birthdate.getDayOfMonth() + "-" + this.birthdate.getMonthValue() + "-"
                + this.birthdate.getYear();
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
