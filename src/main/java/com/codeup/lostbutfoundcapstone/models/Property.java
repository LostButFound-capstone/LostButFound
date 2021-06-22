package com.codeup.lostbutfoundcapstone.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date_found;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
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


}
