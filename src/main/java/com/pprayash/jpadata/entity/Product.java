package com.pprayash.jpadata.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    private String productName;
    private double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.pprayash.jpadata.entity.Image> images = new ArrayList<>();

    public Product() {
    }

    public Product(long productId, String productName, double price, List<com.pprayash.jpadata.entity.Image> images) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        setImages(images);
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<com.pprayash.jpadata.entity.Image> getImages() {
        return images;
    }

    public void setImages(List<com.pprayash.jpadata.entity.Image> images) {
        this.images = images;
        for (com.pprayash.jpadata.entity.Image image : images) {
            image.setProduct(this);
        }
    }

    public void addImage(com.pprayash.jpadata.entity.Image image) {
        images.add(image);
        image.setProduct(this);
    }

    public void removeImage(com.pprayash.jpadata.entity.Image image) {
        images.remove(image);
        image.setProduct(null);
    }
}



