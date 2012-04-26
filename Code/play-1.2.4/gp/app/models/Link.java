package models;

import java.util.*;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

@Table(name="Links_dictionary")
@Entity
public class Link extends Model {

	@Required
	@Column(name="Link", nullable=false, unique=true)
	public String link;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "link")
	public List<GroupLink> groupLink;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "link")
	public List<PostLink> postLinks;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "link")
	public List<ProfileLink> profileLinks;

	public String toString(){
		return this.link;
	}

	public Link(){};	    
    
	public Link(String link){
		this.link = link;
	}
}
