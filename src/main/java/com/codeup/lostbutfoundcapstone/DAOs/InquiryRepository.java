package com.codeup.lostbutfoundcapstone.DAOs;


import com.codeup.lostbutfoundcapstone.models.Inquiry;
import com.codeup.lostbutfoundcapstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findInquiryByUser(User user);
}
