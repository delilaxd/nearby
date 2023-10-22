package com.xyz.nearby.controller;

import com.xyz.nearby.model.PriceHistory;
import com.xyz.nearby.model.Product;
import com.xyz.nearby.repository.CategoryRepository;
import com.xyz.nearby.repository.PriceHistoryRepository;
import com.xyz.nearby.repository.ProductRepository;
import com.xyz.nearby.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PriceHistoryRepository priceHistoryRepository;
    @Autowired
    ProductService productService;

    @GetMapping ("/products")
    public ResponseEntity<List<Product>> getAvailableProducts(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "sortBy", required = false) String sortBy) {
        try {
            List<Product> products = productService.getAvailableProducts(category, minPrice, maxPrice, sortBy);
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping ("/nearbyproducts")
    public ResponseEntity<List<Product>> getNearByProducts(@RequestParam double latitude, @RequestParam double longitude) {
        try {
            List<Product> products = productService.getNearByProducts(latitude, longitude);
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping ("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long productID) {
        try {
            Optional<Product> product = productRepository.findById(productID);
            Product productData = product.get();
            productData.setViews(productData.getViews().add(new BigInteger("1")));
            productRepository.save(productData);
            if (product.isPresent()) {
                return new ResponseEntity<>(productData, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct (@RequestBody Product product) {
        try {
            Product _product = productRepository.save(product);
            priceHistoryRepository.save(new PriceHistory(product, product.getPrice()));
            return new ResponseEntity<>(_product, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct (@PathVariable("id") long id, @RequestBody Product product){
        try {
            Optional<Product> productData = productRepository.findById(id);
            if (productData.isPresent()) {
                Product _product = productData.get();
                _product.setDescription(product.getDescription());
                _product.setDistance(product.getDistance());
                _product.setCategory(product.getCategory());
                _product.setName(product.getName());
                _product.setGpsCoordinates(product.getGpsCoordinates());
                _product.setImage(product.getImage());
                _product.setAvailable(product.isAvailable());
                if (!_product.getPrice().equals(product.getPrice())){
                    priceHistoryRepository.save(new PriceHistory(_product, product.getPrice()));
                }
                _product.setPrice(product.getPrice());
                return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
