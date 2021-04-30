package com.hyoseok.dynamicdatasource.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductJoinResult {
    private final Product product;
    private final String productImageKeys;
    private final String productImageValues;
}
