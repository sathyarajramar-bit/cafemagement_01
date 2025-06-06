package inn.com.cafe01.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import inn.com.cafe01.wrapper.UserWrapper;

public interface UserService {

	ResponseEntity<String> signUp(Map<String, String> requestMap);

	ResponseEntity<String> login(Map<String, String> requestMap);

	ResponseEntity<List<UserWrapper>> getAllUser();

	ResponseEntity<String> update(Map<String,String> requestMap);

	ResponseEntity<String> checkToken();

	ResponseEntity<String> changePassword(Map<String, String> requestMap);

	ResponseEntity<String> forgotPassword(Map<String, String> requestMap);

}
