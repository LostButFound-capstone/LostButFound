package com.codeup.lostbutfoundcapstone.models;

import javax.persistence.*;

@Entity
@Table(name="locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String location_name;

    @Column(nullable = false, length = 6)
    private int location_zip;

    public Location() {
    }

    public Location(String location_name, int location_zip) {
        this.location_name = location_name;
        this.location_zip = location_zip;
    }

    public Location(long id, String location_name, int location_zip) {
        this.id = id;
        this.location_name = location_name;
        this.location_zip = location_zip;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public int getLocation_zip() {
        return location_zip;
    }

    public void setLocation_zip(int location_zip) {
        this.location_zip = location_zip;
    }
}
