package com.hyoseok.dynamicdatasource.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString(exclude = {"productGroup", "productDescriptions", "productImages"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_group_id")
    private ProductGroup productGroup;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private final List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private final List<ProductDescription> productDescriptions = new ArrayList<>();

    public Product(String name, int price, ProductGroup productGroup) {
        this.name = name;
        this.price = price;
        this.productGroup = productGroup;
    }

    public void addProductImage(ProductImage productImage) {
        this.productImages.add(productImage);
        productImage.changeProduct(this);
    }

    public void addProductDescription(ProductDescription productDescription) {
        this.productDescriptions.add(productDescription);
        productDescription.changeProduct(this);
    }

    public static Product create(String name, int price,
                                 ProductGroup productGroup,
                                 List<ProductImage> productImages,
                                 List<ProductDescription> productDescriptions) {
        Product product = new Product(name, price, productGroup);

        productImages.forEach(product::addProductImage);
        productDescriptions.forEach(product::addProductDescription);

        return product;
    }
}
