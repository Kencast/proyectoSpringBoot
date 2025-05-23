package bd2.proyecto1.channels;

import bd2.proyecto1.GlobalAttributes;
import bd2.proyecto1.home.PostInfoDTO;
import bd2.proyecto1.home.PostTag;
import bd2.proyecto1.home.PostViewDTO;
import bd2.proyecto1.home.TagPopularityDTO;
import com.google.shopping.type.Channel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ChannelControllerTest {
    @InjectMocks
    private ChannelController channelController;

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
        channelController.apiUrl = apiUrl;
    }

    @Test
    void obtainTrendingTagsSuccess() {
        List<TagPopularityDTO> list = List.of(new TagPopularityDTO("C++", 100));
        ParameterizedTypeReference<List<TagPopularityDTO>> typeRef = new ParameterizedTypeReference<>() {
        };
        when(restTemplate.exchange(
                eq(apiUrl + "tag/trendingTagsChannel"),
                eq(HttpMethod.GET),
                isNull(),
                eq(typeRef)
        )).thenReturn(ResponseEntity.ok(list));
        List<TagPopularityDTO> result = channelController.obtainTrenTags();
        assertFalse(result.isEmpty());
    }

    @Test
    void obtainTrendingTagsFail() {
        ParameterizedTypeReference<List<TagPopularityDTO>> typeRef = new ParameterizedTypeReference<>() {
        };
        when(restTemplate.exchange(
                eq(apiUrl + "tag/trendingTagsChannel"),
                eq(HttpMethod.GET),
                isNull(),
                eq(typeRef)
        )).thenThrow(new RuntimeException());
        List<TagPopularityDTO> result = channelController.obtainTrenTags();
        assertTrue(result.isEmpty());
    }


    @Test
    void obtainChannelsSuccess() {
        List<PostInfoDTO> list = List.of(new PostInfoDTO(1L, "Hola", 1L,
                "Kener", "https://cdn.discordapp.com/attachments/1220481283268808724/1374861435552792636/th.png?ex=682f96fd&is=682e457d&hm=31f7f4954635514f4a37751808dcb4548f3a49539bb1888df714bc27433a7f70&",
                0L));
        ParameterizedTypeReference<List<PostInfoDTO>> typeRef = new ParameterizedTypeReference<>() {
        };
        when(restTemplate.exchange(
                eq(apiUrl + "channel/getChannelsInfo"),
                eq(HttpMethod.GET),
                isNull(),
                eq(typeRef)
        )).thenReturn(ResponseEntity.ok(list));
        List<PostInfoDTO> result = channelController.obtainChannels();
        assertFalse(result.isEmpty());
    }

    @Test
    void obtainChannelsFail() {
        ParameterizedTypeReference<List<PostInfoDTO>> typeRef = new ParameterizedTypeReference<>() {
        };
        when(restTemplate.exchange(
                eq(apiUrl + "channel/getChannelsInfo"),
                eq(HttpMethod.GET),
                isNull(),
                eq(typeRef)
        )).thenThrow(new RuntimeException());
        List<PostInfoDTO> result = channelController.obtainChannels();
        assertTrue(result.isEmpty());
    }

    @Test
    void obtainChannelByIdSuccess() {
        List<PostInfoDTO> list = List.of(new PostInfoDTO(1234L, "MY INCREDIBLE POST", 598L, "ByLuco",
                "https://cdn.discordapp.com/attachments/1220481283268808724/1374861435552792636/th.png?ex=682f96fd&is=682e457d&hm=31f7f4954635514f4a37751808dcb4548f3a49539bb1888df714bc27433a7f70&",
                41L));
        ParameterizedTypeReference<List<PostInfoDTO>> typeRef = new ParameterizedTypeReference<>() {
        };
        when(restTemplate.exchange(
                eq(apiUrl + "channel/getChannelsInfo/598"),
                eq(HttpMethod.GET),
                isNull(),
                eq(typeRef)
        )).thenReturn(ResponseEntity.ok(list));
        List<PostInfoDTO> result = channelController.obtainChannelsById(598L);
        assertFalse(result.isEmpty());
    }

    @Test
    void obtainChannelByIdFail() {
        ParameterizedTypeReference<List<PostInfoDTO>> typeRef = new ParameterizedTypeReference<>() {
        };
        when(restTemplate.exchange(
                eq(apiUrl + "channel/getChannelsInfo/598"),
                eq(HttpMethod.GET),
                isNull(),
                eq(typeRef)
        )).thenThrow(new RuntimeException());
        List<PostInfoDTO> result = channelController.obtainChannelsById(598L);
        assertTrue(result.isEmpty());
    }

    @Test
    void insertChannelSucces() {
        PostTag postTag = new PostTag();
        postTag.setId(874593L);
        postTag.setTitle("Hello Everyone");
        postTag.setDescription("THIS IS AWSOME");
        postTag.setTags("C PYTHON");
        postTag.setPersonId(1L);
        postTag.setListTags(List.of("C", "Python"));
        when(restTemplate.postForEntity(eq(apiUrl + "/channel/create"),
                eq(PostTag.class), eq(String.class))).thenReturn(ResponseEntity.ok("ok"));
        Boolean response = channelController.insertarChannel(postTag);
        assertTrue(response);
    }

    @Test
    void insertChannelFail() {
        PostTag postTag = new PostTag();
        when(restTemplate.postForEntity(
                eq(apiUrl + "/channel/create"),
                eq(postTag),
                eq(String.class)
        )).thenThrow(new RuntimeException());
        Boolean response = channelController.insertarChannel(postTag);
        assertFalse(response);
    }

    @Test
    void obtainChannelViewSuccess() {
        PostViewDTO chan = new PostViewDTO(501L, "Hola", "channel", "05/04/2025",
                1L, "Pablo", "none");
        when(restTemplate.getForEntity(
                eq(apiUrl + "/channel/getChannelView/501"),
                eq(PostViewDTO.class))).thenReturn(ResponseEntity.ok(chan));
        PostViewDTO result = channelController.obtainChannelView(501L);
        assertNotNull(result);
    }

    @Test
    void obtainChannelViewFail() {
        when(restTemplate.getForEntity(
                eq(apiUrl + "/channel/getChannelView/501"),
                eq(PostViewDTO.class))).thenThrow(new RuntimeException());
        PostViewDTO result = channelController.obtainChannelView(501L);
        assertNull(result);
    }

    @Test
    void obtainMaterialSuccess() {
        List<Material> list = List.of(new Material());
        ParameterizedTypeReference<List<Material>> typeRef = new ParameterizedTypeReference<>() {
        };
        when(restTemplate.exchange(
                eq(apiUrl + "material/get/1"),
                eq(HttpMethod.GET),
                isNull(),
                eq(typeRef)
        )).thenReturn(ResponseEntity.ok(list));
        List<Material> result = channelController.obtainMaterial(1L);
        assertFalse(result.isEmpty());
    }

    @Test
    void obtainMaterialFail() {
        ParameterizedTypeReference<List<Material>> typeRef = new ParameterizedTypeReference<>() {
        };
        when(restTemplate.exchange(
                eq(apiUrl + "material/get/1"),
                eq(HttpMethod.GET),
                isNull(),
                eq(typeRef)
        )).thenThrow(new RuntimeException());
        List<Material> result = channelController.obtainMaterial(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    void insertMaterialSucces() {
        Material material = new Material();
        material.setId(501L);
        material.setTitle("Hola");
        material.setDescription("THIS IS AWSOME");
        material.setChannelId(1L);
        when(restTemplate.postForEntity(eq(apiUrl + "material/create"), eq(Material.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok("ok"));
        Boolean result = channelController.insertarMaterial(material);
        assertTrue(result);
    }

    @Test
    void insertMaterialFail() {
        Material material = new Material();
        when(restTemplate.postForEntity(
                eq(apiUrl + "material/create"),
                eq(material),
                eq(String.class)
        )).thenThrow(new RuntimeException());
        Boolean response = channelController.insertarMaterial(material);
        assertFalse(response);
    }

    @Test
    void deleteChannelByIdSuccess() {
        when(restTemplate.getForEntity(eq(apiUrl + "channel/delete/" + 55), eq(String.class))).thenReturn(ResponseEntity.ok("ok"));
        Boolean response = channelController.deleteChannelById(55L);
        assertTrue(response);
    }

    @Test
    void deleteChannelByIdFail() {
        when(restTemplate.getForEntity(eq(apiUrl + "channel/delete/" + 55), eq(String.class))).thenThrow(new RuntimeException());
        Boolean response = channelController.deleteChannelById(55L);
        assertFalse(response);
    }

    @Test
    void returnHomeGetChannels() {
        List<TagPopularityDTO> listTags = List.of(new TagPopularityDTO("JUPYTER", 90));
        List<PostInfoDTO> listPost = List.of(new PostInfoDTO(1L, "THE best Title", 1L, "BYLUCO",
                "https://cdn.discordapp.com/attachments/1220481283268808724/1374861435552792636/th.png?ex=682f96fd&is=682e457d&hm=31f7f4954635514f4a37751808dcb4548f3a49539bb1888df714bc27433a7f70&",
                123L));
        ChannelController spyController = Mockito.spy(ChannelController.class);
        doReturn(listTags).when(spyController).obtainTrenTags();
        doReturn(listPost).when(spyController).obtainChannels();
        String view = spyController.getChannels(model);
        verify(model).addAttribute(eq("tren"), eq(listTags));
        verify(model).addAttribute("titular", "Channels");
        verify(model).addAttribute("btoNew", "New Channel");
        verify(model).addAttribute("numRes", "Materials: ");
        verify(model).addAttribute("dirView", "/channels/channel/");
        verify(model).addAttribute(eq("posts"), eq(listPost));
        verify(model).addAttribute("rutaCre", "/channels/create");
        assertEquals("Home", view);
    }

    @Test
    void returnHomeGetChannelsById() {
        List<TagPopularityDTO> listTags = List.of(new TagPopularityDTO("JUPYTER", 90));
        List<PostInfoDTO> listPost = List.of(new PostInfoDTO(1L, "THE best Title", 1L, "BYLUCO",
                "https://cdn.discordapp.com/attachments/1220481283268808724/1374861435552792636/th.png?ex=682f96fd&is=682e457d&hm=31f7f4954635514f4a37751808dcb4548f3a49539bb1888df714bc27433a7f70&",
                123L));
        ChannelController spyController = Mockito.spy(ChannelController.class);
        doReturn(listTags).when(spyController).obtainTrenTags();
        doReturn(listPost).when(spyController).obtainChannelsById(eq(1L));
        String view = spyController.getChannelById(1L, model);
        verify(model).addAttribute(eq("tren"), eq(listTags));
        verify(model).addAttribute("titular", "New Channel");
        verify(model).addAttribute("btoNew", "New Channel");
        verify(model).addAttribute("numRes", "Materials: ");
        verify(model).addAttribute("dirView", "/channels/channel/");
        verify(model).addAttribute(eq("posts"), eq(listPost));
        verify(model).addAttribute("rutaCre", "/channels/create");
        assertEquals("Home", view);
    }

    @Test
    void returnCreateViewCreateChannel() {
        when(model.getAttribute("userId")).thenReturn(1L);
        String view = channelController.createChannel(model);
        PostTag postTag = new PostTag();
        verify(model).addAttribute("error", false);
        verify(model).addAttribute(eq("newPost"), any(PostTag.class));
        verify(model).addAttribute("rutaAgre", "/channels/add");
        verify(model).addAttribute("titular", "New Channel");
        assertEquals("CreatePost", view);
    }

    @Test
    void returnLoginViewCreateChannel() {
        when(model.getAttribute("userId")).thenReturn(0L);
        String view = channelController.createChannel(model);
        assertEquals("redirect:/login", view);
    }

    @Test
    void returnCreatePostIfErrorAddChannel() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        String view = channelController.addChannel(new PostTag(), bindingResult, model);
        assertEquals("CreatePost", view);
    }

    @Test
    void redirectChannelsAddChannel() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        PostTag postTag = new PostTag();
        postTag.setListTags(List.of("DP", "Smalll to large", "Interactive", "Binary Search"));
        ChannelController spyController = Mockito.spy(ChannelController.class);
        when(model.getAttribute("userId")).thenReturn(1L);
        doReturn(true).when(spyController).insertarChannel(any(PostTag.class));
        String view = spyController.addChannel(postTag, bindingResult, model);
        assertEquals("redirect:/channels", view);
    }


    @Test
    void returnCreatePostIfNotInsertAddChannel() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        PostTag postTag = new PostTag();
        postTag.setListTags(List.of("DP", "Smalll to large", "Interactive", "Binary Search"));
        ChannelController spyController = Mockito.spy(ChannelController.class);
        when(model.getAttribute("userId")).thenReturn(1L);
        doReturn(false).when(spyController).insertarChannel(any(PostTag.class));
        String view = spyController.addChannel(postTag, bindingResult, model);
        verify(model).addAttribute("error", true);
        assertEquals("CreatePost", view);
    }

    // Si se ocupa se puede hacer más del anterior

    @Test
    void returnChannelViewViewChannel() {
        ChannelController spyController = Mockito.spy(ChannelController.class);
        List<Material> lista = List.of(new Material(2L, "AWSOME", "WE ARE PEOPLE", 1L),
                new Material(3L, "Great world", "How to print hello world", 1L),
                new Material(4L, "GO TO WORK", "THIS IS QA", 1L));
        doReturn(lista).when(spyController).obtainMaterial(1L);
        doReturn(new PostViewDTO(1L, "Hola mundo", "saludos", "05/04/2005", 1L,
                "Kener", "NA")).when(spyController).obtainChannelView(1L);
        String view = spyController.viewChannel(1L, model);
        verify(model).addAttribute(eq("channelId"), eq(1L));
        verify(model).addAttribute(eq("newMaterial"), any(Material.class));
        verify(model).addAttribute("errorMaterial", false);
        verify(model).addAttribute(eq("materials"), eq(lista));
        verify(model).addAttribute(eq("channel"), any(PostViewDTO.class));
        assertEquals("ChannelView", view);
    }

    @Test
    void returnChannelViewIfErrorAddMaterial() {
        BindingResult bindingResult = mock(BindingResult.class);
        Material materialParam = new Material(1L, "Greedy algorithms", "Master the greedy problems", 1L);
        when(bindingResult.hasErrors()).thenReturn(true);
        ChannelController spyController = Mockito.spy(ChannelController.class);
        List<Material> lista = List.of(new Material(2L, "AWSOME", "WE ARE PEOPLE", 1L),
                new Material(3L, "Great world", "How to print hello world", 1L),
                new Material(4L, "GO TO WORK", "THIS IS QA", 1L));
        doReturn(lista).when(spyController).obtainMaterial(1L);
        doReturn(new PostViewDTO(1L, "Hola mundo", "saludos", "05/04/2005", 1L,
                "Kener", "NA")).when(spyController).obtainChannelView(1L);
        String view = spyController.addMaterial(materialParam, bindingResult, model, 1L);
        verify(model).addAttribute(eq("channelId"), eq(1L));
        verify(model).addAttribute(eq("newMaterial"), any(Material.class));
        verify(model).addAttribute("errorMaterial", false);
        verify(model).addAttribute(eq("materials"), eq(lista));
        verify(model).addAttribute(eq("channel"), any(PostViewDTO.class));
        assertEquals("ChannelView", view);
    }

    @Test
    void returnChannelViewIfNotInsertAddMaterial() {
        BindingResult bindingResult = mock(BindingResult.class);
        Material materialParam = new Material(1L, "Graph algorithms", "Master the graph problems", 1L);
        when(bindingResult.hasErrors()).thenReturn(false);
        ChannelController spyController = Mockito.spy(ChannelController.class);
        List<Material> lista = List.of(new Material(2L, "AWSOME", "WE ARE PEOPLE", 1L),
                new Material(3L, "Great world", "How to print hello world", 1L),
                new Material(4L, "GO TO WORK", "THIS IS QA", 1L));
        doReturn(lista).when(spyController).obtainMaterial(1L);
        doReturn(new PostViewDTO(1L, "Hola mundo", "saludos", "05/04/2005", 1L,
                "Kener", "NA")).when(spyController).obtainChannelView(1L);
        doReturn(false).when(spyController).insertarMaterial(any(Material.class));
        String view = spyController.addMaterial(materialParam, bindingResult, model, 1L);
        verify(model).addAttribute(eq("channelId"), eq(1L));
        verify(model).addAttribute(eq("newMaterial"), any(Material.class));
        verify(model).addAttribute("errorMaterial", true);
        verify(model).addAttribute(eq("materials"), eq(lista));
        verify(model).addAttribute(eq("channel"), any(PostViewDTO.class));
        assertEquals("ChannelView", view);
    }

    @Test
    void redirectChannelViewAddMaterial() {
        BindingResult bindingResult = mock(BindingResult.class);
        Material materialParam = new Material(1L, "Graph algorithms", "Master the graph problems", 1L);
        when(bindingResult.hasErrors()).thenReturn(false);
        ChannelController spyController = Mockito.spy(ChannelController.class);
        doReturn(true).when(spyController).insertarMaterial(any(Material.class));
        String view = spyController.addMaterial(materialParam, bindingResult, model, 1L);
        assertEquals("redirect:/channels/channel/" + 1, view);
    }

    @Test
    void redirectChannelViewDeleteChannel(){
        ChannelController spyController = Mockito.spy(ChannelController.class);
        doReturn(false).when(spyController).deleteChannelById(any(Long.class));
        String view = spyController.deleteChannel(1L);
        assertEquals("redirect:/channels/channel/" + 1, view);
    }

    @Test
    void redirectChannelsDeleteChannel(){
        ChannelController spyController = Mockito.spy(ChannelController.class);
        doReturn(true).when(spyController).deleteChannelById(any(Long.class));
        String view = spyController.deleteChannel(1L);
        assertEquals("redirect:/channels", view);
    }
}
