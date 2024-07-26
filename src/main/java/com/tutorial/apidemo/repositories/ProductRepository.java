package com.tutorial.apidemo.repositories;

import com.tutorial.apidemo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
