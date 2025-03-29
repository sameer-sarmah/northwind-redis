package northwind.controller;

import jakarta.servlet.http.HttpServletRequest;
import northwind.model.Product;
import northwind.service.ProductService;
import northwind.service.RedisProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProductsController {
	final static Logger logger = LoggerFactory.getLogger(ProductsController.class);

//	@Autowired
//	private RedisProductService productService;

	@Autowired
	private ProductService productService;

//    @RequestMapping( value = "/products",method = RequestMethod.GET)
//	public List<Product> getProducts(HttpServletRequest request) {
//		return productService.getProducts();
//	}

	@GetMapping( value = "/product/{productId}")
	public Optional<Product> getProduct(HttpServletRequest request,@PathVariable String productId) {
		return productService.getProduct(productId);
	}

	@PostMapping( value = "/product")
	public void createProduct(HttpServletRequest request, @RequestBody Product product) {
		 productService.upsertProduct(product);
	}

	@DeleteMapping( value = "/product/{productId}")
	public void deleteProduct(HttpServletRequest request, @PathVariable String productId) {
		productService.deleteProduct(productId);
	}


}
