package com.hyoseok.dynamicdatasource.usecase.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class ProductHandler {

    private final ProductQuery productQuery;
    private final ProductCommand productCommand;

    public ProductHandler(ProductQuery productQuery, ProductCommand productCommand) {
        this.productQuery = productQuery;
        this.productCommand = productCommand;
    }

    @Transactional
    public Long create() {
        System.out.println("[create] Transaction Name : " + TransactionSynchronizationManager.getCurrentTransactionName());
        return productCommand.createProduct();
    }

    @Transactional(readOnly = true)
    public void update() {
        System.out.println("[update] Transaction Name : " + TransactionSynchronizationManager.getCurrentTransactionName());
    }
}
