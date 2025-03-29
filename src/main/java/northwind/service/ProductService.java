package northwind.service;

import northwind.api.AbstractProductService;
import northwind.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService extends AbstractProductService {

    final static Logger logger = LoggerFactory.getLogger(ProductService.class);
/*
    //@Cacheable
    public List<Product> getProducts(String categoryId){
        String url = "https://services.odata.org/Northwind/Northwind.svc/Products";
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("$format", "json");
        queryParams.put("$filter", "CategoryID eq 1");
        try {
            ApacheHttpClient httpClient = new ApacheHttpClient();
            String jsonResponse = httpClient.request(url, HttpMethod.GET, Collections.<String, String>emptyMap(), queryParams,
                    null);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            String productString = jsonNode.get("value").toPrettyString();
            logger.info(productString);
            TypeReference<List<Product>> typeRef = new TypeReference<List<Product>>() {};
            return objectMapper.readValue(productString, typeRef);
        } catch (CoreException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return List.of();
    }
*/
    @Override
    @Cacheable(value = "products", key="#productId")
    public Optional<Product> getProduct(String productId){
        logger.info("Retrieving product with id={}",productId);
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

    @Override
    @CachePut(value = "products", key = "#product.ProductID")
    public Product upsertProduct(Product product) {
        logger.info("Updating product with id={}", product.getProductID());
        List<Product> products = getProducts();
        products.removeIf(p -> p.getProductID().equals(product.getProductID()));
        products.add(product);
        return product;
    }

    @Override
    @CacheEvict(value = "products", key="#productId")
    public void deleteProduct(String productId) {
        logger.info("Deleting product with id={}", productId);
        List<Product> products = getProducts();
        products.removeIf(p -> p.getProductID().equals(productId));
    }




}
