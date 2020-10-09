package com.messenger.chat.repository;

import com.messenger.chat.domain.Message;
import com.messenger.chat.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTestSuite {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testSaveUser() {

        //Given
        User user = User.builder()
                .id(null)
                .nick("ijot")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new ArrayList<>())
                .rooms(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        //When
        User savedUser = userRepository.save(user);

        String str = savedUser.getLocation();
        Long id = savedUser.getId();

        //Then
        Assert.assertEquals("Bangalore", str);

        //CleanUp
        userRepository.deleteById(id);
    }

    @Test
    public void testGetUsers() {

        //Given
        User user1 = User.builder()
                .id(null)
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new ArrayList<>())
                .rooms(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        User user2 = User.builder()
                .id(null)
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new ArrayList<>())
                .rooms(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        User user3 = User.builder()
                .id(null)
                .nick("kk")
                .name("Karina-Kranik")
                .sex('W')
                .location("Rytro")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new ArrayList<>())
                .rooms(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        Message message1 = Message.builder()
                .messageText("Message #1 text")
                .dateCreated(LocalDate.now())
                .build();

        Message message2 = Message.builder()
                .messageText("Message #2 text")
                .dateCreated(LocalDate.now())
                .build();

        user1.addMessage(message1);
        user1.addMessage(message2);

        user1.addFriend(user2);
        user1.addFriend(user3);

        user2.addFriend(user3);
        user2.addFriend(user1);

        user3.addFriend(user1);
        user3.addFriend(user2);

        //When
        Long id1 = userRepository.save(user1).getId();
        Long id2 = userRepository.save(user2).getId();
        Long id3 = userRepository.save(user3).getId();
        int count = userRepository.findAll().size();

        //Then
        Assert.assertEquals(3, count);

        //CleanUp
        try {
            userRepository.deleteById(id1);
            userRepository.deleteById(id2);
            userRepository.deleteById(id3);
        } catch (Exception e) {
            //
        }
    }

    @Test
    public void testGetUserFriends() {

        //Given
        User user1 = User.builder()
                .id(null)
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new ArrayList<>())
                .rooms(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        User user2 = User.builder()
                .id(null)
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new ArrayList<>())
                .rooms(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        User user3 = User.builder()
                .id(null)
                .nick("kk")
                .name("Karina-Kranik")
                .sex('W')
                .location("Rytro")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new ArrayList<>())
                .rooms(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        Message message1 = Message.builder()
                .messageText("Message #1 text")
                .dateCreated(LocalDate.now())
                .build();

        Message message2 = Message.builder()
                .messageText("Message #2 text")
                .dateCreated(LocalDate.now())
                .build();

        user1.addMessage(message1);
        user1.addMessage(message2);

        user1.addFriend(user2);
        user1.addFriend(user3);

        user2.addFriend(user3);

        //When
        Long id1 = userRepository.save(user1).getId();
        Long id2 = userRepository.save(user2).getId();
        Long id3 = userRepository.save(user3).getId();

        int i1 = userRepository.save(user1).getFriends().size();
        int i2 = userRepository.save(user2).getFriends().size();
        int i3 = userRepository.save(user3).getFriends().size();

        //Then
        Assert.assertEquals(2, i1);
        Assert.assertEquals(1, i2);
        Assert.assertEquals(0, i3);

        //CleanUp
        try {
            userRepository.deleteById(id1);
            userRepository.deleteById(id2);
            userRepository.deleteById(id3);
        } catch (Exception e) {
            //
        }
    }

    @Test
    public void testfindByLoggedInTrue() {

        //Given
        User user1 = User.builder()
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new ArrayList<>())
                .rooms(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        User user2 = User.builder()
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(false)
                .messages(new ArrayList<>())
                .rooms(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .friends(new HashSet<>())
                .build();


        //When
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Long id1 = user1.getId();
        Long id2 = user2.getId();
        boolean b1 = savedUser1.isLoggedIn();

        System.out.println(b1);
        int count = userRepository.findByLoggedInTrue(true).size();

        //Then
        Assert.assertEquals(1, count);

        //CleanUp
        try {
            userRepository.deleteById(id1);
            userRepository.deleteById(id2);
        } catch (Exception e) {
            //
        }
    }

}
