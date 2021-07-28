package com.messenger.chat.repository;

import com.messenger.chat.exception.EntityNotFoundException;
import com.messenger.chat.domain.*;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static UserRoomId userRoom1Id;
    public static UserRoomId userRoom2Id;
    public static UserRoomId userRoom3Id;

    public static Long recipient1Id;
    public static Long recipient2Id;
    public static Long recipient3Id;

    @Before
    public void testPrepareMessageRecipientRepositoryTestSuite() {
        EntityManager em = emFactory.createEntityManager();
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
            Room room = Room.builder()
                    .id(null)
                    .name("Silence Service")
                    .roomUsersRooms(new HashSet<>())
                    .build();

            em.persist(room);
            em.getTransaction().commit();

            em.getTransaction().begin();
            User readUser1 = em.merge(user1);
            User readUser2 = em.merge(user2);

            Message message1 = Message.builder()
                    .id(null)
                    .messageText("Message #1 text")
                    .dateCreated(LocalDate.now())
                    .creator(readUser1)
                    .recipientSet(new HashSet<>())
                    .build();

            Message message2 = Message.builder()
                    .id(null)
                    .messageText("Message #2 text")
                    .dateCreated(LocalDate.now())
                    .creator(readUser2)
                    .recipientSet(new HashSet<>())
                    .build();

            em.persist(message1);
            em.persist(message2);

            em.getTransaction().commit();

            em.getTransaction().begin();

            readUser1 = em.merge(user1);
            readUser2 = em.merge(user2);
            User readUser3 = em.merge(user3);

            Room readRoom = em.merge(room);

            UserRoom userRoom1 = UserRoom.builder()
                    .id(new UserRoomId(readUser1.getId(), readRoom.getId()))
                    .user(readUser1)
                    .room(readRoom)
                    .addedOn(LocalDate.now())
                    .recipients(new HashSet<>())
                    .build();

            em.persist(userRoom1);

            UserRoom userRoom2 = UserRoom.builder()
                    .id(new UserRoomId(readUser2.getId(), readRoom.getId()))
                    .user(readUser2)
                    .room(readRoom)
                    .addedOn(LocalDate.now())
                    .recipients(new HashSet<>())
                    .build();

            em.persist(userRoom2);

            UserRoom userRoom3 = UserRoom.builder()
                    .id(new UserRoomId(readUser3.getId(), readRoom.getId()))
                    .user(readUser3)
                    .room(readRoom)
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

            UserRoom readUserRoom1 = em.find(UserRoom.class, userRoom1Id);
            UserRoom readUserRoom2 = em.find(UserRoom.class, userRoom2Id);
            UserRoom readUserRoom3 = em.find(UserRoom.class, userRoom3Id);

            em.getTransaction().commit();

            em.getTransaction().begin();

            Message readMessage1 = em.merge(message1);
            Message readMessage2 = em.merge(message2);

            message1Id = readMessage1.getId();
            message2Id = readMessage2.getId();

            Recipient recipient1 = Recipient.builder()
                    .id(null)
                    .addedOn(LocalDate.now())
                    .message(message2)
                    .userRoom(null)
                    .user(readUser1)
                    .build();

            Recipient recipient2 = Recipient.builder()
                    .id(null)
                    .addedOn(LocalDate.now())
                    .message(message1)
                    .userRoom(readUserRoom2)
                    .user(null)
                    .build();

            Recipient recipient3 = Recipient.builder()
                    .id(null)
                    .addedOn(LocalDate.now())
                    .message(message1)
                    .userRoom(readUserRoom3)
                    .user(null)
                    .build();

            em.persist(recipient1);
            em.persist(recipient2);
            em.persist(recipient3);

            em.getTransaction().commit();

            em.getTransaction().begin();

            em.merge(recipient1);
            em.merge(recipient2);
            em.merge(recipient3);

            recipient1Id = recipient1.getId();
            recipient2Id = recipient2.getId();
            recipient3Id = recipient3.getId();

            em.getTransaction().commit();

            em.getTransaction().begin();
            readUser1 = em.merge(user1);
            readUser1.addRecipient(recipient1);
            em.getTransaction().commit();

            em.getTransaction().begin();
            readUser1 = em.merge(user1);
            readUser2 = em.merge(user2);
            readUser1.addFriend(readUser2);
            em.getTransaction().commit();

            em.getTransaction().begin();
            readUser1 = em.merge(user1);
            readUserRoom1 = em.merge(userRoom1);
            readUser1.getUserUsersRooms().add(readUserRoom1);
            em.getTransaction().commit();

            em.getTransaction().begin();
            readUser2 = em.merge(user2);
            readUserRoom2 = em.merge(userRoom2);
            readUser2.getUserUsersRooms().add(readUserRoom2);
            em.getTransaction().commit();

            em.getTransaction().begin();
            readUser3 = em.merge(user3);
            readUserRoom3 = em.merge(userRoom3);
            readUser3.getUserUsersRooms().add(readUserRoom3);
            em.getTransaction().commit();

            System.out.println("count = " + recipientRepository.findAll().size());

        } catch (Exception rbEx) {
            System.err.println("Rollback of transaction failed, trace follows!");
            rbEx.printStackTrace(System.err);
        }

        if (em != null && em.isOpen())
            em.close(); // You create it, you close it!

    }

    @After
    public void cleanUpRepository() {
        recipientRepository.deleteAll();
        userRoomRepository.deleteAll();
        messageRepository.deleteAll();
        userRepository.deleteAll();
        roomRepository.deleteAll();

    }

    @Test
    public void testFindRecipientByMessageIdAndUserIdOrUserRoomId() {
        //Given
        //@Before prepared data
        SessionFactory sf = emFactory.unwrap(SessionFactory.class);

        //When
        Session session = sf.openSession();
        EntityTransaction tx = session.beginTransaction();
        List<Recipient> foundRecipients1 = recipientRepository.findRecipientByMessageIdAndUserIdOrUserRoomId(message2Id, null, user1Id);
        List<Recipient> foundRecipients2 = recipientRepository.findRecipientByMessageIdAndUserIdOrUserRoomId(message1Id, userRoom2Id.getRoomId(), null);
        List<Recipient> foundRecipients3 = recipientRepository.findRecipientByMessageIdAndUserIdOrUserRoomId(message1Id, userRoom3Id.getRoomId(), null);
        tx.commit();
        session.close();


        session = sf.openSession();
        tx = session.beginTransaction();
        session.load(foundRecipients1.get(0), recipient1Id);
        String str1 = foundRecipients1.get(0).getUser().getNick();
        tx.commit();
        session.close();

        session = sf.openSession();
        tx = session.beginTransaction();
        session.load(foundRecipients2.get(0), recipient2Id);
        String str2 = foundRecipients2.get(0).getUserRoom().getRoom().getName();
        String str3 = foundRecipients2.get(0).getUserRoom().getUser().getNick();
        tx.commit();
        session.close();

        session = sf.openSession();
        tx = session.beginTransaction();
        session.load(foundRecipients3.get(0), recipient3Id);
        String str4 = foundRecipients3.get(0).getUserRoom().getUser().getNick();
        tx.commit();
        session.close();

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
        Message readMessage2 = em.merge(message2);
        User readUser3 = em.merge(user3);

        Recipient recipient = Recipient.builder()
                .id(null)
                .addedOn(LocalDate.now())
                .message(readMessage2)
                .userRoom(null)
                .user(readUser3)
                .build();

        em.persist(recipient);
        em.getTransaction().commit();

        Long rId = recipient.getId();

        em.getTransaction().begin();
        Recipient readRecipient = em.merge(recipient);
        em.remove(recipient);
        em.getTransaction().commit();

        //When
        recipientRepository.save(readRecipient);
        int count = recipientRepository.findAll().size();

        //Then
        Assert.assertEquals(4, count);

        //CleanUp
        em.getTransaction().begin();
        Recipient r = em.merge(readRecipient);
        em.remove(r);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testDeleteRecipientById() {
        //Given
        //@Before prepared data

        //When
        recipientRepository.deleteById(recipient2Id);
        int count = recipientRepository.findAll().size();

        //Then
        Assert.assertEquals(2, count);

    }

}
