package com.codeup.lostbutfoundcapstone.DAOs;

import com.codeup.lostbutfoundcapstone.models.Location;
import com.codeup.lostbutfoundcapstone.models.Property;
import com.codeup.lostbutfoundcapstone.models.PropertyCategory;
import com.codeup.lostbutfoundcapstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Date;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findPropertyByUser(User user);
    List<Property> findPropertyByCategories(PropertyCategory category);
    List<Property> findPropertyByLocation(Location location);
    @Query("select p from Property p where p.date_found <= :date")
    List<Property> findPropertyByDate_found(@Param("date") Date date);
//    List<Property> findPropertyByDate_found(Date date);
    List<Property> findPropertyByCategoriesAndLocation(PropertyCategory category, Location location);
//    List<Property> findPropertyByCategoriesAndDate_found(PropertyCategory category, Date date);
//    List<Property> findPropertyByLocationAndDate_found(Location location, Date date);
//    List<Property> findPropertyByCategoriesAndLocationAndDate_found(PropertyCategory category, Location location, Date date);
}
