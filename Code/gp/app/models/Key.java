package models;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;
import play.db.jpa.*;
import play.data.validation.*;

@Embeddable
public class Key implements Serializable {

	@Required
	@JoinColumn(name="Id")
	@ManyToOne
	public GPM Id;
	
	@Required
	@Column(name="Date")
    public Date date;
}
