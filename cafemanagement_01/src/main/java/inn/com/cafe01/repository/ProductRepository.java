package inn.com.cafe01.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import inn.com.cafe01.pojo.Product;
import inn.com.cafe01.wrapper.ProductWrapper;

public interface ProductRepository  extends JpaRepository<Product, Integer>{

	List<ProductWrapper> getAllProduct();

}
