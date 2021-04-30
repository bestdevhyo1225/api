//package com.hyoseok.dynamicdatasource.test.unit.domain.product;
//
//import com.hyoseok.dynamicdatasource.domain.product.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//@SpringBootTest
//@Transactional
//public class ProductQueryRepositoryTest {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ProductQueryRepository productQueryRepository;
//
//    @Test
//    void test1() {
//        List<ProductImage> productImages = Arrays.asList(
//                new ProductImage("key1", "value1"),
//                new ProductImage("key2", "value2")
//        );
//
//        List<ProductDescription> productDescriptions = Arrays.asList(
//                new ProductDescription("name", "hello", 0),
//                new ProductDescription("nameEng", "안녕", 1),
//                new ProductDescription("banner", "bannerImages", 0),
//                new ProductDescription("contents", "내용", 1)
//        );
//
//        ProductGroup productGroup = new ProductGroup("code");
//
//        Product product = Product.create("name", 15_000, productGroup, productImages, productDescriptions);
//
//        productRepository.save(product);
//
//        entityManager.flush();
//        entityManager.clear();
//
//        Map<Product, List<ProductImage>> productsMap = productQueryRepository.findProductGroupById(product.getId());
//
//        System.out.println("productsMap size: " + productsMap.size());
//
//        for (Map.Entry<Product, List<ProductImage>> entry : productsMap.entrySet()) {
//            Product findProduct = entry.getKey();
//            ProductGroup findProducGroup = findProduct.getProductGroup();
//            List<ProductImage> findProductImages = entry.getValue();
//
//            System.out.println("product = " + findProduct);
//            System.out.println("productGroup = " + findProducGroup);
//            System.out.println("productImages = " + findProductImages);
//            System.out.println("productImages key = " + findProductImages.get(0).getKey());
//            System.out.println("productImages value = " + findProductImages.get(0).getValue());
//        }
//    }
//
//    @Test
//    void test2() {
//        List<ProductImage> productImages = Arrays.asList(
//                new ProductImage("profileImage", "value1"),
//                new ProductImage("mainImage", "value2")
//        );
//
//        List<ProductGroupDescription> productGroupDescriptions = Arrays.asList(
//                new ProductGroupDescription("name", "groupValueEng", 0),
//                new ProductGroupDescription("name", "groupValueKor", 1)
//        );
//
//        ProductGroup productGroup = ProductGroup.create("code", productGroupDescriptions);
//
//        List<ProductDescription> productDescriptions = Arrays.asList(
//                new ProductDescription("name", "hello", 0),
//                new ProductDescription("nameEng", "안녕", 1),
//                new ProductDescription("banner", "bannerImages", 0),
//                new ProductDescription("contents", "내용", 1)
//        );
//
//        Product product = Product.create("name", 15_000, productGroup, productImages, productDescriptions);
//
//        productRepository.save(product);
//
//        entityManager.flush();
//        entityManager.clear();
//
//        ProductJoinResult productJoinResult = productQueryRepository.findProductById(product.getId());
//
//        Product findProduct = productJoinResult.getProduct();
//        List<ProductGroupDescription> findProductGroupDescriptions = findProduct.getProductGroup().getProductGroupDescriptions();
//        String imageKeys = productJoinResult.getProductImageKeys();
//        String imageValues = productJoinResult.getProductImageValues();
//
//        String[] splitImageKeys = imageKeys.split(",");
//        String[] splitImageValues = imageValues.split(",");
//
//        System.out.println(findProduct);
//        System.out.println(findProductGroupDescriptions);
//        System.out.println(imageKeys);
//        System.out.println(splitImageKeys[0]);
//        System.out.println(splitImageValues[0]);
//    }
//
//    @Test
//    void test3() {
//        int size = 10;
//
//        for (int i = 0; i < size; i++) {
//            System.out.println("----- insert start : " + i + " -----");
//
//            List<ProductImage> productImages = Arrays.asList(
//                    new ProductImage("profileImage", "value1"),
//                    new ProductImage("mainImage", "value2")
//            );
//
//            List<ProductGroupDescription> productGroupDescriptions = Arrays.asList(
//                    new ProductGroupDescription("name", "groupValueEng", 0),
//                    new ProductGroupDescription("name", "groupValueKor", 1)
//            );
//
//            ProductGroup productGroup = ProductGroup.create("code", productGroupDescriptions);
//
//            List<ProductDescription> productDescriptions = Arrays.asList(
//                    new ProductDescription("name", "hello", 0),
//                    new ProductDescription("nameEng", "안녕", 1),
//                    new ProductDescription("banner", "bannerImages", 0),
//                    new ProductDescription("contents", "내용", 1)
//            );
//
//            Product product = Product.create("name" + i, 15_000, productGroup, productImages, productDescriptions);
//
//            productRepository.save(product);
//        }
//
//        entityManager.flush();
//        entityManager.clear();
//
//        List<ProductJoinResult> productJoinResults = productQueryRepository.findProducts(size);
//
////        System.out.println("productJoinResults size = " + productJoinResults.size());
//
//        for (ProductJoinResult productJoinResult : productJoinResults) {
//            Product findProduct = productJoinResult.getProduct();
//            List<ProductDescription> findProductDescriptions = findProduct.getProductDescriptions();
//
//            ProductGroup findProductGroup = findProduct.getProductGroup();
//            List<ProductGroupDescription> findProductGroupDescriptions = findProductGroup.getProductGroupDescriptions();
//
//            String imageKeys = productJoinResult.getProductImageKeys();
//            String imageValues = productJoinResult.getProductImageValues();
//
//            String[] splitImageKeys = imageKeys.split(",");
//            String[] splitImageValues = imageValues.split(",");
//
////            System.out.println(findProduct);
//            System.out.println(findProductGroup);
////            System.out.println(findProductDescriptions);
////            System.out.println(findProductGroupDescriptions);
//            System.out.println(imageKeys);
//            System.out.println(splitImageKeys[0]);
//            System.out.println(splitImageValues[0]);
//        }
//    }
//
//    @Test
//    void test4() {
//        Long lastId = 0L;
//        int size = 1000;
//
//        for (int i = 0; i < size; i++) {
//            System.out.println("----- insert start : " + i + " -----");
//
//            List<ProductImage> productImages = Arrays.asList(
//                    new ProductImage("profileImage", "value1"),
//                    new ProductImage("mainImage", "value2")
//            );
//
//            List<ProductGroupDescription> productGroupDescriptions = Arrays.asList(
//                    new ProductGroupDescription("name", "groupValueEng", 0),
//                    new ProductGroupDescription("name", "groupValueKor", 1)
//            );
//
//            ProductGroup productGroup = ProductGroup.create("code", productGroupDescriptions);
//
//            List<ProductDescription> productDescriptions = Arrays.asList(
//                    new ProductDescription("name", "hello", 0),
//                    new ProductDescription("nameEng", "안녕", 1),
//                    new ProductDescription("banner", "bannerImages", 0),
//                    new ProductDescription("contents", "내용", 1)
//            );
//
//            Product product = Product.create("name" + i, 15_000, productGroup, productImages, productDescriptions);
//
//            productRepository.save(product);
//        }
//
//        entityManager.flush();
//        entityManager.clear();
//
//        List<Product> findProducts = productQueryRepository.findProductsWithFetchInnerJoin(lastId, size);
//
//        for (Product findProduct : findProducts) {
//            List<ProductImage> findProductImages = findProduct.getProductImages();
//            List<ProductDescription> findProductDescriptions = findProduct.getProductDescriptions();
//
//            ProductGroup findProductGroup = findProduct.getProductGroup();
//            List<ProductGroupDescription> findProductGroupDescriptions = findProductGroup.getProductGroupDescriptions();
//
//            System.out.println(findProduct);
//            System.out.println(findProductImages);
//            System.out.println(findProductGroup);
//            System.out.println(findProductDescriptions);
//            System.out.println(findProductGroupDescriptions);
//        }
//    }
//}
