package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Profile_link")
@Entity
public class Profile_link extends GenericModel {

	@Id
	//@JoinColumn(name="Id_GPM")
    @ManyToOne
    /*@JoinColumns({
    	@JoinColumn(name="Id_GPM", referencedColumnName="Id"),
    	@JoinColumn(name="Date", referencedColumnName="Date")
    })*/ 
    public Profile profile;
    
    @Id
	@JoinColumn(name="Id_Link")
    @ManyToOne
    public Link_dictionary Id_Link;
    
    @Required
    @Min(1)
	@Column(name="Count", nullable=false)
    public int Count;
    
    public Profile_link(Profile Id, Link_dictionary Id_Link, int Count){
        this.profile = Id;
        this.Id_Link = Id_Link;
        this.Count = Count;
        }

}
