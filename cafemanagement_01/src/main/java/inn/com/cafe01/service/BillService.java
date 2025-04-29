package inn.com.cafe01.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import inn.com.cafe01.pojo.Bill;

public interface BillService {

	ResponseEntity<String> generateReport(Map<String, Object> requestMap);

	ResponseEntity<List<Bill>> getBills();

	ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap);

}
