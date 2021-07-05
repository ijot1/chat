package com.messenger.chat.repository;


import com.messenger.chat.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByLoggedInTrue();

    @Query(nativeQuery = true)
    List<User> findFriends(@Param("id")Long id);

    @Modifying
    @Query(nativeQuery = true)
    void addUsersFriend(@Param("userId") Long userId,
                      @Param("friendId") Long friendId);

    @Modifying
    @Query(nativeQuery = true)
    void deleteUsersFriend(@Param("userId") Long userId,
                        @Param("friendId") Long friendId);

    @Override
    List<User> findAll();

    @Override
    Optional<User> findById(Long id);

    @Override
    User save(User user);

    @Override
    void deleteById(Long id);
}
