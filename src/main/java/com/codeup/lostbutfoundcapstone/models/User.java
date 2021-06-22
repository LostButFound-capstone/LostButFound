package com.codeup.lostbutfoundcapstone.models;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean admin;

    @Column(nullable = false)
    private boolean verified;

    @Column(nullable = true)
    private String profile_image_path;

    public User() {
    }

    //Insert Constructor
    public User(String email, String username, String password, boolean admin, boolean verified, String profile_image_path) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.verified = verified;
        this.profile_image_path = profile_image_path;
    }

    //Update Constructor
    public User(long id, String email, String username, String password, boolean admin, boolean verified, String profile_image_path) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.verified = verified;
        this.profile_image_path = profile_image_path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getProfile_image_path() {
        return profile_image_path;
    }

    public void setProfile_image_path(String profile_image_path) {
        this.profile_image_path = profile_image_path;
    }
}
