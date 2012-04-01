package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
//import java.io.Serializable;
//import play.data.validation.*;

@Entity
public class Group_def extends Model {
	
	public String Name;
	public String Picture;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Group")
    public List<Pets> pets;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Group")
    public List<Group_Link> links;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Group")
    public List<Group_Word> words;
    
    public Group_def(String Name, String Picture) {
        this.Name = Name;
        this.Picture = Picture;
        }

}
