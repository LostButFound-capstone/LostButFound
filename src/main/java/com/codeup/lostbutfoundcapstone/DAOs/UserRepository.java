package com.codeup.lostbutfoundcapstone.DAOs;

import com.codeup.lostbutfoundcapstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByUsernameAndAdmin(String username, Boolean trueOrFalse);
}
