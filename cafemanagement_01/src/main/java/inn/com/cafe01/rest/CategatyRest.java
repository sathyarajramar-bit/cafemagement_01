package inn.com.cafe01.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import inn.com.cafe01.pojo.Categary;

@RequestMapping(path="/categary")
public interface CategatyRest {
	
	@PostMapping(path="/add")
	public ResponseEntity<String> addNewCategary(@RequestBody(required= true) Map<String,String> requestMap);
		
	@GetMapping(path="/all")
	public ResponseEntity<List<Categary>> getAllCategary(@RequestParam(required =false) String filterValue);
	
	@PostMapping(path="/update")
	public ResponseEntity<String> updateCategary(@RequestBody(required= true) Map<String,String> requestMap);
	

}
