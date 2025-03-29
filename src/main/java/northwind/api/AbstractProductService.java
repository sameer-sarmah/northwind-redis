package northwind.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import northwind.model.Product;
import northwind.service.ProductService;
import org.apache.commons.io.IOUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractProductService {

    protected List<Product> products = new CopyOnWriteArrayList<>();

    public abstract Optional<Product> getProduct(String productId);
    public abstract Product upsertProduct(Product product) ;
    public abstract void deleteProduct(String productId);

    protected List<Product>  getProducts() {
        if(!CollectionUtils.isEmpty(products)){
            return products;
        }
        else {
            InputStream inputStream = ProductService.class.getClassLoader().getResourceAsStream("products.json");
            if (inputStream != null) {
                try {
                    String json = IOUtils.toString(inputStream, Charset.defaultCharset());
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    TypeReference<List<Product>> typeRef = new TypeReference<List<Product>>() {
                    };
                    List<Product> products = objectMapper.readValue(json, typeRef);
                    synchronized (this) {
                        products.stream().forEach(product -> {
                            this.products.add(product);
                        });
                    }
                    return products;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return List.of();
        }

    }
}
