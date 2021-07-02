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
    // Single parameter query
    @Query(value = "SELECT * FROM p property where p.title like %?1%", nativeQuery = true)
    List<Property> findPropertyByTitle(String title);

    List<Property> findPropertyByUser(User user);

    List<Property> findPropertyByCategories(PropertyCategory category);

    List<Property> findPropertyByLocation(Location location);

    @Query(value = "SELECT * FROM property p where p.date_found like %?1%", nativeQuery = true)
    List<Property> findPropertyByDate_found(String date);

    // Double parameter query
    List<Property> findPropertyByCategoriesAndLocation(PropertyCategory category, Location location);

    @Query(value = "SELECT * FROM property p JOIN property_categories ON property_categories.post_id = p.id JOIN categories ON categories.id = property_categories.category_id JOIN locations ON locations.id = p.location_id WHERE categories.property_type LIKE %?1% OR locations.location_name LIKE %?2%", nativeQuery = true)
    List<Property> findPropertyByCategoriesIsLikeOrLocationIsLike(String category, String location);

    @Query(value = "SELECT * FROM property JOIN property_categories ON property_categories.post_id = property.id WHERE property_categories.category_id = ?1 AND property.date_found LIKE %?2%", nativeQuery = true)
    List<Property> findPropertyByCategoriesAndDate_found(String category, String date);

    @Query(value = "SELECT * FROM property p WHERE p.date_found LIKE %?1% AND p.location_id = ?2", nativeQuery = true)
    List<Property> findPropertyByLocationAndDate_found(String date, String location_id);

    // Triple parameter query
    @Query(value = "SELECT * FROM property JOIN property_categories ON property_categories.post_id = property.id WHERE property_categories.category_id = ?1 AND property.date_found LIKE %?2% AND property.location_id = ?3", nativeQuery = true)
    List<Property> findPropertyByCategoriesAndLocationAndDate_found(String category, String date, String location);
}
