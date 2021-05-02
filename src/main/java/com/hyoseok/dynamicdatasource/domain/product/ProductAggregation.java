package com.hyoseok.dynamicdatasource.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductAggregation {
    private final Product product;
    private final List<ProductDescription> productDescriptions;
    private final List<ProductImage> productImages;
}
