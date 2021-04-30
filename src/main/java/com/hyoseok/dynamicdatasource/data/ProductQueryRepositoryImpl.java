package com.hyoseok.dynamicdatasource.data;

import com.hyoseok.dynamicdatasource.domain.product.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.hyoseok.dynamicdatasource.domain.product.QProduct.product;
import static com.hyoseok.dynamicdatasource.domain.product.QProductDescription.productDescription;
import static com.hyoseok.dynamicdatasource.domain.product.QProductGroup.productGroup;
import static com.hyoseok.dynamicdatasource.domain.product.QProductGroupDescription.productGroupDescription;
import static com.hyoseok.dynamicdatasource.domain.product.QProductImage.productImage;
import static com.querydsl.core.group.GroupBy.*;

@Repository
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ProductQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Map<Product, List<ProductImage>> findProductGroupById(Long id) {
        return queryFactory
                .from(product)
                .innerJoin(product.productImages, productImage)
                .innerJoin(product.productGroup, productGroup).fetchJoin()
                .where(productIdEq(id))
                .transform(
                        groupBy(product).as(list(productImage))
                );
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
}
