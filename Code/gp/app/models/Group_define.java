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
	@Column(name="Pictire_active", nullable=false)
	public String Picture_active;

	@Required
	@Column(name="Pictire_passive", nullable=false)
	public String Picture_passive;

	@Column(name="Description")
	public String Description;

	@Range(min=0, max=100)
	@Column(name="Percent_text")
	public int Percent_text;

	@Range(min=0, max=100)
	@Column(name="Percent_image")
	public int Percent_image;

	@Range(min=0, max=100)
	@Column(name="Percent_link")
	public int Percent_link;

	@Range(min=0, max=100)
	@Column(name="Percent_video")
	public int Percent_video;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Group")
	public List<Added_By_Admin> pets;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Group")
	public List<Group_Link> links;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Group")
	public List<Group_Word> words;

	public String toString(){
		return this.Name;
	}

	public Group_define(){};

	public Group_define(String Name, String Pic_act, String Pic_pas, String Descr) {
	this.Name = Name;
	this.Picture_active = Pic_act;
	this.Picture_passive = Pic_pas;
	this.Description = Descr;
	}
}
