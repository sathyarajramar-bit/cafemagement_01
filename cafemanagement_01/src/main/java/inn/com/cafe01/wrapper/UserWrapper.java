package inn.com.cafe01.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {

	private Integer id;
	private String name;
	private String email;
	private String contacNumber;
	private String status;
	
	
	public UserWrapper(Integer id, String name, String email, String contacNumber, String status) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.contacNumber = contacNumber;
		this.status = status;
	}
	
	
}
