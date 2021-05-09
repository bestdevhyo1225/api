package com.hyoseok.dynamicdatasource.usecase.product;

import com.hyoseok.dynamicdatasource.domain.product.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collections;
import java.util.List;

@Service
public class ProductCommand {

    private final ProductRepository productRepository;

    public ProductCommand(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Long createProduct() {
        System.out.println("[createProduct] Transaction Name : " + TransactionSynchronizationManager.getCurrentTransactionName());

        ProductGroup productGroup = new ProductGroup("code");
        List<ProductImage> productImages = Collections.singletonList(
                new ProductImage("productImageKey", "productImageValue")
        );
        List<ProductDescription> productDescriptions = Collections.singletonList(
                new ProductDescription("descKey", "descValue", 0)
        );
        Product product = Product.create("name", 15_000, productGroup, productImages, productDescriptions);

        productRepository.save(product);

        return product.getId();
    }

    @Transactional
    public void updateProduct() {
        System.out.println("[updateProduct] Transaction Name : " + TransactionSynchronizationManager.getCurrentTransactionName());


    }
}
