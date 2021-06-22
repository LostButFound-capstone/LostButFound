package com.codeup.lostbutfoundcapstone.DAOs;

import com.codeup.lostbutfoundcapstone.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyCategoryRepository extends JpaRepository <Property, Long>{

}
