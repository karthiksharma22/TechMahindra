package com.app.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idloc")
    private Long id;

    @Column(name = "hotelname", nullable = false, length = 45)
    private String hotelName;

    @Column(name = "hoteldesc", length = 45)
    private String hotelDesc;

    @Column(name = "location", nullable = false, length = 45)
    private String location;

    @Column(name = "totalcap", length = 45)
    private String totalCapacity;

    @Column(name = "imgUrl", nullable = false, length = 45)
    private String imgUrl;

    @Column(name = "category", nullable = false, length = 45)
    private String category;

    @Column(name = "totemp")
    private Integer totalEmployees;

    @Column(name = "count", columnDefinition = "INT DEFAULT 0")
    private Integer count = 0;

    @Column(name = "amenities", columnDefinition = "JSON", nullable = false)
    private String amenities; // Stored as JSON string

    @Column(name = "attractions", columnDefinition = "JSON", nullable = false)
    private String attractions; // Stored as JSON string

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public String getHotelDesc() { return hotelDesc; }
    public void setHotelDesc(String hotelDesc) { this.hotelDesc = hotelDesc; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getTotalCapacity() { return totalCapacity; }
    public void setTotalCapacity(String totalCapacity) { this.totalCapacity = totalCapacity; }

    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getTotalEmployees() { return totalEmployees; }
    public void setTotalEmployees(Integer totalEmployees) { this.totalEmployees = totalEmployees; }

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public String getAttractions() { return attractions; }
    public void setAttractions(String attractions) { this.attractions = attractions; }
}
