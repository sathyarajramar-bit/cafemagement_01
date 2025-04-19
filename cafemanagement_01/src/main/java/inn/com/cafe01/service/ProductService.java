package inn.com.cafe01.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import inn.com.cafe01.wrapper.ProductWrapper;

public interface ProductService {

	ResponseEntity<String> addNewProduct(Map<String, String> requestMap);

	ResponseEntity<List<ProductWrapper>> getAllProduct();

	ResponseEntity<String> updateProduct(Map<String, String> requestMap);

	ResponseEntity<String> deleteProduct(Integer id);

	ResponseEntity<String> updateStatus(Map<String, String> requestMap);

	ResponseEntity<List<ProductWrapper>> getByCategary(Integer id);

	ResponseEntity<ProductWrapper> getProductById(Integer id);

}
