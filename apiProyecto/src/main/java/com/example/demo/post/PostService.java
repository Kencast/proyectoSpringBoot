package com.example.demo.post;

import com.example.demo.tag.TagService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private EntityManager entityManager;

    public List<Post> findAllPost() {return postRepository.findAll();}

    public void insertPost(Post post, String tags) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("createPost");
        query.setParameter("title_c", post.getTitle());
        query.setParameter("description", post.getDescription());
        query.setParameter("personId", post.getPerson());
        query.setParameter("tags", tags);
        query.execute();
    }

    public List<PostInfoDTO> getPostsInfo(){
        List<Object[]> objs = postRepository.getPostsInfo();
        return getPostInfoDTOS(objs);
    }

    public List<PostInfoDTO> getPostsInfoById(Long id){
        List<Object[]> objs = postRepository.getPostsInfoById(id);
        return getPostInfoDTOS(objs);
    }

    public PostViewDTO getPostsView(Long id){
        List<Object[]> objs=postRepository.getPostView(id);
        List<PostViewDTO> posts=new ArrayList<>();
        for(Object[] obj:objs){
            PostViewDTO post=new PostViewDTO(
                    ((BigDecimal) obj[0]).longValue(),
                    (String) obj[1],
                    (String) obj[2],
                    (String) obj[3],
                    ((BigDecimal) obj[4]).longValue(),
                    (String) obj[5],
                    (String) obj[6]
            );
            posts.add(post);
        }
        return posts.get(0);
    }

    private List<PostInfoDTO> getPostInfoDTOS(List<Object[]> objs) {
        List<PostInfoDTO> posts = new ArrayList<>();
        for(Object[] obj : objs){
            PostInfoDTO post=new PostInfoDTO(
                    ((BigDecimal) obj[0]).longValue(),
                    (String) obj[1],
                    ((BigDecimal) obj[2]).longValue(),
                    (String) obj[3],
                    (String) obj[4],
                    ((BigDecimal) obj[5]).longValue()
            );
            post.setTags(tagService.getTagsByPost(post.getPostId()));
            posts.add(post);
        }
        return posts;
    }

    public void deletePost(Long postId) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("deletePost");
        query.setParameter("d_post_id", postId);
        query.execute();
    }

    public void updatePost(PostViewDTO postViewDTO){
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("updatePost");
        query.setParameter("u_post_id",postViewDTO.getId());
        query.setParameter("u_title", postViewDTO.getTitle());
        query.setParameter("u_description", postViewDTO.getDescription());
        query.setParameter("u_person_id", postViewDTO.getPersonId());
        query.execute();
    }

}
