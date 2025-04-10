package inn.com.cafe01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inn.com.cafe01.pojo.Product;

public interface ProductRepository  extends JpaRepository<Product, Integer>{

}
