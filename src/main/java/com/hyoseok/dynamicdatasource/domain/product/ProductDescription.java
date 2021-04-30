package com.hyoseok.dynamicdatasource.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString(exclude = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDescription {

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
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductDescription(String key, String value, int languageId) {
        this.key = key;
        this.value = value;
        this.languageId = languageId;
    }

    public void changeProduct(Product product) {
        this.product = product;
    }
}
