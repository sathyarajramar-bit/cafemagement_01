package inn.com.cafe01.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import inn.com.cafe01.pojo.Product;
import inn.com.cafe01.wrapper.ProductWrapper;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Integer>{

	List<ProductWrapper> getAllProduct();

	@Modifying
	@Transactional
	Integer UpdateProductDao(@Param("status") String status, @Param("id") Integer id);

}
