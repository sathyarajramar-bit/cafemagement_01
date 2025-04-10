package inn.com.cafe01.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import inn.com.cafe01.constants.CafeConstants;
import inn.com.cafe01.jwt.CustomerUserDetailsService;
import inn.com.cafe01.jwt.JwtFilter;
import inn.com.cafe01.jwt.JwtUtils;
import inn.com.cafe01.pojo.User;
import inn.com.cafe01.repository.UserRepository;
import inn.com.cafe01.service.UserService;
import inn.com.cafe01.utils.CafeUtils;
import inn.com.cafe01.utils.EmailUtils;
import inn.com.cafe01.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	CustomerUserDetailsService customerUserDetailsService;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired
	EmailUtils emailUtils;

	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		log.info("inside signUp {}  " + requestMap);
		try {
			if (validateSignUpMap(requestMap)) {
				User user = userRepository.findByEmailId(requestMap.get("email"));
				if (Objects.isNull(user)) {
					userRepository.save(getUserFromMap(requestMap));
					return CafeUtils.getResponseEntity("email registered  ", HttpStatus.OK);
				} else {
					return CafeUtils.getResponseEntity("email ID already exits ", HttpStatus.BAD_REQUEST);
				}

			} else {
				return CafeUtils.getResponseEntity(CafeConstants.INVAILD_DATA, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity("Some internal server error ", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateSignUpMap(Map<String, String> requestMap) {
		if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email")
				&& requestMap.containsKey("password")) {
			return true;
		} else {
			return false;
		}

	}

	private User getUserFromMap(Map<String, String> requestMap) {
		User user = new User();
		user.setName(requestMap.get("name"));
		user.setContactNumber(requestMap.get("contactNumber"));
		user.setEmail(requestMap.get("email"));
		user.setPassword(requestMap.get("password"));
		user.setRole("user");
		user.setStatus("false");
		return user;
	}

	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {
		log.info("inside login");
		try {
			org.springframework.security.core.Authentication auth= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
			if(auth.isAuthenticated()) {
				log.info("inside isAuthenticated");
				log.info("before isAuthenticated");
				if(customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
					log.info("inside customerUserDetailsService");
					return new ResponseEntity<String>("{\"token\":\""+jwtUtils.generateToken(customerUserDetailsService.getUserDetails().getEmail(),customerUserDetailsService.getUserDetails().getRole())+"\"}",HttpStatus.OK);
				}else {
					return new ResponseEntity<String>("{\"message\":\""+"wait for admin approvel."+"\"}",HttpStatus.BAD_REQUEST);
				}
				
			}
		}catch(Exception ex) {
			log.error("{}",ex);
			ex.printStackTrace();
		}
		return new ResponseEntity<String>("{\"message\":\""+"Bad credentials ."+"\"}",HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<List<UserWrapper>> getAllUser() {
		try {
			if(jwtFilter.isAdmin()) {
				return new ResponseEntity<List<UserWrapper>>(userRepository.getAllUser(),HttpStatus.OK);
			}else {
				return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				Optional<User> optional=userRepository.findById(Integer.parseInt(requestMap.get("id")));
				if(!optional.isEmpty()) {
					userRepository.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
					sendMailToAllAdmin(requestMap.get("status"),optional.get().getEmail(),userRepository.getAllAdmin());
					return CafeUtils.getResponseEntity("user id updated successfully", HttpStatus.OK);
				}
				else {
					return CafeUtils.getResponseEntity("User id doesnt exits",HttpStatus.OK);
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
		allAdmin.remove(jwtFilter.getCurrentUser());
		if(status !=null && status.equalsIgnoreCase("true")){
			emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved ", "User :-"+user+"\n is approved by\n ADMIN :-"+jwtFilter.getCurrentUser(),allAdmin);
	}else {
	     	emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account disable ", "User :-"+user+"\n is disable by\n ADMIN :-"+jwtFilter.getCurrentUser(),allAdmin);
	}
	}

	@Override
	public ResponseEntity<String> checkToken() {
		return CafeUtils.getResponseEntity("true",HttpStatus.OK);
}

	@Override
	public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
		try {
			User userObj=userRepository.findByEmail(jwtFilter.getCurrentUser());
			if(!userObj.equals(null)) {
				if(userObj.getPassword().equals(requestMap.get("oldPassword"))){
					userObj.setPassword(requestMap.get("newPassword"));
					userRepository.save(userObj);
					return CafeUtils.getResponseEntity("Password Updated successfully",HttpStatus.OK);
				}
				return CafeUtils.getResponseEntity("Incorrect Old Password",HttpStatus.BAD_REQUEST);
			     }	
			return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	

	
}

	@Override
	public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {

		return null;
	}
}