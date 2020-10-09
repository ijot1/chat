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
                .roomsName("Silence Service")
                .build();

        //When
        Room r = roomRepository.save(room);
        String str = r.getRoomsName();
        Long id1 = r.getId();

        //Then
        Assert.assertEquals("Silence Service", str);

        //CleanUp
        roomRepository.deleteById(id1);
    }

    @Test
    public void testGetRooms() {

        //Given
        Room room1 = Room.builder()
                .roomsName("Silence Service")
                .build();

        Room room2 = Room.builder()
                .roomsName("Java Talks")
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
}
