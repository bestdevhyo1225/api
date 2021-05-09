package com.hyoseok.dynamicdatasource.web.http;


import com.hyoseok.dynamicdatasource.usecase.product.ProductHandler;
import com.hyoseok.dynamicdatasource.web.http.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductHandler productHandler;

    public ProductController(ProductHandler productHandler) {
        this.productHandler = productHandler;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> create() {
        Long productId = productHandler.create();
        return ResponseEntity.created(URI.create("/products/" + productId)).body(new SuccessResponse(productId));
    }

    @PatchMapping
    public ResponseEntity<SuccessResponse> update() {
        return ResponseEntity.ok(new SuccessResponse(null));
    }
}
