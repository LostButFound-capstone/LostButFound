package com.codeup.lostbutfoundcapstone.DAOs;


import com.codeup.lostbutfoundcapstone.models.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
