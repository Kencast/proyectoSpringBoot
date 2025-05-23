package bd2.proyecto1.home;

import bd2.proyecto1.GlobalAttributes;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${app.urlDatabase}")
    public String apiUrl;

    public List<PostInfoDTO> obtainPosts(){
        try{
            ResponseEntity<List<PostInfoDTO>> response = restTemplate.exchange(apiUrl+"post/getPostInfo",
                    HttpMethod.GET,null,new ParameterizedTypeReference<List<PostInfoDTO>>(){});
            return response.getBody();
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public List<PostInfoDTO> obtainPostsById(Long id){
        try{
            ResponseEntity<List<PostInfoDTO>> response = restTemplate.exchange(apiUrl+"post/postsById/"+id,
                    HttpMethod.GET,null,new ParameterizedTypeReference<List<PostInfoDTO>>(){});
            return response.getBody();
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public List<TagPopularityDTO> obtainTrenTags(){
        try{
            ResponseEntity<List<TagPopularityDTO>> res = restTemplate.exchange(apiUrl+"tag/getTrending",
                    HttpMethod.GET,null,
                    new ParameterizedTypeReference<List<TagPopularityDTO>>(){});
            return res.getBody();
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public List<TagPopularityDTO> obtainTagsById(Long id){
        try{
            ResponseEntity<List<TagPopularityDTO>> res = restTemplate.exchange(apiUrl + "/tag/trendingByPost/" + id,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<TagPopularityDTO>>() {
                    });
            return res.getBody();
        }catch (Exception e){
            System.out.println("error: "+e);
            return new ArrayList<>();
        }
    }

    public Boolean insertarPost(PostTag postTag){
        try{
            System.out.println("Im going to call the api");
            ResponseEntity<String> res = restTemplate.postForEntity(apiUrl+"post/create",postTag,
                    String.class);
            return true;
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return false;
        }
    }

    public PostViewDTO obtainPostById(Long id){
        try{
            ResponseEntity<PostViewDTO> res = restTemplate.getForEntity(apiUrl+"post/getPostView/"+id,
                    PostViewDTO.class);
            return res.getBody();
        } catch (Exception e){
            System.out.println("error: "+e.getMessage());
            return null;
        }
    }

    public List<CommentDTO> obtainComments(Long id){
        try{
            ResponseEntity<List<CommentDTO>> res = restTemplate.exchange(apiUrl + "comment/getByPost/" + id,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<CommentDTO>>() {
                    });
            return res.getBody();
        } catch (Exception e){
            return new ArrayList<>();
        }
    }

    public Boolean insertComments(NewComment newComment){
        try{
            ResponseEntity<String> res = restTemplate.postForEntity(apiUrl + "comment/create",
                    newComment,String.class);
            return true;
        }catch (Exception e){
            System.out.println("error: "+e.getMessage());
            return false;
        }
    }

    public Boolean deletePost(Long id){
        try{
            ResponseEntity<String> res = restTemplate.getForEntity(apiUrl+"post/delete/"+id,String.class);
            return true;
        }catch (Exception e){
            System.out.println("error: "+e.getMessage());
            return false;
        }
    }

    public Boolean updatePost(PostViewDTO postViewDTO){
        try{
            restTemplate.put(apiUrl+"post/update",postViewDTO);
            return true;
        }catch (Exception e){
            System.out.println("error: "+e.getMessage());
            return false;
        }
    }

    @GetMapping()
    public String home(Model model, RedirectAttributes redirectAttributes) {
        List<PostInfoDTO> posts = obtainPosts();
        model.addAttribute("posts", posts);
        List<TagPopularityDTO> tren = obtainTrenTags();
        model.addAttribute("tren", tren);
        model.addAttribute("titular","Posts");
        model.addAttribute("btoNew","New Post");
        model.addAttribute("numRes","Answers: ");
        model.addAttribute("dirView","/home/post/");
        model.addAttribute("rutaCre","/home/create");
        return "Home";
    }    
    
    @GetMapping("/{id}")
    public String home(@PathVariable Long id, Model model) {
        Long userId = (Long) model.getAttribute("userId");
        if(userId == 0L) return "redirect:/login";
        List<PostInfoDTO> posts = obtainPostsById(id);
        model.addAttribute("posts",posts);
        List<TagPopularityDTO> tren = obtainTrenTags();
        model.addAttribute("tren",tren);
        model.addAttribute("titular","Posts");
        model.addAttribute("btoNew","New Post");
        model.addAttribute("numRes","Answers: ");
        model.addAttribute("dirView","/home/post/");
        model.addAttribute("rutaCre","/home/create");
        return "Home";
    }

    @GetMapping("/create")
    public String createPost(Model model){
        Long id = (Long) model.getAttribute("userId");
        if(id == 0) return "redirect:/login";
        PostTag postTag=new PostTag();
        model.addAttribute("error", false);
        model.addAttribute("newPost", postTag);

        model.addAttribute("rutaAgre","/home/add");
        model.addAttribute("titular", "New Post");
        return "CreatePost";
    }

    @PostMapping("/add")
    public String createPost(@Valid @ModelAttribute("newPost") PostTag postTag, BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "CreatePost";
        }
        String tags="";
        for(String tag : postTag.getListTags()){
            tags += tag.toLowerCase()+"-/";
        }
        if(!tags.isEmpty()) tags = tags.substring(0,tags.length()-2);
        postTag.setTags(tags);
        postTag.setId(0L);
        postTag.setPersonId((Long) model.getAttribute("userId"));
        if(insertarPost(postTag)){
            return "redirect:/home";
        }
        postTag.setListTags(new ArrayList<>());
        model.addAttribute("error",true);
        return "CreatePost";
    }

    @GetMapping("/tags")
    @ResponseBody
    public List<String> getTagMatches(@RequestParam("query") String query){
        try{
            ResponseEntity<List<String>> response = restTemplate.exchange(apiUrl+"tag/search/"+query,
                    HttpMethod.GET,null,new ParameterizedTypeReference<List<String>>(){});
            return response.getBody();
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        model.addAttribute("error",false);
        PostViewDTO post = obtainPostById(id);
        model.addAttribute("post", post);
        if(post == null) model.addAttribute("error",true);
        List<CommentDTO> comments = obtainComments(id);
        model.addAttribute("comments",comments);
        List<TagPopularityDTO> tren = obtainTagsById(id);
        model.addAttribute("tren",tren);
        model.addAttribute("newComment", new NewComment());
        model.addAttribute("errorComment",false);
        model.addAttribute("postId", id);
        return "PostView";
    }

    @PostMapping("/addComment")
    public String createComment(@Valid @ModelAttribute("newComment") NewComment newComment,
                                BindingResult bindingResult, @RequestParam("postId") Long id, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("error",false);
            PostViewDTO post = obtainPostById(id);
            model.addAttribute("post", post);
            if(post == null) model.addAttribute("error",true);
            List<CommentDTO> comments = obtainComments(id);
            model.addAttribute("comments",comments);
            List<TagPopularityDTO> tren = obtainTagsById(id);
            model.addAttribute("tren",tren);
            model.addAttribute("errorComment",false);
            model.addAttribute("newComment", newComment);
            model.addAttribute("postId", id);
            return "PostView";
        }
        newComment.setPerson_id((Long) model.getAttribute("userId"));
        newComment.setId(0L);
        newComment.setPost_id(id);
        if(insertComments(newComment)){
            System.out.println("Listo");
            return "redirect:/home/post/"+id;
        } else{
            model.addAttribute("errorComment",true);
            model.addAttribute("error",false);
            PostViewDTO post = obtainPostById(id);
            model.addAttribute("post", post);
            if(post == null) model.addAttribute("error",true);
            List<CommentDTO> comments = obtainComments(id);
            model.addAttribute("comments",comments);
            List<TagPopularityDTO> tren = obtainTagsById(id);
            model.addAttribute("tren",tren);
            model.addAttribute("newComment", newComment);
            model.addAttribute("postId", id);
            return "PostView";
        }
    }

    @PostMapping("/delete")
    public String deletePost(@RequestParam("postId") Long id, Model model) {
        if(!deletePost(id)){
            return "redirect:/home/post/"+id;
        }
        return "redirect:/home";
    }

    @PostMapping("/update")
    public String update(@RequestParam("postId") Long id, Model model) {
        PostViewDTO post = obtainPostById(id);
        model.addAttribute("error", false);
        model.addAttribute("post", post);
        model.addAttribute("postId",id);
        return "ChangePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@Valid @ModelAttribute("post") PostViewDTO postViewDTO,
                             BindingResult bindingResult, Model model,
                             @RequestParam("postId") Long id){
        if(bindingResult.hasErrors()) {
            return "ChangePost";
        }
        postViewDTO.setId(id);
        postViewDTO.setPersonId((Long) model.getAttribute("userId"));
        if(!updatePost(postViewDTO)){
            model.addAttribute("error",true);
            return "ChangePost";
        }
        return "redirect:/home/post/"+postViewDTO.getId();
    }
}


