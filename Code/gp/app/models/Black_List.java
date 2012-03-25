package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import java.io.Serializable;
import play.data.validation.*;

@Entity
public class Black_List extends GenericModel {
 
	@Id
	@JoinColumn(name="Id")
	@OneToOne
    public GPM GPM;
	
	public Date date;
	
    public Black_List(GPM GPM) {
        this.GPM = GPM;
        this.date = new Date();
        }

}
