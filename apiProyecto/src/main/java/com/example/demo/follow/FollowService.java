package com.example.demo.follow;

import com.example.demo.person.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private EntityManager entityManager;

    public List<Follow> findAllFollow() {
        return followRepository.findAll();
    }

    public void insertFollow(Follow follow) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("createFollow");
        query.setParameter("follower_id", follow.getId().getFollowerId());
        query.setParameter("followed_id", follow.getId().getFollowedId());
        query.execute();
    }

    public Long getTotalFollowers(Long id) {
        return followRepository.getTotalFollowers(id);
    }

    public Long getTotalFollowing(Long id) {
        return followRepository.getTotalFollowing(id);
    }

    public List<User> getFollowingUsers(Long id) {
        List<Object[]> objs = followRepository.getFollowingUsers(id);
        List<User> users = new ArrayList<>();
        for (Object[] result : objs) {
            User user = new User(
                    ((BigDecimal) result[0]).longValue(),
                    (String) result[1],
                    (String) result[2],
                    ((BigDecimal) result[3]).longValue());
            users.add(user);
        }
        return users;
    }

    public Long isFollowing(Long personId, Long userId){
        return followRepository.isFollowing(personId, userId);
    }

    @Transactional
    public void deleteFollow(Long personId, Long userId){
        followRepository.deleteFollow(personId, userId);
    }

}
