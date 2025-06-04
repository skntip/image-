package com.pprayash.jpadata.repository;

import com.pprayash.jpadata.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}



