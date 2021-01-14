package com.messenger.chat.repository;

import com.messenger.chat.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
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
                .nick("ijot")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
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
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        User user2 = User.builder()
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        User user3 = User.builder()
                .nick("kk")
                .name("Karina-Kranik")
                .sex('W')
                .location("Rytro")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

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
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        User user2 = User.builder()
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        User user3 = User.builder()
                .nick("kk")
                .name("Karina-Kranik")
                .sex('W')
                .location("Rytro")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        user1.addFriend(user2);
        user1.addFriend(user3);

        user2.addFriend(user3);

        //When
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        User savedUser3 = userRepository.save(user3);

        Long id1 = savedUser1.getId();
        Long id2 = savedUser2.getId();
        Long id3 = savedUser3.getId();

        int i1 = savedUser1.getFriends().size();
        int i2 = savedUser2.getFriends().size();
        int i3 = savedUser3.getFriends().size();

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
    public void testFindByLoggedInTrue() {

        //Given
        User user1 = User.builder()
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        User user2 = User.builder()
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(false)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        //When
        Long id1 = userRepository.save(user1).getId();
        Long id2 = userRepository.save(user2).getId();

        int count = userRepository.findByLoggedInTrue().size();

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

    @Test
    public void testGetUserById() {
        //Given
        User user1 = User.builder()
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        User user2 = User.builder()
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        User user3 = User.builder()
                .nick("kk")
                .name("Karina-Kranik")
                .sex('W')
                .location("Rytro")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        //When
        Long id1 = userRepository.save(user1).getId();
        Long id2 = userRepository.save(user2).getId();
        Long id3 = userRepository.save(user3).getId();

        String str = userRepository.findById(id3).orElse(new User("No such user")).getNick();

        //Then
        Assert.assertEquals("kk", str);

        //CleanUp
        try {
            userRepository.deleteById(id1);
            userRepository.deleteById(id2);
            userRepository.deleteById(id3);
        } catch (Exception e) {
            //
        }
    }
}
