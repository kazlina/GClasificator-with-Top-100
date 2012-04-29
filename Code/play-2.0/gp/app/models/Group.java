package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "GroupDescr")
public class Group extends Model {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	@Constraints.Required
	@Column(name = "name", length = 50, nullable = false, unique = true)
	public String name;

	@Constraints.Required
	@Column(name = "activeImage", nullable = false)
	public String activeImage;

	@Constraints.Required
	@Column(name = "passiveImage", nullable = false)
	public String passiveImage;

	@Column(name = "description")
	public String description;

	@Column(name = "textPercent")
	public Integer textPercent;

	@Column(name = "imagePercent")
	public Integer imagePercent;

	@Column(name = "linkPercent")
	public Integer linkPercent;

	@Column(name = "videoPercent")
	public Integer videoPercent;

	@OneToMany(mappedBy = "group")
	public List<AddedByAdmin> addedByAdmin;

	@OneToMany(mappedBy = "group")
	public List<GroupLink> links;

	@OneToMany(mappedBy = "group")
	public List<GroupWord> words;
/*
	public String toString(){
		return this.name;
	}

	public Group(String name, String activeImage, String passiveImage, String description, int textPercent, int imagePercent, int 			linkPercent, int videoPercent) {
	this.name = name;
	this.activeImage = activeImage;
	this.passiveImage = passiveImage;
	this.description = description;
	this.textPercent = textPercent;
	this.imagePercent = imagePercent;
	this.linkPercent = linkPercent;
	this.videoPercent = videoPercent;
	}
*/
	public static Model.Finder<Long, Group> find = new Model.Finder<Long, Group>(Long.class, Group.class);

	public static List<Group> all() {
		return find.all();
	}

	public static Group groupById(Long Id) {
		return find.ref(Id);
	}

	public static void create(Group group) {
		group.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
