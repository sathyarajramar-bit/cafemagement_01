package inn.com.cafe01.pojo;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="product")
public class Product {

}
