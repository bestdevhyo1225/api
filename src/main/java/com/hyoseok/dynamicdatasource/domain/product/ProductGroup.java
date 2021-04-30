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
@ToString(exclude = "productGroupDescriptions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String code;

    @OneToMany(mappedBy = "productGroup", cascade = CascadeType.ALL)
    private final List<ProductGroupDescription> productGroupDescriptions = new ArrayList<>();

    public ProductGroup(String code) {
        this.code = code;
    }

    public void addProductGroupDescription(ProductGroupDescription productGroupDescription) {
        this.productGroupDescriptions.add(productGroupDescription);
        productGroupDescription.changeProductGroup(this);
    }

    public static ProductGroup create(String code, List<ProductGroupDescription> productGroupDescriptions) {
        ProductGroup productGroup = new ProductGroup(code);

        productGroupDescriptions.forEach(productGroup::addProductGroupDescription);

        return productGroup;
    }
}
