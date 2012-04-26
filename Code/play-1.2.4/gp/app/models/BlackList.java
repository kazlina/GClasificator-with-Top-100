package models;

import java.util.*;

import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Black_List")
@Entity
public class BlackList extends GenericModel {
 
	@Id
	@Required
	@JoinColumn(name="Id")
	@OneToOne
	public GPM gpm;
	
	@Required
	@InFuture
	@Column(name="Date", nullable=false)
	public Date dateOfAddition;

	public String toString(){
		return this.gpm.id;
	}
	
	public BlackList(){};
	
	public BlackList(GPM gpm) {
		this.gpm = gpm;
        this.dateOfAddition = Calendar.getInstance().getTime();;
    }

}
