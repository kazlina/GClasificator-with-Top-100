package models;

import java.util.*;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

@Table(name="Link_dictionary")
@Entity
public class Link_dictionary extends Model {

	@Column(name="Word", nullable=false, unique=true)
	public String Link;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Link")
    public List<Group_Link> group_link;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Link")
    public List<Post_link> post_links;
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Link")
    public List<Profile_link> profile_links;
    */
    
    public String toString() {
    return Link;
   }
    public Link_dictionary(String Link){
        this.Link = Link;
        }

}
