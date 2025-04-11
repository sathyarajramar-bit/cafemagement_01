package inn.com.cafe01.wrapper;

import lombok.Data;

@Data
public class ProductWrapper { 

	Integer id;
	String name;
	String description;
	Integer price;
	String status;
	Integer categaryId;
	String categaryName;
	public ProductWrapper() {	
	}
	public ProductWrapper(Integer id,String name,String description,Integer price,String status,Integer categaryId,String categaryName) {
		this.id=id;
		this.name=name;
		this.description=description;
		this.price=price;
		this.status=status;
		this.categaryId=categaryId;
		this.categaryName=categaryName;
		
	}
}
