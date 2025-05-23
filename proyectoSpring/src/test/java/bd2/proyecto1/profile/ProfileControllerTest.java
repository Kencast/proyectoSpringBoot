package bd2.proyecto1.profile;

import bd2.proyecto1.GlobalAttributes;
import bd2.proyecto1.login.LoginController;
import bd2.proyecto1.login.LoginInfo;
import bd2.proyecto1.signup.SignUpInfo;
import bd2.proyecto1.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileControllerTest {

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private GlobalAttributes globalAttributes;

    @Mock
    private StorageService storageService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Model model;

    private String apiUrl = "http://localhost:8080/";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profileController.apiUrl = apiUrl;
    }

    @Test
    void obtainProfileSuccess() {
        ProfilePerson profile = new ProfilePerson();
        when(restTemplate.getForEntity(
                eq(apiUrl + "user/profile/1"),
                eq(ProfilePerson.class)
        )).thenReturn(ResponseEntity.ok(profile));
        ProfilePerson result = profileController.obtainProfile(1L);
        assertEquals(profile, result);
    }

    @Test
    void obtainProfileFail() {
        when(restTemplate.getForEntity(
                eq(apiUrl + "user/profile/1"),
                eq(ProfilePerson.class)
        )).thenThrow(new RuntimeException());
        assertNull(profileController.obtainProfile(1L));
    }

    @Test
    void obtainFollowingSuccess() {
        List<UserSimple> list = List.of(new UserSimple());
        ParameterizedTypeReference<List<UserSimple>> typeRef = new ParameterizedTypeReference<>() {};
        when(restTemplate.exchange(
                eq(apiUrl + "follow/following/1"),
                eq(HttpMethod.GET),
                isNull(),
                eq(typeRef)
        )).thenReturn(ResponseEntity.ok(list));
        List<UserSimple> result = profileController.obtainFollowing(1L);
        assertEquals(1, result.size());
    }

    @Test
    void obtainFollowingFail() {
        when(restTemplate.exchange(
                anyString(),
                any(),
                isNull(),
                ArgumentMatchers.<ParameterizedTypeReference<List<UserSimple>>>any()
        )).thenThrow(new RuntimeException());

        List<UserSimple> result = profileController.obtainFollowing(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    void isFollowingTrue() {
        when(restTemplate.getForEntity(
                eq(apiUrl + "follow/validate/1/2"),
                eq(Boolean.class)
        )).thenReturn(ResponseEntity.ok(true));
        assertTrue(profileController.isFollowing(1L, 2L));
    }

    @Test
    void isFollowingFalse() {
        when(restTemplate.getForEntity(
                eq(apiUrl + "follow/validate/2/1"),
                eq(Boolean.class)
        )).thenThrow(new RuntimeException());
        assertFalse(profileController.isFollowing(2L, 1L));
    }

    @Test
    void followPersonSuccess() {
        when(restTemplate.getForEntity(
                eq(apiUrl + "follow/create/1/2"),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("ok"));
        assertTrue(profileController.followPerson(1L, 2L));
    }

    @Test
    void followPersonFail() {
        when(restTemplate.getForEntity(
                eq(apiUrl + "follow/create/2/1"),
                eq(String.class)
        )).thenThrow(new RuntimeException());
        assertFalse(profileController.followPerson(2L, 1L));
    }

    @Test
    void unfollowPersonSuccess() {
        when(restTemplate.getForEntity(
                eq(apiUrl + "follow/delete/1/2"),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("ok"));
        assertTrue(profileController.unfollowPerson(1L, 2L));
    }

    @Test
    void unfollowPersonFail() {
        when(restTemplate.getForEntity(
                eq(apiUrl + "follow/delete/2/1"),
                eq(String.class)
        )).thenThrow(new RuntimeException());
        assertFalse(profileController.unfollowPerson(2L, 1L));
    }

    @Test
    void deletePersonSuccess() {
        when(restTemplate.postForEntity(
                eq(apiUrl + "user/delete"),
                eq(1L),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("deleted"));
        assertTrue(profileController.deletePerson(1L));
    }

    @Test
    void deletePersonFail() {
        when(restTemplate.postForEntity(
                eq(apiUrl + "user/delete"),
                eq(1L),
                eq(String.class)
        )).thenThrow(new RuntimeException());
        assertFalse(profileController.deletePerson(1L));
    }

    @Test
    void updatePersonSuccess() {
        SignUpInfo info = new SignUpInfo();
        doNothing().when(restTemplate).put(
                eq(apiUrl + "user/update/1"),
                eq(info));
        assertTrue(profileController.updatePerson(info, 1L));
    }

    @Test
    void updatePersonFail() {
        SignUpInfo info = new SignUpInfo();
        doThrow(new RuntimeException()).when(restTemplate).put(
                eq(apiUrl + "user/update/1"),
                eq(info));
        assertFalse(profileController.updatePerson(info, 1L));
    }

    @Test
    void redirectWhenLogOut() {
        String result = profileController.logout(model);
        verify(globalAttributes).setUserId(0L);
        assertEquals("redirect:/login", result);
    }

    @Test
    void redirectsToLoginIfUserIdIsZero() {
        when(model.getAttribute("userId")).thenReturn(0L);
        String result = profileController.profile(model, 1L);
        assertEquals("redirect:/login", result);
    }

    @Test
    void profileAddsErrorIfProfileIsNull() {
        ProfileController controllerSpy = Mockito.spy(profileController);
        when(model.getAttribute("userId")).thenReturn(1L);
        doReturn(null).when(controllerSpy).obtainProfile(1L);
        String result = controllerSpy.profile(model, 1L);
        verify(model).addAttribute("error", true);
        assertEquals("Profile", result);
    }

    @Test
    void profileNoAddsErrorIfProfileIsValid() {
        ProfileController controllerSpy = Mockito.spy(profileController);
        ProfilePerson profilePerson = new ProfilePerson();
        when(model.getAttribute("userId")).thenReturn(1L);
        doReturn(profilePerson).when(controllerSpy).obtainProfile(1L);
        String result = controllerSpy.profile(model, 1L);
        verify(model).addAttribute("error", false);
        assertEquals("Profile", result);
    }

    @Test
    void profileFollowSuccessRedirects() {
        when(model.getAttribute("userId")).thenReturn(1L);
        when(restTemplate.getForEntity(
                eq(apiUrl + "follow/create/1/2"),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("ok"));
        String result = profileController.profileFollow(2L, model);
        assertEquals("redirect:/profile/2", result);
    }

    @Test
    void profileUnFollowSuccessRedirects() {
        when(model.getAttribute("userId")).thenReturn(1L);
        when(restTemplate.getForEntity(
                eq(apiUrl + "unfollow/2"),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("ok"));
        String result = profileController.profileUnFollow(2L, model);
        assertEquals("redirect:/profile/2", result);
    }

    @Test
    void redirectsIfProfileIsNull() {
        when(model.getAttribute("error")).thenReturn(false);
        when(model.getAttribute("userId")).thenReturn(1L);
        ProfileController controllerSpy = Mockito.spy(profileController);
        doReturn(null).when(controllerSpy).obtainProfile(1L);
        String result = controllerSpy.changeProfile(model);
        assertEquals("redirect:/home", result);
    }

    @Test
    void testChangeProfile_ProfileExists_ReturnsChangeProfileView() {
        ProfilePerson profile = new ProfilePerson();
        profile.setEmail("email@test.com");
        profile.setCountry("CR");
        profile.setUserName("abc");
        profile.setSecondName("Pereira");
        profile.setFirstName("Godinez");
        profile.setName("Hugo");
        profile.setProfileUrl("url.com");

        when(model.getAttribute("userId")).thenReturn(1L);

        ProfileController controllerSpy = Mockito.spy(profileController);
        doReturn(profile).when(controllerSpy).obtainProfile(1L);

        String viewName = controllerSpy.changeProfile(model);

        verify(model).addAttribute("error", false);
        verify(model).addAttribute(eq("change"), any(SignUpInfo.class));
        assertEquals("ChangeProfile", viewName);
    }


    @Test
    void keepInChangeProfilePageWhenHasErrorsIsTrue() {
        SignUpInfo signUpInfo = new SignUpInfo();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = profileController.changeProfile(model, signUpInfo, bindingResult);
        assertEquals("ChangeProfile", result);
    }

    @Test
    void changeProfilePostWithImageUploadFails() throws IOException, InterruptedException {
        SignUpInfo signUpInfo = mock(SignUpInfo.class);
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(signUpInfo.getProfilePicture()).thenReturn(multipartFile);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(model.getAttribute("userId")).thenReturn(1L);
        doThrow(new RuntimeException()).when(storageService).uploadImage(any(), eq("user1"));
        BindingResult bindingsResults = mock(BindingResult.class);
        when(bindingsResults.hasErrors()).thenReturn(false);
        String result = profileController.changeProfile(model, signUpInfo, bindingsResults);
        verify(model).addAttribute("error", true);
        assertEquals("ChangeProfile", result);
    }

    @Test
    void changeProfilePostSuccessRedirects() throws IOException, InterruptedException {
        SignUpInfo signUpInfo = mock(SignUpInfo.class);
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(signUpInfo.getProfilePicture()).thenReturn(multipartFile);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(model.getAttribute("userId")).thenReturn(1L);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(storageService.uploadImage(any(),eq("user1"))).thenReturn("https://cdn.discordapp.com/attachments/1220481283268808724/1374861435552792636/th.png?ex=6831913d&is=68303fbd&hm=338d810c4abf23508b7677cece913ec3baf3995a324d8f9bb87e289ec558293f&");
        String result = profileController.changeProfile(model, signUpInfo, bindingResult);
        assertEquals("redirect:/profile/1", result);
    }

    @Test
    void deleteProfileSuccess() {
        when(model.getAttribute("userId")).thenReturn(1L);
        ProfileController spyController = Mockito.spy(profileController);
        doReturn(true).when(spyController).deletePerson(1L);
        String result = spyController.deleteProfile(model);
        verify(globalAttributes).setUserId(0L);
        assertEquals("redirect:/login", result);
    }

    @Test
    void deleteProfileFail() {
        when(model.getAttribute("userId")).thenReturn(1L);
        ProfileController spyProfileController = Mockito.spy(ProfileController.class);
        doReturn(false).when(spyProfileController).deletePerson(any(Long.class));
        String result = spyProfileController.deleteProfile(model);
        assertEquals("redirect:/profile/1", result);
    }
}
