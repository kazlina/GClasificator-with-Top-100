package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Black_List")
@Entity
public class Black_List extends GenericModel {
 
	@Id
	@Required
	@JoinColumn(name="Id")
	@OneToOne
    public GPM GPM;
	
	@Required
	@InFuture
	@Column(name="Date", nullable=false)
	public Date date;
	
    public Black_List(GPM GPM) {
        this.GPM = GPM;
        this.date = new Date();
        }

}
