package inn.com.cafe01.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inn.com.cafe01.constants.CafeConstants;
import inn.com.cafe01.pojo.Categary;
import inn.com.cafe01.rest.CategatyRest;
import inn.com.cafe01.service.CategaryService;
import inn.com.cafe01.utils.CafeUtils;
@RestController
public class CategaryRestImpl implements CategatyRest {

	@Autowired
	CategaryService categaryService; 
	
	@Override
	public ResponseEntity<String> addNewCategary(Map<String, String> requestMap) {
		try {
			return categaryService.addNewCategary(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Categary>>  getAllCategary( String filterValue) {
		
		try {
			return categaryService.getAllCategary(filterValue);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateCategary(Map<String, String> requestMap) {
		try {
			return categaryService.updateCategary(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}

