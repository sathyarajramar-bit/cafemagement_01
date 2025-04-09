package inn.com.cafe01.jwt;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import inn.com.cafe01.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	private inn.com.cafe01.pojo.User userDetail;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		log.info("inside the loadUserByUsername   username  "+username);
		userDetail=userRepository.findByEmailId(username);
		if(!Objects.isNull(userDetail)) {
			return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>()); 
		}else {
			throw new UsernameNotFoundException("User not found.");
		}
	}
	
	public inn.com.cafe01.pojo.User getUserDetails(){
	//	inn.com.cafe01.pojo.User user =userDetail;
		return userDetail;
	}
	
	
	

}
