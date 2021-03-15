package com.oneToMany.oneToMany.Service;

import com.oneToMany.oneToMany.domain.User;
import com.oneToMany.oneToMany.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService{

    @Autowired
    UserJpaRepository userJpaRepository;

    public void addUser(String username, String realName){
        User user = User.builder()
                .username(username)
                .realName(realName)
                .build();
        validateDuplicateUser(user);
        userJpaRepository.save(user);
    }

    public void validateDuplicateUser(User user) {
        Optional<User> findUser =
                userJpaRepository.findUserByUsername(user.getUsername());
        if(findUser.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public void addFollow(String followerName, String username) throws Exception {
        //엔티티 받아오기
        User followerUser = userJpaRepository.findUserByUsername(followerName)
                .orElseThrow(() -> new Exception("무야호"));

        User user = userJpaRepository.findUserByUsername(username)
                .orElseThrow(() -> new Exception("무야호"));

        //객체지향적으로 추가(연관관계의 주인으로도 삽입)
        followerUser.getUserFollowing().getFollowingList().add(user);
        followerUser.getFollowingList().add(user);

        user.getUserFollower().getFollowerList().add(followerUser);
        user.getFollowerList().add(followerUser);
    }

    public User getOneUserEntity(String username) throws Exception {
        return userJpaRepository.findUserByUsername(username)
                .orElseThrow(() -> new Exception("무야호"));
    }
}