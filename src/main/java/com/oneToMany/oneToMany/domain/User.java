package com.oneToMany.oneToMany.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String realName;

    @ManyToOne
    @JoinColumn
    private User userFollowing = this;

    @ManyToOne
    @JoinColumn
    private User userFollower = this;

    @OneToMany(mappedBy = "userFollowing")
    private List<User> followingList = new ArrayList<User>();

    @OneToMany(mappedBy = "userFollower")
    private List<User> followerList = new ArrayList<User>();

    public void addFollowing(User following) {
        this.followingList.add(following);

        if(!following.getFollowerList().contains(this)) {
            following.getFollowerList().add(this);
        }
        //연관관계의 주인을 통한 확인
        if(!following.getUserFollower().getFollowerList().contains(this)) {
            following.getUserFollower().getFollowerList().add(this);
        }
    }
    public void addFollower(User follower) {
        this.followerList.add(follower);

        if(follower.getFollowingList().contains(this)) {
            follower.getFollowingList().add(this);
        }
        //연관관계의 주인을 통한 확인
        if(!follower.getUserFollowing().getFollowingList().contains(this)) {
            follower.getUserFollowing().getFollowingList().add(this);
        }
    }

    @Builder
    public User(String username, String realName) {
        this.username = username;
        this.realName = realName;
    }
}
