package inn.com.cafe01.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import inn.com.cafe01.constants.CafeConstants;
import inn.com.cafe01.jwt.JwtFilter;
import inn.com.cafe01.pojo.Categary;
import inn.com.cafe01.pojo.Product;
import inn.com.cafe01.repository.ProductRepository;
import inn.com.cafe01.service.ProductService;
import inn.com.cafe01.utils.CafeUtils;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
       try {
			if(jwtFilter.isAdmin()) {
				if(validateProductMap(requestMap,false)) {
					productRepository.save(getProductFromMap(requestMap,false));
					return CafeUtils.getResponseEntity("Product added successfully ",HttpStatus.OK);
				}else {
					return CafeUtils.getResponseEntity(CafeConstants.INVAILD_DATA,HttpStatus.BAD_REQUEST);
				}
				
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	  }

	private boolean validateProductMap(Map<String, String> requestMap,boolean validate) {
		if(requestMap.containsKey("name")) {
			if(requestMap.containsKey("id") && validate) {
				return true;
			}else if(! validate) {
				return true;
			}
		}
		return false;
	  }

	private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
		Categary categary=new Categary(); 
		categary.setId(Integer.parseInt(requestMap.get("categaryId")));
		Product product=new Product();
		if(isAdd) {
			product.setId(Integer.parseInt(requestMap.get("id")));
		}else {
			product.setStatus("true");
		}
		product.setName(requestMap.get("name"));
		product.setDescription(requestMap.get("description"));
		product.setPrice(Integer.parseInt(requestMap.get("price")));
		product.setCategary(categary);
		return product;
	}
      }
