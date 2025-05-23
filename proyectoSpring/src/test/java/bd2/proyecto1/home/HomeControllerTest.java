package bd2.proyecto1.home;

import bd2.proyecto1.GlobalAttributes;
import bd2.proyecto1.channels.ChannelController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HomeControllerTest {
    @InjectMocks
    private HomeController homeController;

    @Mock
    private GlobalAttributes globalAttributes;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Model model;

    private String apiUrl = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        homeController.apiUrl = apiUrl;
    }

    @Test
    void insertPostSuccesfull(){
        PostTag postTag = new PostTag();
        postTag.setId(1L);
        postTag.setTitle("GREAT TEST");
        postTag.setDescription("TEST");
        postTag.setPersonId(1L);
        when(restTemplate.postForEntity(eq(apiUrl+"post/create"),eq(PostTag.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok("ok"));
        Boolean answer = homeController.insertarPost(postTag);
        assertTrue(answer);
    }

    @Test
    void insertPostFail(){
        PostTag postTag = new PostTag();
        postTag.setId(1L);
        postTag.setTitle("GREAT TEST");
        postTag.setDescription("TEST");
        postTag.setPersonId(1L);
        when(restTemplate.postForEntity(
                eq(apiUrl+"post/create"),
                any(PostTag.class),
                eq(String.class)
        )).thenThrow(new RuntimeException());
        Boolean answer = homeController.insertarPost(postTag);
        assertFalse(answer);
    }

    @Test
    void deletePostSucces() {
        when(restTemplate.getForEntity(
                eq(apiUrl+"post/delete/1"),
                eq(String.class)
        )) .thenReturn(ResponseEntity.ok("deleted"));
        assertTrue(homeController.deletePost(1L));
    }


    @Test
    void createPostSucces() {
        when(model.getAttribute("userId")).thenReturn(1L);
        String view=homeController.createPost(model);
        verify(model).addAttribute("error", false);
        verify(model).addAttribute(eq("newPost"), any(PostTag.class));
        verify(model).addAttribute("rutaAgre","/home/add");
        verify(model).addAttribute("titular", "New Post");
        assertEquals("CreatePost", view);
    }

}