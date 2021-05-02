package com.hyoseok.dynamicdatasource.data;

import com.hyoseok.dynamicdatasource.domain.product.*;
import com.querydsl.core.group.Group;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hyoseok.dynamicdatasource.domain.product.QProduct.product;
import static com.hyoseok.dynamicdatasource.domain.product.QProductDescription.productDescription;
import static com.hyoseok.dynamicdatasource.domain.product.QProductGroup.productGroup;
import static com.hyoseok.dynamicdatasource.domain.product.QProductImage.productImage;
import static com.querydsl.core.group.GroupBy.*;
import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final static long MAX_LIMIT_IMAGE_DESCRIPTION_COUNT = 4000;
    private final static long MAX_LIMIT_COUNT = 3000;
    private final JPAQueryFactory queryFactory;

    @Override
    public Map<Product, List<ProductImage>> findProductGroupById(Long lastId, int defaultLimitCount, int productImageRowCount) {
        long limitCount = (long) defaultLimitCount * productImageRowCount;

        if (limitCount > MAX_LIMIT_COUNT) {
            throw new IllegalArgumentException("defaultLimitCount * productImageRowCount 값이 " + MAX_LIMIT_COUNT + "을 넘었습니다.");
        }

        return queryFactory
                .from(product)
                .innerJoin(product.productGroup, productGroup).fetchJoin()
                .innerJoin(product.productImages, productImage)
                .where(
                        productIdGt(lastId),
                        productImageKeyEq("profileImage")
                )
                .limit(limitCount)
                .transform(groupBy(product).as(list(productImage)));
    }

    @Override
    public Map<Product, List<ProductDescription>> findProductGroupByIdV2(Long lastId, int defaultLimitCount, int productDescriptionRowCount) {
        long limitCount = (long) defaultLimitCount * productDescriptionRowCount;

        if (limitCount > MAX_LIMIT_COUNT) {
            throw new IllegalArgumentException("defaultLimitCount * productDescriptionRowCount 값이 " + MAX_LIMIT_COUNT + "을 넘었습니다.");
        }

        return queryFactory
                .from(product)
                .innerJoin(product.productGroup, productGroup).fetchJoin()
                .innerJoin(product.productDescriptions, productDescription)
                .where(
                        productIdGt(lastId),
                        productDescriptionKeyIn(asList("name", "nameEng", "banner"))
                )
                .limit(limitCount)
                .transform(groupBy(product).as(list(productDescription)));
    }

    @Override
    public Map<Product, Group> findProductGroupByIdV3(Long lastId, int defaultLimitCount, int rowCount) {
        long limitCount = (long) defaultLimitCount * rowCount;

        if (limitCount > MAX_LIMIT_IMAGE_DESCRIPTION_COUNT) {
            throw new IllegalArgumentException("defaultLimitCount * rowCount 값이 " + MAX_LIMIT_IMAGE_DESCRIPTION_COUNT + "을 넘었습니다.");
        }

        return queryFactory
                .from(product)
                .innerJoin(product.productGroup, productGroup).fetchJoin()
                .innerJoin(product.productDescriptions, productDescription)
                .innerJoin(product.productImages, productImage)
                .where(
                        productIdGt(lastId),
                        productDescriptionKeyIn(asList("name", "nameEng", "banner")),
                        productImageKeyEq("profileImage")
                )
                .limit(limitCount)
                .transform(
                        groupBy(product).as(
                                list(productDescription),
                                list(productImage)
                        )
                );
    }

    @Override
    public List<ProductAggregation> findProductsWithJoin(Long lastId, int defaultLimitCount, int rowCount) {
        long limitCount = (long) defaultLimitCount * rowCount;

        if (limitCount > MAX_LIMIT_IMAGE_DESCRIPTION_COUNT) {
            throw new IllegalArgumentException("defaultLimitCount * rowCount 값이 " + MAX_LIMIT_IMAGE_DESCRIPTION_COUNT + "을 넘었습니다.");
        }

        Map<Product, Group> productsMap = queryFactory
                .from(product)
                .innerJoin(product.productGroup, productGroup).fetchJoin()
                .innerJoin(product.productDescriptions, productDescription)
                .innerJoin(product.productImages, productImage)
                .where(
                        productIdGt(lastId),
                        productDescriptionKeyIn(asList("name", "nameEng", "banner")),
                        productImageKeyEq("profileImage")
                )
                .limit(limitCount)
                .transform(
                        groupBy(product).as(
                                list(productDescription),
                                list(productImage)
                        )
                );

        return productsMap.entrySet().stream()
                .map(entry -> new ProductAggregation(
                        entry.getKey(),
                        entry.getValue().getList(productDescription),
                        entry.getValue().getList(productImage)
                ))
                .collect(toList());
    }

    @Override
    public List<Product> findProductsWithFetchInnerJoin(Long lastId, int limit) {
        return queryFactory
                .selectFrom(product)
                .innerJoin(product.productDescriptions, productDescription).fetchJoin()
                .innerJoin(product.productGroup, productGroup).fetchJoin()
                .where(productIdGt(lastId))
                .limit(limit)
                .fetch();
    }

    @Override
    public List<ProductJoinResult> findProducts(int limit) {
        return queryFactory
                .select(
                        Projections.constructor(
                                ProductJoinResult.class,
                                product,
                                Expressions.stringTemplate("GROUP_CONCAT({0})", productImage.key),
                                Expressions.stringTemplate("GROUP_CONCAT({0})", productImage.value)
                        )
                )
                .from(product)
                .innerJoin(product.productImages, productImage)
                .innerJoin(product.productDescriptions, productDescription).fetchJoin()
                .innerJoin(product.productGroup, productGroup).fetchJoin()
                .where(
                        productIdGt(0L),
                        productImageKeyEq("profileImage")
                )
                .groupBy(product.id)
                .limit(limit)
                .fetch();
    }

    @Override
    public ProductJoinResult findProductById(Long id) {
        return queryFactory
                .select(
                        Projections.constructor(
                                ProductJoinResult.class,
                                product,
                                Expressions.stringTemplate("GROUP_CONCAT({0})", productImage.key),
                                Expressions.stringTemplate("GROUP_CONCAT({0})", productImage.value)
                        )
                )
                .from(product)
                .innerJoin(product.productImages, productImage)
                .innerJoin(product.productDescriptions, productDescription)
                .innerJoin(product.productGroup, productGroup)
                .where(
                        productIdEq(id),
                        productImageKeyEq("profileImage")
                )
                .groupBy(product.id)
                .fetchOne();
    }

    private BooleanExpression productIdGt(Long id) {
        return product.id.gt(id);
    }

    private BooleanExpression productIdEq(Long id) {
        return product.id.eq(id);
    }

    private BooleanExpression productImageKeyEq(String key) {
        return productImage.key.eq(key);
    }

    private BooleanExpression productDescriptionKeyIn(List<String> keys) {
        return productDescription.key.in(keys);
    }
}
