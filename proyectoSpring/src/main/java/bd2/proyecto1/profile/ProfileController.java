package bd2.proyecto1.profile;

import bd2.proyecto1.GlobalAttributes;
import bd2.proyecto1.signup.SignUpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import bd2.proyecto1.storage.StorageService;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private GlobalAttributes globalAttributes;
    @Autowired
    private StorageService storageService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${app.urlDatabase}")
    String apiUrl;

    public ProfilePerson obtainProfile(Long id) {
        try {
            ResponseEntity<ProfilePerson> res = restTemplate.getForEntity(apiUrl + "user/profile/" + id,
                    ProfilePerson.class);
            return res.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public List<UserSimple> obtainFollowing(Long id) {
        try {
            ResponseEntity<List<UserSimple>> res = restTemplate.exchange(
                    apiUrl + "follow/following/" + id, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<UserSimple>>() {
                    });
            return res.getBody();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Boolean isFollowing(Long userId, Long profileId) {
        try {
            ResponseEntity<Boolean> res =
                    restTemplate.getForEntity(apiUrl + "follow/validate/" + userId + "/" + profileId,
                    Boolean.class);
            return res.getBody();
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean followPerson(Long userId, Long profileId) {
        try{
            ResponseEntity<String> res = restTemplate.getForEntity(apiUrl+"follow/create/"+userId+"/"+profileId,
                    String.class);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Boolean unfollowPerson(Long userId, Long profileId){
        try{
            ResponseEntity<String> res = restTemplate.getForEntity(apiUrl+"follow/delete/"+userId+"/"+profileId,
                    String.class);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Boolean deletePerson(Long id){
        try{
            ResponseEntity<String> res = restTemplate.postForEntity(apiUrl+"user/delete",id,String.class);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Boolean updatePerson(SignUpInfo signUpInfo, Long id){
        try{
            System.out.println("Trying to update");
           restTemplate.put(apiUrl+"user/update/"+id, signUpInfo);
           return true;
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return false;
        }
    }

    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable Long id) {
        Long userId = (Long) model.getAttribute("userId");
        if(userId == 0){
            return "redirect:/login";
        }
        model.addAttribute("profileId", id);
        List<UserSimple> users = obtainFollowing(id);
        model.addAttribute("users", users);
        ProfilePerson profile = obtainProfile(id);
        if (profile == null) {
            model.addAttribute("error", true);
        } else
            model.addAttribute("error", false);
        model.addAttribute("profile", profile);
        model.addAttribute("isFollowing", isFollowing(userId, id));
        return "Profile";
    }

    @PostMapping("/follow")
    public String profileFollow(@RequestParam("personId") Long profileId, Model model) {
        if(followPerson((Long) model.getAttribute("userId"), profileId)){
            System.out.println("ok");
        }
        else{
            System.out.println("fail");
        }
        return "redirect:/profile/"+profileId;
    }

    @PostMapping("/unfollow")
    public String profileUnFollow(@RequestParam("personId") Long profileId,Model model) {
        if(unfollowPerson((Long) model.getAttribute("userId"), profileId)){
            System.out.println("ok");
        }
        else{
            System.out.println("fail");
        }
        return "redirect:/profile/"+profileId;
    }

    @GetMapping("/change")
    public String changeProfile(Model model) {
        model.addAttribute("error", false);
        ProfilePerson profile = obtainProfile((Long) model.getAttribute("userId"));
        if(profile==null) return "redirect:/home";
        SignUpInfo signUpInfo = new SignUpInfo(profile.getEmail(),
                profile.getCountry(),profile.getUserName(),profile.getSecondName(),profile.getFirstName(),
                profile.getName(), profile.getProfileUrl());
        model.addAttribute("change", signUpInfo);
        return "ChangeProfile";
    }

    @PostMapping("/change")
    public String changeProfile(Model model, @Valid @ModelAttribute("change") SignUpInfo signUpInfo,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println("Error: " + error.getDefaultMessage());
            });
            return "ChangeProfile";
        }
        if(!signUpInfo.getProfilePicture().isEmpty()){
            try{
                String url=storageService.uploadImage(signUpInfo.getProfilePicture(), "user"+model.getAttribute("userId"));
                signUpInfo.setImageLink(url);
            }
            catch(Exception e){
                model.addAttribute("error",true);
                return "ChangeProfile";
            }
        }
        if(updatePerson(signUpInfo,(Long) model.getAttribute("userId"))){
            System.out.println("success");
        }
        else {
            System.out.println("fail");
        }
        return "redirect:/profile/" + model.getAttribute("userId");
    }

    @GetMapping("/delete")
    public String deleteProfile(Model model) {
        if(deletePerson((Long) model.getAttribute("userId"))){
            System.out.println("nice");
            globalAttributes.setUserId(0L);
            return "redirect:/login";
        }else {
            System.out.println("fail");
            return "redirect:/profile/"+model.getAttribute("userId");
        }
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        globalAttributes.setUserId(0L);
        return "redirect:/login";
    }
}
