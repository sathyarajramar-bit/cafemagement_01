package inn.com.cafe01.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inn.com.cafe01.constants.CafeConstants;
import inn.com.cafe01.rest.UserRest;
import inn.com.cafe01.service.UserService;
import inn.com.cafe01.utils.CafeUtils;
import inn.com.cafe01.wrapper.UserWrapper;

@RestController
public class UserRestImpl implements UserRest {

	@Autowired
	UserService  userService;
	
	
	@Override
	public ResponseEntity<String> singUp(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			return userService.signUp(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@Override
    public ResponseEntity<String> login(Map<String,String> requestMap ){

      try {
    	  return userService.login(requestMap);
    	  
      }catch(Exception ex) {
    	  ex.printStackTrace();
      }
      return  null;
    }
	@Override
	public ResponseEntity<List<UserWrapper>> getAllUser() {
		try {
			
			return userService.getAllUser();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<String> update(Map<String,String> requestMap) {
        try {
			return userService.update(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
}
}