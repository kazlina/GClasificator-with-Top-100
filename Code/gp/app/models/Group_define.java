package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Group_define")
@Entity
public class Group_define extends Model {
	
	@Required
	@MaxSize(value=50)
	@Column(name="Name", length=50, nullable=false, unique=true)
	public String Name;
	
	@Required
	@URL
	@Column(name="Pictire_active", nullable=false)
	public String Picture_active;

	@Required
	@URL
	@Column(name="Pictire_passive", nullable=false)
	public String Picture_passive;
	
	@Column(name="Description")
	public String Description;


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Group")
    public List<Added_By_Admin> pets;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Group")
    public List<Group_Link> links;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Group")
    public List<Group_Word> words;
    
    public Group_define(String Name, String Pic_act, String Pic_pas, String Descr) {
        this.Name = Name;
        this.Picture_active = Pic_act;
        this.Picture_passive = Pic_pas;
        this.Description = Descr;
        }

}
