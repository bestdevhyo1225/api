package com.hyoseok.dynamicdatasource.domain.product;

import java.util.List;
import java.util.Map;

public interface ProductQueryRepository {
    Map<Product, List<ProductImage>> findProductGroupById(Long id);
    List<Product> findProductsWithFetchInnerJoin(Long lastId, int limit);
    List<ProductJoinResult> findProducts(int limit);
    ProductJoinResult findProductById(Long id);
}
