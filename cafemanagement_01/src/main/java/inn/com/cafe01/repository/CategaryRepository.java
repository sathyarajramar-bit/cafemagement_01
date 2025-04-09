package inn.com.cafe01.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import inn.com.cafe01.pojo.Categary;

public interface CategaryRepository extends JpaRepository<Categary, Integer> {
	
	List<Categary> getAllCategary();

}
