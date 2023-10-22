package com.xyz.nearby.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class PriceHistory {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long priceHistoryID;
    @ManyToOne
    @JsonBackReference(value = "priceHistoryReference")
    @JoinColumn(name = "product_id")
    private Product product;
    private BigDecimal price;
    private Timestamp timestamp;

    public PriceHistory() {}

    public PriceHistory(Product product, BigDecimal price) {
        this.product = product;
        this.price = price;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public long getPriceHistoryID() {
        return priceHistoryID;
    }

    public void setPriceHistoryID(long priceHistoryID) {
        this.priceHistoryID = priceHistoryID;
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
