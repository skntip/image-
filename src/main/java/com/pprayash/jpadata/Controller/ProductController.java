package com.pprayash.jpadata.Controller;

import com.pprayash.jpadata.entity.Image;
import com.pprayash.jpadata.entity.Product;
import com.pprayash.jpadata.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("images") MultipartFile[] imageFiles) {

        Product product = new Product();
        product.setProductName(name);
        product.setPrice(price);

        for (MultipartFile file : imageFiles) {
            try {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                Image image = new Image();
                image.setFileName(fileName);
                image.setFileType(file.getContentType());
                product.addImage(image); // maintains both sides
            } catch (IOException e) {
                return new ResponseEntity<>("Image upload failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        productRepository.save(product);
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }
}



