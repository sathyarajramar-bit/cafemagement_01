package inn.com.cafe01.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inn.com.cafe01.constants.CafeConstants;
import inn.com.cafe01.rest.BillRest;
import inn.com.cafe01.service.BillService;
import inn.com.cafe01.utils.CafeUtils;

@RestController
public class BillRestImpl implements BillRest{

	@Autowired
	BillService billService;
	
	
	@Override
	public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {		
		try {			
			return billService.generateReport(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
