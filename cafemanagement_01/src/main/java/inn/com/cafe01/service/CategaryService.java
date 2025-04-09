package inn.com.cafe01.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import inn.com.cafe01.pojo.Categary;

public interface CategaryService {

	ResponseEntity<String> addNewCategary(Map<String, String> requestMap);

	ResponseEntity<List<Categary>> getAllCategary(String filterValue);

	ResponseEntity<String> updateCategary(Map<String, String> requestMap);

}
