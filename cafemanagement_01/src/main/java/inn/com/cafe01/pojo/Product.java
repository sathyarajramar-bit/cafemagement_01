package inn.com.cafe01.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

@NamedQuery(name="Product.getAllProduct",query="select new inn.com.cafe01.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.categary.id,p.categary.name) from Product p")

@NamedQuery(name="Product.UpdateProductDao" ,query="update Product p set p.status=:status where p.id=:id")

@NamedQuery(name="Product.getAProductByCategary", query=" select new inn.com.cafe01.wrapper.ProductWrapper(p.id,p.name) from Product p where p.categary.id=:id and p.status='true'")

@NamedQuery(name="Product.getProductById",query="select new inn.com.cafe01.wrapper.ProductWrapper(p.id,p.name,p.description,p.price) from Product p where p.id=:id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="product")
public class Product implements Serializable {

	public final Long serialVersionUid=123456L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_fk" ,nullable=false)
	private Categary categary;
	
	@Column(name="description")
	private String description; 
	
	@Column(name="price")
	private Integer price;
	
	@Column(name="status")
	private String status;
}
