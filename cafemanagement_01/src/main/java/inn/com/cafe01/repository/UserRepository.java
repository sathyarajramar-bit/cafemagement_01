package inn.com.cafe01.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import inn.com.cafe01.pojo.User;
import inn.com.cafe01.wrapper.UserWrapper;

@Repository
public interface UserRepository extends  JpaRepository<User, Integer> {

	User findByEmailId(@Param("email") String email) ;
		
	List<UserWrapper> getAllUser();
	List<String> getAllAdmin();
	
	
	@Transactional
	@Modifying
	Integer updateStatus(@Param("status") String status,@Param("id")Integer id);
	
	
	
}
