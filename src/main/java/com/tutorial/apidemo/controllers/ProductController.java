package com.tutorial.apidemo.controllers;

import com.tutorial.apidemo.models.Product;
import com.tutorial.apidemo.models.ResponseObject;
import com.tutorial.apidemo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {

    //DI = Dependency Injection
    @Autowired
    private ProductRepository productRepository;


    @GetMapping("")
    List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //Get detail product
    @GetMapping("/{id}")
    //Return an object with: data, message, status
    ResponseEntity<ResponseObject> findProductById(@PathVariable Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.isPresent() ?
            ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Found", "Query product successfully found", foundProduct)
            ):
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Not found", "Cannot find product with id: " + id, "")
            );
    }

    //Insert new product - Postman: Raw, Json
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct){
        //2 Product must not have the same name
        List<Product> foundProduct = productRepository.findByProductName(newProduct.getProductName().trim());
        return foundProduct.size() > 0 ?
            ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
            new ResponseObject("FAILED", "Product name already exist", "")
        ):
            ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("OK", "Inserted product successfully", productRepository.save(newProduct))
        );
    }
}

