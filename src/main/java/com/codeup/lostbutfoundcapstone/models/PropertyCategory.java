package com.codeup.lostbutfoundcapstone.models;

import com.codeup.lostbutfoundcapstone.models.Property;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="categories")
public class PropertyCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String property_type;

    @ManyToMany(mappedBy = "categories")
    private List<Property> properties;

    public PropertyCategory() {
    }

    public PropertyCategory(String property_type, List<Property> properties) {
        this.property_type = property_type;
        this.properties = properties;
    }

    public PropertyCategory(long id, String property_type, List<Property> properties) {
        this.id = id;
        this.property_type = property_type;
        this.properties = properties;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProperty_type() {
        return property_type;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
