package inn.com.cafe01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inn.com.cafe01.pojo.Bill;

public interface BillRepository extends JpaRepository<Bill,Integer> {

}
