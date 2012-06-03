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

	@Constraints.MaxLength(50)
	@Constraints.Required
	@Constraints.Pattern("[a-zA-Z0-9\\s]+")
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

	@Constraints.Min(0)
	@Column(name = "textPercent")
	public Integer textPercent;

	@Constraints.Min(0)
	@Column(name = "imagePercent")
	public Integer imagePercent;

	@Constraints.Min(0)
	@Column(name = "linkPercent")
	public Integer linkPercent;

	@Constraints.Min(0)
	@Column(name = "videoPercent")
	public Integer videoPercent;
	
	@Constraints.Min(0)
	@Column(name = "audioPercent")
	public Integer audioPercent;

	@OneToMany(mappedBy = "group")
	public List<AddedByAdmin> addedByAdmin;

	@OneToMany(mappedBy = "group")
	public List<GroupLink> links;

	@OneToMany(mappedBy = "group")
	public List<GroupWord> words;

	public String toString(){
		return this.name;
	}

	public Group(String name, String activeImage, String passiveImage, String description, 
			int textPercent, int imagePercent, int linkPercent, int videoPercent, int audioPercent) {
		this.name = name;
		this.activeImage = activeImage;
		this.passiveImage = passiveImage;
		this.description = description;
		this.textPercent = textPercent;
		this.imagePercent = imagePercent;
		this.linkPercent = linkPercent;
		this.videoPercent = videoPercent;
		this.audioPercent = audioPercent;
	}

	private static Model.Finder<Long, Group> find = new Model.Finder<Long, Group>(Long.class, Group.class);

	public static List<Group> all() {
		return find.all();
	}

	public static Group findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}
	
	public static Group findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}

	public static void add(Group group) {
		Group findGroup = Group.findByName(group.name);
		if (findGroup == null)
			group.save();
	}
	
	public static void updateGroup(Long idGroup, Group group) {
		Group findGroup = Group.findById(idGroup);
		if (findGroup == null)
			return;
		
		findGroup.name = group.name;
		findGroup.activeImage = group.activeImage;
		findGroup.passiveImage = group.passiveImage;
		findGroup.description = group.description;
		findGroup.textPercent = group.textPercent;
		findGroup.imagePercent = group.imagePercent;
		findGroup.linkPercent = group.linkPercent;
		findGroup.videoPercent = group.videoPercent;
		findGroup.audioPercent = group.audioPercent;
		
		findGroup.update();
	}
	
	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
