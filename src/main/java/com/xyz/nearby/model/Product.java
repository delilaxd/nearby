package com.xyz.nearby.model;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long productID;
    private String name;
    private String description;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "category_id")
    private Category category;
    private BigDecimal price;
    @Embedded
    private GPSCoordinates gpsCoordinates;
    private BigInteger views;
    private String image;
    @Transient
    private double distance;
    private boolean available;

    @JsonManagedReference(value = "priceHistoryReference")
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriceHistory> priceHistoryList;

    public Product () {}

    public Product(String name, String description, Category category, BigDecimal price, GPSCoordinates gpsCoordinates, String image, boolean available) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.gpsCoordinates = gpsCoordinates;
        this.views = BigInteger.ZERO;
        this.image = image;
        this.available = available;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public GPSCoordinates getGpsCoordinates() {
        return gpsCoordinates;
    }

    public void setGpsCoordinates(GPSCoordinates gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }

    public BigInteger getViews() {
        return views;
    }

    public void setViews(BigInteger views) {
        this.views = views;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<PriceHistory> getPriceHistoryList() {
        return priceHistoryList;
    }

    public void setPriceHistoryList(List<PriceHistory> priceHistoryList) {
        this.priceHistoryList = priceHistoryList;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
