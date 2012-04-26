package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Profile_links")
@Entity
public class ProfileLink extends GenericModel {

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
    public Link link;
    
    @Required
    @Min(1)
	@Column(name="Amount", nullable=false)
    public int amount;
    
    public ProfileLink(){};
    
    public ProfileLink(Profile profile, Link link, int amount){
        this.profile = profile;
        this.link = link;
        this.amount = amount;
        }

}
