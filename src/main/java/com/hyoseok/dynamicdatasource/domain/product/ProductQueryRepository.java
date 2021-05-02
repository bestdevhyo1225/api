package com.hyoseok.dynamicdatasource.domain.product;

import java.util.List;
import java.util.Map;

public interface ProductQueryRepository {
    Map<Product, List<ProductImage>> findProductGroupById(Long lastId, int defaultLimitCount, int productImageRowCount);

    Map<Product, List<ProductDescription>> findProductGroupByIdV2(Long lastId, int defaultLimitCount, int productDescriptionRowCount);

    List<Product> findProductsWithFetchInnerJoin(Long lastId, int limit);

    List<ProductJoinResult> findProducts(int limit);

    ProductJoinResult findProductById(Long id);
}
