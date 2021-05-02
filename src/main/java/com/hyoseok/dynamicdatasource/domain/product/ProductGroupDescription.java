package com.hyoseok.dynamicdatasource.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString(exclude = "productGroup")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductGroupDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`key`", nullable = false, length = 20)
    private String key;

    @Column(nullable = false, length = 20)
    private String value;

    @Column(nullable = false)
    private int languageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_group_id")
    private ProductGroup productGroup;

    public ProductGroupDescription(String key, String value, int languageId) {
        this.key = key;
        this.value = value;
        this.languageId = languageId;
    }

    public void changeProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }
}
