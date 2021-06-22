package com.codeup.lostbutfoundcapstone.models;

import javax.persistence.*;

@Entity
@Table(name="inquiry_images")
public class InquiryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    public InquiryImage() {
    }

    public InquiryImage(String path, String description, Inquiry inquiry) {
        this.path = path;
        this.description = description;
        this.inquiry = inquiry;
    }

    public InquiryImage(long id, String path, String description, Inquiry inquiry) {
        this.id = id;
        this.path = path;
        this.description = description;
        this.inquiry = inquiry;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Inquiry getInquiry() {
        return inquiry;
    }

    public void setInquiry(Inquiry inquiry) {
        this.inquiry = inquiry;
    }
}
