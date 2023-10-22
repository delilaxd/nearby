package com.xyz.nearby.service;

import com.xyz.nearby.model.Product;
import com.xyz.nearby.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAvailableProducts(String category, Double minPrice, Double maxPrice, String sortBy) {

        List<Product> products = productRepository.findAvailableProducts(category, minPrice, maxPrice, sortBy);
        return products;
    }

    public List<Product> getNearByProducts(double latitude, double longitude) {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        products.forEach(product -> {
            double distance = calculateDistance(
                    latitude,
                    longitude,
                    product.getGpsCoordinates().getLatitude(),
                    product.getGpsCoordinates().getLongitude()
            );
            product.setDistance(distance);
        });
        products.sort(Comparator.comparing(Product::getDistance));
        return products;
    }


    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Radius of the Earth in kilometers
        double earthRadius = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }
}
