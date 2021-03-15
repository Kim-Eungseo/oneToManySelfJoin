package com.oneToMany.oneToMany;

import com.oneToMany.oneToMany.Service.UserService;
import com.oneToMany.oneToMany.domain.User;
import com.oneToMany.oneToMany.repository.UserJpaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@SpringBootTest
public class OneToManyApplicationTests {

	@Autowired
	UserJpaRepository userJpaRepository;
	@Autowired
	UserService userService;

	@Transactional
	@Test
	public void 유저_팔로우_테스트() throws Exception {
		//given
		String username = "user";
		String realName = "real";

		userService.addUser(username, realName);
		userService.addUser(username + "Follow", realName + "Follow");

		//when
		userService.addFollow("userFollow", "user");

		//then
		User checkFollower = userService.getOneUserEntity("userFollow");
		User checkUser = userService.getOneUserEntity("user");

		Assert.assertEquals(checkUser.getFollowerList().contains(checkFollower), true);
		Assert.assertEquals(checkFollower.getFollowingList().contains(checkUser), true);
	}

}
