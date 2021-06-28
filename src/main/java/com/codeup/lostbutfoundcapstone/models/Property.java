package com.codeup.lostbutfoundcapstone.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private Date date_found;

    @Column(nullable = false)
    private Date date_posted;

    @Column(nullable = false)
    private boolean inquiry_complete;

    @OneToOne
    private User user;

    @OneToOne
    private Location location;

    @Column(nullable = true)
    private boolean is_animal;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="property_categories",
            joinColumns={@JoinColumn(name = "post_id")},
            inverseJoinColumns={@JoinColumn(name="category_id")}
    )
    private List<PropertyCategory> categories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "property")
    private List<Inquiry> inquiries;

    public Property() {
    }

    public Property(String title, Date date_found, Date date_posted, boolean inquiry_complete, User user, Location location, boolean is_animal) {
        this.title = title;
        this.date_found = date_found;
        this.date_posted = date_posted;
        this.inquiry_complete = inquiry_complete;
        this.user = user;
        this.location = location;
        this.is_animal = is_animal;
    }

    public Property(long id, String title, Date date_found, Date date_posted, boolean inquiry_complete, User user, Location location, boolean is_animal) {
        this.id = id;
        this.title = title;
        this.date_found = date_found;
        this.date_posted = date_posted;
        this.inquiry_complete = inquiry_complete;
        this.user = user;
        this.location = location;
        this.is_animal = is_animal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate_found() {
        return date_found;
    }

    public void setDate_found(Date date_found) {
        this.date_found = date_found;
    }

    public Date getDate_posted() {
        return date_posted;
    }

    public void setDate_posted(Date date_posted) {
        this.date_posted = date_posted;
    }

    public boolean isInquiry_complete() {
        return inquiry_complete;
    }

    public void setInquiry_complete(boolean inquiry_complete) {
        this.inquiry_complete = inquiry_complete;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isIs_animal() {
        return is_animal;
    }

    public void setIs_animal(boolean is_animal) {
        this.is_animal = is_animal;
    }

    public List<PropertyCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<PropertyCategory> categories) {
        this.categories = categories;
    }

    public List<Inquiry> getInquiries() {
        return inquiries;
    }

    public void setInquiries(List<Inquiry> inquiries) {
        this.inquiries = inquiries;
    }

    public String getFormattedDateFound() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        return formatter.format(this.date_found);
    }
}
