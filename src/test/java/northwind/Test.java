package northwind;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import northwind.model.Product;
import northwind.service.ProductService;

import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        productService.getProduct("1");
    }
}
