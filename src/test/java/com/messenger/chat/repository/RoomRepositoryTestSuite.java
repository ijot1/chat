package com.messenger.chat.repository;

import com.messenger.chat.domain.Room;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomRepositoryTestSuite {

    @Autowired
    RoomRepository roomRepository;

    @Test
    public void testSaveRoom() {

        //Given
        Room room = Room.builder()
                .name("Silence Service")
                .build();

        //When
        Room r = roomRepository.save(room);
        String str = r.getName();
        Long id1 = r.getId();

        //Then
        Assert.assertEquals("Silence Service", str);

        //CleanUp
        roomRepository.deleteById(id1);
    }

    @Test
    public void testFindRooms() {

        //Given
        Room room1 = Room.builder()
                .name("Silence Service")
                .build();

        Room room2 = Room.builder()
                .name("Java Talks")
                .build();

        //When
        roomRepository.save(room1);
        roomRepository.save(room2);
        int count = roomRepository.findAll().size();
        Long id1 = room1.getId();
        Long id2 = room2.getId();

        //Then
        Assert.assertEquals(2, count);

        //CleanUp
        roomRepository.deleteById(id1);
        roomRepository.deleteById(id2);
    }

    @Test
    public void testFindRoomById() {

        //Given
        Room room1 = Room.builder()
                .name("Silence Service")
                .build();

        Room room2 = Room.builder()
                .name("Java Talks")
                .build();

        //When
        roomRepository.save(room1);
        roomRepository.save(room2);
        int count = roomRepository.findAll().size();
        Long id1 = room1.getId();
        Long id2 = room2.getId();
        Room readRoom = roomRepository.findById(id2).orElse(new Room("No such room"));

        //Then
        Assert.assertEquals(room2, readRoom);

        //CleanUp
        roomRepository.deleteById(id1);
        roomRepository.deleteById(id2);
    }
}
