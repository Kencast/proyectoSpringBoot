package bd2.proyecto1.channels;

import bd2.proyecto1.home.PostInfoDTO;
import bd2.proyecto1.home.PostTag;
import bd2.proyecto1.home.PostViewDTO;
import bd2.proyecto1.home.TagPopularityDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/channels")
public class ChannelController {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${app.urlDatabase}")
    private String apiUrl;

    public List<TagPopularityDTO> obtainTrenTags(){
        try{
            ResponseEntity<List<TagPopularityDTO>> res = restTemplate.exchange(apiUrl+"tag/trendingTagsChannel",
                    HttpMethod.GET,null,
                    new ParameterizedTypeReference<List<TagPopularityDTO>>(){});
            return res.getBody();
        }catch (Exception e){
            System.out.println("error: "+e);
            return new ArrayList<>();
        }
    }

    public List<PostInfoDTO> obtainChannels(){
        try{
            ResponseEntity<List<PostInfoDTO>> res = restTemplate.exchange(apiUrl + "channel/getChannelsInfo",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<PostInfoDTO>>() {
                    });
            return res.getBody();
        }catch (Exception e){
            System.out.println("error: "+e);
            return new ArrayList<>();
        }
    }

    public List<PostInfoDTO> obtainChannelsById(Long id){
        try{
            ResponseEntity<List<PostInfoDTO>> res = restTemplate.exchange(apiUrl +
                            "channel/getChannelsInfo/" + id, HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<PostInfoDTO>>() {
                    });
            return res.getBody();
        }catch (Exception e){
            System.out.println("error: "+e.getMessage());
            return new ArrayList<>();
        }
    }

    public Boolean insertarChannel(PostTag channel){
        try{
            ResponseEntity<String> res = restTemplate.postForEntity(apiUrl+"/channel/create", channel, String.class);
            return true;
        }catch (Exception e){
            System.out.println("error: "+e);
            return false;
        }
    }

    public PostViewDTO obtainChannelView(Long id){
        try{
            ResponseEntity<PostViewDTO> res = restTemplate.getForEntity(apiUrl+"/channel/getChannelView/"+id,
                    PostViewDTO.class);
            return res.getBody();
        } catch (Exception e) {
            System.out.println("error: "+ e);
            return null;
        }
    }

    public List<Material> obtainMaterial(Long id){
        try{
            ResponseEntity<List<Material>> res = restTemplate.exchange(apiUrl + "material/get/"+id,
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Material>>() {});
            return res.getBody();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Boolean insertarMaterial(Material material){
        try{
            ResponseEntity<String> res = restTemplate.postForEntity(apiUrl+"material/create", material, String.class);
            return true;
        } catch (Exception e) {
            System.out.println("error: "+ e);
            return false;
        }
    }

    public Boolean deleteChannelById(Long id){
        try{
            ResponseEntity<String> res = restTemplate.getForEntity(apiUrl+"channel/delete/"+id, String.class);
            return true;
        } catch (Exception e) {
            System.out.println("error: "+ e);
            return false;
        }
    }

    @GetMapping()
    public String getChannels(Model model){
        List<TagPopularityDTO> tren = obtainTrenTags();
        model.addAttribute("tren",tren);
        model.addAttribute("titular","Channels");
        model.addAttribute("btoNew","New Channel");
        model.addAttribute("numRes","Materials: ");
        model.addAttribute("dirView","/channels/channel/");
        List<PostInfoDTO> channels = obtainChannels();
        model.addAttribute("posts",channels);
        model.addAttribute("rutaCre","/channels/create");
        return "Home";
    }

    @GetMapping("/{id}")
    public String getChannelById(@PathVariable("id") Long id, Model model){
        List<TagPopularityDTO> tren = obtainTrenTags();
        model.addAttribute("tren",tren);
        model.addAttribute("titular","New Channel");
        model.addAttribute("btoNew","New Channel");
        model.addAttribute("numRes","Materials: ");
        model.addAttribute("dirView","/channels/channel/");
        List<PostInfoDTO> channels = obtainChannelsById(id);
        model.addAttribute("posts",channels);
        model.addAttribute("rutaCre","/channels/create");
        return "Home";
    }

    @GetMapping("/create")
    public String createChannel(Model model){
        Long id = (Long) model.getAttribute("userId");
        if(id == 0) return "redirect:/login";
        PostTag postTag=new PostTag();
        model.addAttribute("error", false);
        model.addAttribute("newPost", postTag);

        model.addAttribute("rutaAgre","/channels/add");
        model.addAttribute("titular", "New Channel");
        return "CreatePost";
    }

    @PostMapping("/add")
    public String addChannel(@Valid @ModelAttribute("newPost") PostTag channel, BindingResult bindingResult,
                             Model model){
        if(bindingResult.hasErrors()){
            return "CreatePost";
        }
        String tags="";
        for(String tag : channel.getListTags()){
            tags += tag.toLowerCase()+"-/";
        }
        if(!tags.isEmpty()) tags = tags.substring(0,tags.length()-2);
        channel.setTags(tags);
        channel.setId(0L);
        channel.setPersonId((Long) model.getAttribute("userId"));
        if(insertarChannel(channel)){
            return "redirect:/channels";
        }
        model.addAttribute("error", true);
        return "CreatePost";
    }

    @GetMapping("/channel/{id}")
    public String viewChannel(@PathVariable("id") Long id, Model model){
        model.addAttribute("channelId",id);
        model.addAttribute("newMaterial",new Material());
        model.addAttribute("errorMaterial",false);
        List<Material> materials = obtainMaterial(id);
        System.out.println(materials.size());
        model.addAttribute("materials",materials);
        PostViewDTO channel = obtainChannelView(id);
        model.addAttribute("channel",channel);
        return "ChannelView";
    }

    @PostMapping("/addMaterial")
    public String addMaterial(@Valid @ModelAttribute("newMaterial") Material material,
                              BindingResult bindingResult, Model model,
                              @RequestParam("channelId") Long id ){
        if(bindingResult.hasErrors()){
            model.addAttribute("channelId",id);
            model.addAttribute("newMaterial",material);
            model.addAttribute("errorMaterial",false);
            List<Material> materials = obtainMaterial(id);
            model.addAttribute("materials",materials);
            PostViewDTO channel = obtainChannelView(id);
            model.addAttribute("channel",channel);
            return "ChannelView";
        }
        System.out.println(id);
        material.setChannelId(id);
        if(!insertarMaterial(material)){
            model.addAttribute("channelId",id);
            model.addAttribute("newMaterial",material);
            model.addAttribute("errorMaterial",true);
            List<Material> materials = obtainMaterial(id);
            model.addAttribute("materials",materials);
            PostViewDTO channel = obtainChannelView(id);
            model.addAttribute("channel",channel);
            return "ChannelView";
        }
        return "redirect:/channels/channel/"+id;
    }

    @PostMapping("/delete")
    public String deleteChannel(@RequestParam("channelId") Long channelId){
        if(!deleteChannelById(channelId)){
            return "redirect:/channels/channel/"+channelId;
        }
        return "redirect:/channels";
    }
}
