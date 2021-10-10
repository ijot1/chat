package com.messenger.chat.repository;

import com.messenger.chat.domain.*;
import com.messenger.chat.exception.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipientRepositoryTestSuite {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipientRepositoryTestSuite.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RecipientRepository recipientRepository;

    @Autowired
    private UserRoomRepository userRoomRepository;

    @Autowired
    private EntityManager em;

    @PersistenceUnit
    public EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ChatPU");

    public static Long user1Id;
    public static Long user2Id;
    public static Long user3Id;

    public static Long message1Id;
    public static Long message2Id;

    public static Long roomId;

    public static UserRoomId userRoom1Id;
    public static UserRoomId userRoom2Id;
    public static UserRoomId userRoom3Id;

    public static Long recipient1Id;
    public static Long recipient2Id;
    public static Long recipient3Id;

    @Before
    public void prepareMessageRecipientRepositoryTestData() {
        em = emFactory.createEntityManager();
        try {
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

            Room room = Room.builder()
                    .id(null)
                    .name("Silence Service")
                    .roomUsersRooms(new HashSet<>())
                    .build();
            em.persist(room);

            em.getTransaction().commit();

            em.getTransaction().begin();
            user1 = em.merge(user1);
            user2 = em.merge(user2);
            user3 = em.merge(user3);

            user1Id = user1.getId();
            user2Id = user2.getId();
            user3Id = user3.getId();

            Message message1 = Message.builder()
                    .id(null)
                    .messageText("Message #1 text")
                    .dateCreated(LocalDate.now())
                    .creator(user1)
                    .recipientSet(new HashSet<>())
                    .build();

            Message message2 = Message.builder()
                    .id(null)
                    .messageText("Message #2 text")
                    .dateCreated(LocalDate.now())
                    .creator(user2)
                    .recipientSet(new HashSet<>())
                    .build();

            em.persist(message1);
            em.persist(message2);

            em.getTransaction().commit();

            em.getTransaction().begin();
            em.merge(room);
            roomId = room.getId();

            UserRoom userRoom1 = UserRoom.builder()
                    .id(new UserRoomId(user1.getId(), room.getId()))
                    .user(user1)
                    .room(room)
                    .addedOn(LocalDate.now())
                    .recipients(new HashSet<>())
                    .build();
            em.persist(userRoom1);

            UserRoom userRoom2 = UserRoom.builder()
                    .id(new UserRoomId(user2.getId(), room.getId()))
                    .user(user2)
                    .room(room)
                    .addedOn(LocalDate.now())
                    .recipients(new HashSet<>())
                    .build();
            em.persist(userRoom2);

            UserRoom userRoom3 = UserRoom.builder()
                    .id(new UserRoomId(user3.getId(), room.getId()))
                    .user(user3)
                    .room(room)
                    .addedOn(LocalDate.now())
                    .recipients(new HashSet<>())
                    .build();
            em.persist(userRoom3);

            em.getTransaction().commit();

            em.getTransaction().begin();
            em.merge(userRoom1);
            em.merge(userRoom2);
            em.merge(userRoom3);

            userRoom1Id = userRoom1.getId();
            userRoom2Id = userRoom2.getId();
            userRoom3Id = userRoom3.getId();

            em.merge(message1);
            em.merge(message2);

            message1Id = message1.getId();
            message2Id = message2.getId();

            Recipient recipient1 = Recipient.builder()
                    .id(null)
                    .addedOn(LocalDate.now())
                    .message(message2)
                    .userRoom(null)
                    .user(user1)
                    .build();
            em.persist(recipient1);

            Recipient recipient2 = Recipient.builder()
                    .id(null)
                    .addedOn(LocalDate.now())
                    .message(message1)
                    .userRoom(userRoom2)
                    .user(null)
                    .build();
            em.persist(recipient2);

            Recipient recipient3 = Recipient.builder()
                    .id(null)
                    .addedOn(LocalDate.now())
                    .message(message1)
                    .userRoom(userRoom3)
                    .user(null)
                    .build();
            em.persist(recipient3);
            em.getTransaction().commit();

            em.getTransaction().begin();
            em.merge(recipient1);
            em.merge(recipient2);
            em.merge(recipient3);

            recipient1Id = recipient1.getId();
            recipient2Id = recipient2.getId();
            recipient3Id = recipient3.getId();

            em.merge(userRoom2);
            em.merge(userRoom3);

            userRoom2.addMessageRecipient(recipient2);
            userRoom3.addMessageRecipient(recipient3);
            em.merge(user1);
            user1.addRecipient(recipient1);
            em.merge(user1);
            em.merge(user2);
            user1.addFriend(user2);
            em.merge(userRoom1);
            user1.getUserUsersRooms().add(userRoom1);
            userRoom2 = em.merge(userRoom2);
            user2.getUserUsersRooms().add(userRoom2);
            em.merge(user3);
            em.merge(userRoom3);
            user3.getUserUsersRooms().add(userRoom3);

            em.flush();
            em.clear();
            em.getTransaction().commit();

        } catch (Exception rbEx) {
            System.err.println("Rollback of transaction failed, trace follows!");
            rbEx.printStackTrace(System.err);
        }

        if (em != null && em.isOpen())
            em.close(); // You create it, you close it!

    }

    @After
    public void cleanUpRepository() {
        em = emFactory.createEntityManager();

        em.getTransaction().begin();

        User user1 = em.find(User.class, user1Id);
        User user2 = em.find(User.class, user2Id);
        User user3 = em.find(User.class, user3Id);

        Recipient recipient1 = em.find(Recipient.class, recipient1Id);
        Recipient recipient2 = em.find(Recipient.class, recipient2Id);
        Recipient recipient3 = em.find(Recipient.class, recipient3Id);

        UserRoom userRoom1 = em.find(UserRoom.class, userRoom1Id);
        UserRoom userRoom2 = em.find(UserRoom.class, userRoom2Id);
        UserRoom userRoom3 = em.find(UserRoom.class, userRoom3Id);

        Room room = em.find(Room.class, roomId);

        Message message1 = em.find(Message.class, message1Id);
        Message message2 = em.find(Message.class, message2Id);

        user1.removeRecipient(recipient1);
        user2.removeRecipient(recipient2);
        user2.removeRecipient(recipient3);

        user2.removeUserRoomFromUser(room);
        user3.removeUserRoomFromUser(room);

        em.remove(message1);
        em.remove(message2);

        em.remove(user1);
        em.remove(user2);
        em.remove(user3);
        em.remove(room);

        em.flush();
        em.clear();
        em.getTransaction().commit();

        em.close();

    }

    @Test
    public void testFindRecipientByMessageIdAndUserIdOrUserRoomId() {
        //Given
        //@Before prepared data
        em = emFactory.createEntityManager();

        //When
        List<Recipient> foundRecipients1 = recipientRepository.findRecipientsByMessageIdAndUserIdOrUserRoomId_RoomId(message2Id, user1Id, null);
        List<Recipient> foundRecipients2 = recipientRepository.findRecipientsByMessageIdAndUserIdOrUserRoomId_RoomId(message1Id, null, userRoom2Id.getRoomId());

        em.getTransaction().begin();
        String str1 = em.find(Recipient.class, foundRecipients1.get(0).getId()).getUser().getNick();

        String str2 = em.find(Recipient.class, foundRecipients2.get(0).getId()).getUserRoom().getRoom().getName();
        String str3 = em.find(Recipient.class, foundRecipients2.get(0).getId()).getUserRoom().getUser().getNick();

        String str4 = em.find(Recipient.class, foundRecipients2.get(1).getId()).getUserRoom().getUser().getNick();
        em.getTransaction().commit();
        em.close();

        //Then
        Assert.assertEquals("ij", str1);
        Assert.assertEquals("Silence Service", str2);
        Assert.assertEquals("jk", str3);
        Assert.assertEquals("kk", str4);
    }

    @Test
    public void testFindAllRecipients() {
        //Given
        //@Before prepared data

        //When
        int count = recipientRepository.findAll().size();
        //Then
        Assert.assertEquals(3, count);

    }

    @Test
    public void testFindRecipientById() {
        //Given
        //@Before prepared data

        //When
        Recipient recipient = recipientRepository.findById(recipient1Id).orElseThrow(() -> new EntityNotFoundException(Recipient.class, recipient1Id));
        String str = recipient.getUser().getName();
        //Then
        Assert.assertEquals("Irena-Janik", str);
    }

    @Test
    public void testSaveRecipient() {
        //Given
        EntityManager em = emFactory.createEntityManager();

        Message message2 = (Message) messageRepository.findAll().toArray()[1];
        User user3 = (User) userRepository.findAll().toArray()[2];

        em.getTransaction().begin();
        em.merge(message2);
        em.merge(user3);


        Recipient recipient = Recipient.builder()
                .id(null)
                .addedOn(LocalDate.now())
                .message(message2)
                .userRoom(null)
                .user(user3)
                .build();

        recipientRepository.save(recipient);
        em.getTransaction().commit();

        //When
        int count = recipientRepository.findAll().size();

        //Then
        Assert.assertEquals(4, count);

        //CleanUp
        em.getTransaction().begin();

        em.remove(em.contains(recipient) ? recipient : em.merge(recipient));
        em.getTransaction().commit();
//        em.close();
    }

    @Test
    public void testDeleteRecipientById() {
        //Given
        //@Before prepared data

        //When
        recipientRepository.deleteById(recipient3Id);
        int count = recipientRepository.findAll().size();

        //Then
        Assert.assertEquals(2, count);

    }

}
