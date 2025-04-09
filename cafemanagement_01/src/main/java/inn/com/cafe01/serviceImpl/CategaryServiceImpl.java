package inn.com.cafe01.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import inn.com.cafe01.constants.CafeConstants;
import inn.com.cafe01.jwt.JwtFilter;
import inn.com.cafe01.pojo.Categary;
import inn.com.cafe01.repository.CategaryRepository;
import inn.com.cafe01.service.CategaryService;
import inn.com.cafe01.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategaryServiceImpl implements CategaryService {
	
	@Autowired
	CategaryRepository categaryRepository;
	
	@Autowired
	JwtFilter jwtFilter;

	@Override
	public ResponseEntity<String> addNewCategary(Map<String, String> requestMap) {
		try{
			if(jwtFilter.isAdmin()) {
				if(validateCategoryMap(requestMap,false)) {
					categaryRepository.save(getCategaryFromMap(requestMap,false));
					return CafeUtils.getResponseEntity("Categary added successfully  ", HttpStatus.OK);
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
		if(requestMap.containsKey("name")) {
			if(requestMap.containsKey("name") && validateId) {
				return true;
			}else if (!validateId){
				return true;
			}
		}
		return false;
	}
	private Categary getCategaryFromMap(Map<String,String> requestMap,Boolean isAdd) {
		Categary categary=new Categary();
		if(isAdd)
		{
			categary.setId(Integer.parseInt(requestMap.get("id")));
		}
		categary.setName(requestMap.get("name"));
		return categary;
}

	@Override
	public ResponseEntity<List<Categary>> getAllCategary(String filterValue) {
		try {
			if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
				log.info("inside the if condition");
				return new ResponseEntity<List<Categary>>(categaryRepository.getAllCategary(),HttpStatus.OK);
			}
			return new ResponseEntity<>(categaryRepository.findAll(),HttpStatus.OK);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateCategary(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try{
			if(jwtFilter.isAdmin()) {
				if(validateCategoryMap(requestMap,true)) {
					Optional optional=categaryRepository.findById(Integer.parseInt(requestMap.get("id")));
					if(!optional.isEmpty()) {
						categaryRepository.save(getCategaryFromMap(requestMap,true));
						return CafeUtils.getResponseEntity("Categary updated successfully ", HttpStatus.OK);
					}else {
						return CafeUtils.getResponseEntity("Categary id does not exist", HttpStatus.OK);
					}	
				}else {
					return CafeUtils.getResponseEntity(CafeConstants.INVAILD_DATA, HttpStatus.BAD_REQUEST);
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
