package com.example.demo.person;

import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.follow.FollowService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user")
public class PersonController {
    @Autowired
    private PersonService personService;
    @Autowired
    private FollowService followService;

    @GetMapping("/get")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<ProfileInfo> getUserById(@PathVariable Long id) {
        try {
            Person user = personService.getUserById(id);
            Long totalFollowing = followService.getTotalFollowing(id);
            Long totalFollowers = followService.getTotalFollowers(id);
            ProfileInfo profile = new ProfileInfo(user.getPersonName(), user.getFirstName(),
                    user.getSecondName(), user.getUserName(), user.getCountry(), user.getBirthdate(), user.getEmail(),
                    totalFollowers, user.getPostAmount(), totalFollowing, user.getImageLink());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(profile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createPerson(@RequestBody RegisterInfo registerInfo) {
        try {
            Person person = new Person(registerInfo.getPersonName(), registerInfo.getFirstName(),
                    registerInfo.getSecondName(), registerInfo.getUserName(), registerInfo.getCountry(),
                    registerInfo.getBirthdate(), registerInfo.getEmail(), registerInfo.getPersonPassword());
            personService.insertPerson(person);
            LoginInfo login = new LoginInfo(registerInfo.getEmail(), registerInfo.getPersonPassword());
            return validateUser(login);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(1L);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Long> validateUser(@RequestBody LoginInfo loginInfo) {
        try {
            Long id = personService.validatePerson(loginInfo);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(1L);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deletePerson(@RequestBody Long id){
        try{
            personService.deletePerson(id);
            System.out.println(id);
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se cashó: "+e);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePerson(@PathVariable Long id, @RequestBody RegisterInfo registerInfo) {
        try{
            System.out.println("Update: " + id);
            Person person = new Person(registerInfo.getPersonName(), registerInfo.getFirstName(), registerInfo.getSecondName(),
                    registerInfo.getUserName(), registerInfo.getCountry(), null, registerInfo.getEmail(), registerInfo.getPersonPassword());
            person.setId(id);
            person.setImageLink(registerInfo.getImageLink());
            System.out.println(person);
            personService.updatePerson(person);
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail: "+e.getMessage());
        }
    }

}
