package inn.com.cafe01.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import inn.com.cafe01.pojo.Bill;

public interface BillRepository extends JpaRepository<Bill,Integer> {

	List<Bill> getAllBills();
	
	List<Bill> getBillByName(@Param("username")String username );
}
