package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Group_define")
@Entity
public class Group_define extends Model {
	
        @Required
	@Column(name="Name", length=50, nullable=false, unique=true)
        public String Name;
	
	@Column(name="Pictire_active", nullable=false)
	public String Picture_active;

	@Column(name="Pictire_passive", nullable=false)
	public String Picture_passive;
	
	@Lob
    	@MaxSize(1000)
	@Column(name="Description")
	public String Description;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Group")
    public List<Group_Link> links;
  
 @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Group")
    public List<Group_Word> words;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Group")
    public List<Pets> pets;

   public String toString() {
     return Name;
}
      
    
    public Group_define(String Name, String Pic_act, String Pic_pas, String Descr) {
        this.links = new ArrayList<Group_Link>();
        this.Name = Name;
        this.Picture_active = Pic_act;
        this.Picture_passive = Pic_pas;
        this.Description = Descr;
        }

}
