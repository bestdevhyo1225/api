//package com.hyoseok.dynamicdatasource.test.unit.domain.product;
//
//import com.hyoseok.dynamicdatasource.domain.product.*;
//import com.querydsl.core.group.Group;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//import static com.hyoseok.dynamicdatasource.domain.product.QProductDescription.productDescription;
//import static com.hyoseok.dynamicdatasource.domain.product.QProductImage.*;
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
//    void test2() {
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
//    void test3() {
//        Long lastId = 0L;
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
//
//    @Test
//    void test4() {
//        int size = 1000;
//
//        for (int i = 0; i < size; i++) {
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
//        Long lastId = 0L;
//        int productDescriptionRowCount = 3;
//        int defaultLimitCount = 1000;
//
//        Map<Product, List<ProductDescription>> productsMap =
//                productQueryRepository.findProductGroupByIdV2(lastId, defaultLimitCount, productDescriptionRowCount);
//
//        System.out.println("productMap values size = " + productsMap.values().size());
//
//        long startTime = System.currentTimeMillis();
//
//        for (Map.Entry<Product, List<ProductDescription>> entry : productsMap.entrySet()) {
//            Product product = entry.getKey();
//            ProductGroup productGroup = product.getProductGroup();
//            List<ProductGroupDescription> productGroupDescriptions = productGroup.getProductGroupDescriptions();
//            List<ProductImage> productImages = product.getProductImages();
//            List<ProductDescription> productDescriptions = entry.getValue();
//
//            System.out.println(product);
//            System.out.println(productImages);
//            System.out.println(productGroup);
//            System.out.println(productDescriptions);
//            System.out.println(productGroupDescriptions);
//        }
//
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("execution time = " + (endTime - startTime));
//    }
//
//    @Test
//    void test5() {
//        int size = 1000;
//
//        for (int i = 0; i < size; i++) {
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
//        Long lastId = 0L;
//        int productImageRowCount = 1;
//        int defaultLimitCount = 1000;
//
//        Map<Product, List<ProductImage>> productsMap =
//                productQueryRepository.findProductGroupById(lastId, defaultLimitCount, productImageRowCount);
//
//        System.out.println("productMap values size = " + productsMap.values().size());
//
//        long startTime = System.currentTimeMillis();
//
//        for (Map.Entry<Product, List<ProductImage>> entry : productsMap.entrySet()) {
//            Product product = entry.getKey();
//            ProductGroup productGroup = product.getProductGroup();
//            List<ProductGroupDescription> productGroupDescriptions = productGroup.getProductGroupDescriptions();
//            List<ProductDescription> productImages = product.getProductDescriptions();
//            List<ProductImage> productDescriptions = entry.getValue();
//
//            System.out.println(product);
//            System.out.println(productImages);
//            System.out.println(productGroup);
//            System.out.println(productDescriptions);
//            System.out.println(productGroupDescriptions);
//        }
//
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("execution time = " + (endTime - startTime));
//    }
//
//    @Test
//    void test6() {
//        int size = 1000;
//
//        for (int i = 0; i < size; i++) {
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
//        Long lastId = 0L;
//        int rowCount = 4;
//        int defaultLimitCount = 1000;
//
//        System.out.println("[1] Max Memory : " + Runtime.getRuntime().maxMemory());
//        System.out.println("[1] Total Memory : " + Runtime.getRuntime().totalMemory());
//        System.out.println("[1] Used Memory : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
//
//        Map<Product, Group> productsMap = productQueryRepository.findProductGroupByIdV3(lastId, defaultLimitCount, rowCount);
//
//        System.out.println("[2] Max Memory : " + Runtime.getRuntime().maxMemory());
//        System.out.println("[2] Total Memory : " + Runtime.getRuntime().totalMemory());
//        System.out.println("[2] Used Memory : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
//
//        System.out.println("productMap values size = " + productsMap.values().size());
//
//        long startTime = System.currentTimeMillis();
//
//        for (Map.Entry<Product, Group> entry : productsMap.entrySet()) {
//            Product product = entry.getKey();
//            ProductGroup productGroup = product.getProductGroup();
//            List<ProductGroupDescription> productGroupDescriptions = productGroup.getProductGroupDescriptions();
//            List<ProductDescription> productDescriptions = entry.getValue().getList(productDescription);
//            List<ProductImage> productImages = entry.getValue().getList(productImage);
//
//            System.out.println("------- product ------");
//            System.out.println(product);
//            System.out.println(productGroup);
//            System.out.println(productGroupDescriptions);
//            System.out.println(productDescriptions);
//            System.out.println(productImages);
//            System.out.println("----------------------");
//        }
//
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("execution time = " + (endTime - startTime));
//    }
//}
