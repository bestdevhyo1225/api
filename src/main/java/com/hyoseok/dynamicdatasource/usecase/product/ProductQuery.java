package com.hyoseok.dynamicdatasource.usecase.product;

import com.hyoseok.dynamicdatasource.domain.product.Product;
import com.hyoseok.dynamicdatasource.domain.product.ProductQueryRepository;
import com.hyoseok.dynamicdatasource.domain.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class ProductQuery {

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;

    public ProductQuery(ProductRepository productRepository, ProductQueryRepository productQueryRepository) {
        this.productRepository = productRepository;
        this.productQueryRepository = productQueryRepository;
    }

    public Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product를 찾을 수 없습니다."));
    }
}
