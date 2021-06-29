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

    @Query(value = "SELECT * FROM property p where p.date_found like %?1%", nativeQuery = true)
    List<Property> findPropertyByDate_found(String date);

    List<Property> findPropertyByCategoriesAndLocation(PropertyCategory category, Location location);

    @Query(value = "SELECT * FROM property p WHERE p.date_found LIKE %?1% AND ", nativeQuery = true)
    List<Property> findPropertyByCategoriesAndDate_found(PropertyCategory category, Date date);

    @Query(value = "SELECT * FROM property p WHERE p.date_found LIKE %?1% AND p.location_id = ?2", nativeQuery = true)
    List<Property> findPropertyByLocationAndDate_found(String date, String location_id);

    //    List<Property> findPropertyByCategoriesAndLocationAndDate_found(PropertyCategory category, Location location, Date date);
}
