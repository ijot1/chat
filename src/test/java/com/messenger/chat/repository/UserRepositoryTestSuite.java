package com.messenger.chat.repository;

import com.messenger.chat.domain.Room;
import com.messenger.chat.domain.User;
import com.messenger.chat.domain.UserRoom;
import com.messenger.chat.domain.UserRoomId;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTestSuite {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    private EntityManager em;

    @PersistenceUnit
    public EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ChatPU");

    @Test
    public void testSaveUser() {
        //Given
        em = emFactory.createEntityManager();

        em.getTransaction().begin();
        User user = User.builder()
                .id(null)
                .nick("mz")
                .name("Milena-Zanik")
                .sex('W')
                .location("Katowice")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Room room = Room.builder()
                .id(null)
                .name("Silence Service")
                .roomUsersRooms(new HashSet<>())
                .build();

        em.persist(room);
        em.getTransaction().commit();

        em.getTransaction().begin();
        UserRoom userRoom = UserRoom.builder()
                .id(new UserRoomId(user.getId(), room.getId()))
                .user(user)
                .room(room)
                .addedOn(LocalDate.now())
                .recipients(new HashSet<>())
                .build();

        em.persist(room);
        em.getTransaction().commit();

        //When
        em.getTransaction().begin();
        User savedUser = em.merge(user);
        Room savedRoom = em.merge(room);
        savedUser.addUserRoomToUser(savedRoom);

        em.getTransaction().commit();
        em.close();
        savedUser = userRepository.save(user);

        String str = savedUser.getLocation();
        Long id1 = savedUser.getId();
        Long id2 = savedRoom.getId();

        //Then
        Assert.assertEquals("Katowice", str);

        //CleanUp
        userRepository.deleteById(id1);
        roomRepository.deleteById(id2);

    }

    @Test
    public void testFindUsers() {
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
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
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
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        //When
        int count = userRepository.findAll().size();

        user1 = (User) userRepository.findAll().toArray()[0];
        user2 = (User) userRepository.findAll().toArray()[1];

        Long id1 = user1.getId();
        Long id2 = user2.getId();

        //Then
        Assert.assertEquals(2, count);

        //CleanUp
        userRepository.deleteById(user1.getId());
        userRepository.deleteById(user2.getId());

    }

    @Test
    public void testAddUsersFriend() {
        //Given
        EntityManager em = emFactory.createEntityManager();

        User user1 = User.builder()
                .id(null)
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
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
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        userRepository.addUsersFriend(user1.getId(), user2.getId());

        user1 = (User) userRepository.findAll().toArray()[0];
        user2 = (User) userRepository.findAll().toArray()[1];

        //When
        em.getTransaction().begin();

        User readUser1 = em.merge(user1);
        User readUser2 = em.merge(user2);
        int user1FriendsCount = readUser1.getFriends().size();

        readUser1.removeFriend(readUser2);
        em.getTransaction().commit();

        if (em != null && em.isOpen())
            em.close(); // You create it, you close it!

        //Then
        Assert.assertEquals(1, user1FriendsCount);

        //CleanUp
        userRepository.deleteById(user1.getId());
        userRepository.deleteById(user2.getId());

    }

    @Test
    public void testFindFriends() {
        //Given
        EntityManager em = emFactory.createEntityManager();

        em.getTransaction().begin();
        User user1 = User.builder()
                .id(null)
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        User user2 = User.builder()
                .id(null)
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        User user3 = User.builder()
                .id(null)
                .nick("kk")
                .name("Karina-Kranik")
                .sex('W')
                .location("Rytro")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user3);
        em.getTransaction().commit();

        //When
        user1 = (User) userRepository.findAll().toArray()[0];
        user2 = (User) userRepository.findAll().toArray()[1];
        user3 = (User) userRepository.findAll().toArray()[2];

        userRepository.addUsersFriend(user1.getId(), user2.getId());
        userRepository.addUsersFriend(user1.getId(), user3.getId());
        userRepository.addUsersFriend(user2.getId(), user1.getId());
        userRepository.addUsersFriend(user2.getId(), user3.getId());
        userRepository.addUsersFriend(user3.getId(), user1.getId());
        userRepository.addUsersFriend(user3.getId(), user2.getId());

        int f1 = userRepository.findFriends(user1.getId()).size();
        int f2 = userRepository.findFriends(user2.getId()).size();
        int f3 = userRepository.findFriends(user3.getId()).size();

        //Then
        Assert.assertEquals(2, f1);
        Assert.assertEquals(2, f2);
        Assert.assertEquals(2, f3);

        //CleanUp
        em = emFactory.createEntityManager();
        em.getTransaction().begin();

        user1 = em.merge(user1);
        user2 = em.merge(user2);
        user3 = em.merge(user3);

        user1.removeFriend(user2);
        user1.removeFriend(user3);
        user2.removeFriend(user1);
        user2.removeFriend(user3);
        user3.removeFriend(user1);
        user3.removeFriend(user2);

        em.getTransaction().commit();

        if (em != null && em.isOpen())
            em.close(); // You create it, you close it!

        userRepository.deleteById(user1.getId());
        userRepository.deleteById(user2.getId());
        userRepository.deleteById(user3.getId());

    }

    @Test
    public void testDeleteUsersFriend() {

        //Given
        EntityManager em = emFactory.createEntityManager();

        em.getTransaction().begin();
        User user1 = User.builder()
                .id(null)
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        User user2 = User.builder()
                .id(null)
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        User user3 = User.builder()
                .id(null)
                .nick("kk")
                .name("Karina-Kranik")
                .sex('W')
                .location("Rytro")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user3);
        em.getTransaction().commit();

        em.getTransaction().begin();

        User readUser1 = em.merge(user1);
        User readUser2 = em.merge(user2);
        User readUser3 = em.merge(user3);

        readUser1.addFriend(readUser2);
        readUser1.addFriend(readUser3);

        em.getTransaction().commit();

        if (em != null && em.isOpen())
            em.close(); // You create it, you close it!

        //When
        user1 = (User) userRepository.findAll().toArray()[0];
        user2 = (User) userRepository.findAll().toArray()[1];
        user3 = (User) userRepository.findAll().toArray()[2];

        userRepository.deleteUsersFriend(user1.getId(), user2.getId());
        userRepository.deleteUsersFriend(user1.getId(), user3.getId());

        int fNr = userRepository.findFriends(user1.getId()).size();

        //Then
        Assert.assertEquals(0, fNr);

        //CleanUp
        userRepository.deleteById(user1.getId());
        userRepository.deleteById(user2.getId());
        userRepository.deleteById(user3.getId());
    }

    @Test
    public void testFindLoggedInUsers() {
        //Given
        EntityManager em = emFactory.createEntityManager();

        em.getTransaction().begin();
        User user1 = User.builder()
                .id(null)
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        User user2 = User.builder()
                .id(null)
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        User user3 = User.builder()
                .id(null)
                .nick("kk")
                .name("Karina-Kranik")
                .sex('W')
                .location("Rytro")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(false)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user3);
        em.getTransaction().commit();

        if (em != null && em.isOpen())
            em.close(); // You create it, you close it!

        //When
        int count = userRepository.findByLoggedInTrue().size();

        //Then
        Assert.assertEquals(2, count);

        //CleanUp
        userRepository.deleteById(user1.getId());
        userRepository.deleteById(user2.getId());
        userRepository.deleteById(user3.getId());

    }

    @Test
    public void testFindUserById() {
        //Given
        EntityManager em = emFactory.createEntityManager();

        em.getTransaction().begin();
        User user1 = User.builder()
                .id(null)
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        User user2 = User.builder()
                .id(null)
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        User user3 = User.builder()
                .id(null)
                .nick("kk")
                .name("Karina-Kranik")
                .sex('W')
                .location("Rytro")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user3);
        em.getTransaction().commit();

        if (em != null && em.isOpen())
            em.close(); // You create it, you close it!

        //When
        user3 = (User) userRepository.findAll().toArray()[2];
        Long id3 = user3.getId();

        String str = userRepository.findById(id3).orElse(new User("No such user")).getNick();

        //Then
        Assert.assertEquals("kk", str);

        //CleanUp
        userRepository.deleteById(user1.getId());
        userRepository.deleteById(user2.getId());
        userRepository.deleteById(user3.getId());

    }

    @Test
    public void testDeleteUserById() {
        //Given
        em = emFactory.createEntityManager();
        em.getTransaction().begin();

        User user = User.builder()
                .id(null)
                .nick("az")
                .name("Andy-Zelig")
                .sex('M')
                .location("Oslo")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user);
        em.getTransaction().commit();

        //When
        em.getTransaction().begin();

        User readUser = em.merge(user);
        Long uId = readUser.getId();

        em.getTransaction().commit();

        if (em != null && em.isOpen())
            em.close(); // You create it, you close it!

        userRepository.deleteById(uId);

        int count = userRepository.findAll().size();

        //Then
        Assert.assertEquals(0, count);

    }
}
