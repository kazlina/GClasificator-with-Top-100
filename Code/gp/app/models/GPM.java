package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="GPMs")
@Entity
public class GPM extends GenericModel {

	@Id
	@Required
	@MinSize(value=21)
	@MaxSize(value=21)
	@Match(value="^\\d{21}$", message="Incorrect identifer")
	@Column(name="Id", length=21)
	public String id;
  
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gpm")
	public List<Profile> profiles;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gpm")
	public List<Post> posts;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gpm")
	public List<AddedByAdmin> addedByAdmin;
    
	public String toString(){
		return this.id;
	}
	
	public GPM(){};
	    
	public GPM(String id) {
	 this.id = id;
	}
}
