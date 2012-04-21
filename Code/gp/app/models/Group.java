package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Groups_description")
@Entity
public class Group extends Model {
	
	@Required
	@MaxSize(value=50)
	@Column(name="Name", length=50, nullable=false, unique=true)
	public String name;
	
	@Required
	@Column(name="Pictire_active", nullable=false)
	public String activeImage;

	@Required
	@Column(name="Pictire_passive", nullable=false)
	public String passiveImage;
	
	@Column(name="Description")
	public String description;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
	public List<AddedByAdmin> addedByAdmin;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
	public List<GroupLink> links;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
	public List<GroupWord> words;

	public String toString(){
		return this.name;
	}
    
	public Group(){};

	public Group(String name, String activeImage, String passiveImage, String description) {
	this.name = name;
	this.activeImage = activeImage;
	this.passiveImage = passiveImage;
	this.description = description;
	}
}
