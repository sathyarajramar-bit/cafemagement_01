package inn.com.cafe01.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import inn.com.cafe01.wrapper.ProductWrapper;

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

	@Override
	public ResponseEntity<List<ProductWrapper>> getAllProduct() {
		try {
			return new ResponseEntity<>(productRepository.getAllProduct(),HttpStatus.OK);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				if(validateProductMap(requestMap,true)) {
					Optional<Product> optional=productRepository.findById(Integer.parseInt(requestMap.get("id")));
					if(!optional.isEmpty()) {
						Product product=getProductFromMap(requestMap, true);
						product.setStatus(optional.get().getStatus());
						productRepository.save(product);
						return CafeUtils.getResponseEntity("Product updated successfully ",HttpStatus.OK);
					}	else {
						return CafeUtils.getResponseEntity("Product id does exits",HttpStatus.OK);
					}
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

	@Override
	public ResponseEntity<String> deleteProduct(Integer id) {
		try {
			if(jwtFilter.isAdmin()) {
			Optional optional=productRepository.findById(id);
			if(!optional.isEmpty()) {
				productRepository.deleteById(id);
				return CafeUtils.getResponseEntity("Product Deleted successfully ",HttpStatus.OK);
			}else {
				return CafeUtils.getResponseEntity("Product id does exits ",HttpStatus.OK);
			}				
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);	
			}		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				Optional optional=productRepository.findById(Integer.parseInt(requestMap.get("id")));
				if(!optional.isEmpty()) {
					productRepository.UpdateProductDao(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
					return CafeUtils.getResponseEntity("Product status successfully ",HttpStatus.OK);
				}else {
					return CafeUtils.getResponseEntity("Product id does exits ",HttpStatus.OK);	
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);	
			}			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<ProductWrapper>> getByCategary(Integer id) {
		try {
			return new ResponseEntity<>(productRepository.getAProductByCategary(id),HttpStatus.OK);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<ProductWrapper> getProductById(Integer id) {
		try {
			return new ResponseEntity<>(productRepository.getProductById(id),HttpStatus.OK);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
      }
