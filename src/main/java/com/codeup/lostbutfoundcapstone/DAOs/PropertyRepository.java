package com.codeup.lostbutfoundcapstone.DAOs;

import com.codeup.lostbutfoundcapstone.models.Property;
import com.codeup.lostbutfoundcapstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findPropertyByUser(User user);
}
