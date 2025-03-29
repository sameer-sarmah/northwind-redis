package northwind.service;

import northwind.api.AbstractProductService;
import northwind.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedisProductService extends AbstractProductService {

    @Autowired
    private RedisTemplate <String, Product> redisTemplate;

    private static final String PRODUCT_CACHE = "products";

    final static Logger logger = LoggerFactory.getLogger(RedisProductService.class);

    @Override
    public Optional<Product> getProduct(String productId) {
        logger.info("Retrieving product with id={}",productId);
        String key = getKey(productId);
        if(redisTemplate.opsForValue().getOperations().hasKey(key)){
            Product product = redisTemplate.opsForValue().get(key);
            logger.info("Product not found in Cache");
            return Optional.of(product);
        } else {
            List<Product> products = getProducts();
            Optional<Product> retrievedProduct = products.stream().filter(product -> {
                return product.getProductID().equals(productId);
            }).findAny();
            if (retrievedProduct.isPresent()) {
                logger.info(retrievedProduct.get().toString());
            } else {
                logger.info("Product not found");
            }
            return retrievedProduct;
        }

    }

    @Override
    public Product upsertProduct(Product product) {
        List<Product> products = getProducts();
        products.removeIf(p -> p.getProductID().equals(product.getProductID()));
        products.add(product);
        String key = getKey(product.getProductID());
        redisTemplate.opsForValue().set(key,product);
        logger.info("Updated product with id={}", product.getProductID());
        return product;
    }

    @Override
    public void deleteProduct(String productId) {
        String key = getKey(productId);
        redisTemplate.opsForValue().getOperations().delete(key);
        logger.info("Deleted product with id={}", productId);
    }

    private String getKey(String productId){
        return PRODUCT_CACHE+"::"+productId;
    }
}
