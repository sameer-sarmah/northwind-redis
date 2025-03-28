package northwind.controller;

import java.lang.reflect.Type;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import northwind.client.ApacheHttpClient;
import northwind.exception.CoreException;
import northwind.model.Product;
import northwind.service.ProductService;
import northwind.util.HttpMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {
	final static Logger logger = LoggerFactory.getLogger(ProductsController.class);

	@Autowired
	private ProductService productService;

    @RequestMapping( value = "/products",method = RequestMethod.GET)
	public List<Product> getProducts(HttpServletRequest request) {
		return productService.getProducts();
	}

	@RequestMapping( value = "/product",method = RequestMethod.GET)
	public Optional<Product> getProduct(HttpServletRequest request,@RequestParam String productId) {
		return productService.getProduct(productId);
	}
}
