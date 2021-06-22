package com.codeup.lostbutfoundcapstone.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="inquiries")
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition="TEXT")
    private String inquiry_description;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date_posted;

    @OneToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inquiry")
    private List<InquiryImage> images;

    public Inquiry() {
    }

    public Inquiry(String inquiry_description, Date date_posted, User user, List<InquiryImage> images) {
        this.inquiry_description = inquiry_description;
        this.date_posted = date_posted;
        this.user = user;
        this.images = images;
    }

    public Inquiry(long id, String inquiry_description, Date date_posted, User user, List<InquiryImage> images) {
        this.id = id;
        this.inquiry_description = inquiry_description;
        this.date_posted = date_posted;
        this.user = user;
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInquiry_description() {
        return inquiry_description;
    }

    public void setInquiry_description(String inquiry_description) {
        this.inquiry_description = inquiry_description;
    }

    public Date getDate_posted() {
        return date_posted;
    }

    public void setDate_posted(Date date_posted) {
        this.date_posted = date_posted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<InquiryImage> getImages() {
        return images;
    }

    public void setImages(List<InquiryImage> images) {
        this.images = images;
    }
}
