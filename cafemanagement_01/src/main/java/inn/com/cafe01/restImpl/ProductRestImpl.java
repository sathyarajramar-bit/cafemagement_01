package inn.com.cafe01.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inn.com.cafe01.constants.CafeConstants;
import inn.com.cafe01.rest.ProductRest;
import inn.com.cafe01.service.ProductService;
import inn.com.cafe01.utils.CafeUtils;
import inn.com.cafe01.wrapper.ProductWrapper;

@RestController
public class ProductRestImpl implements ProductRest{

	@Autowired
	 ProductService productService;
	
	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		try {
			
			return productService.addNewProduct(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try {			
			return productService.getAllProduct();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	

	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
		try {			
			return productService.updateProduct(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

}
}
