package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
@Table(name = "AddedByAdmin", uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"gpm", "groupDescr"
		})
	})
public class AddedByAdmin extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Constraints.Required
	@JoinColumn(name = "gpm", nullable = false)
	@ManyToOne
    public GPM gpm;

	@JoinColumn(name = "groupDescr", nullable = false)
	@ManyToOne
    public Group group;

	@Constraints.Min(1)
	@Constraints.Required
	@Column(name = "position", nullable = false)
	public Integer position;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd")
    @Column(name = "dateOfAddition", nullable = false)
	public Date dateOfAddition;

	@Formats.DateTime(pattern = "yyyy-MM-dd")
	@Column(name = "dateOfRemoval")
	public Date dateOfRemoval;

	@Constraints.MaxLength(value = 255, message = "max length = 255")
	@Column(name = "commentField")
	public String comment;

    public AddedByAdmin(GPM gpm, Group group, Integer position, Date dateOfAddition, Date dateOfRemoval, String comment) {
    	this.gpm = gpm;
    	this.group = group;
    	this.position = position;
        this.dateOfAddition = dateOfAddition;
        this.dateOfRemoval = dateOfRemoval;
        this.comment = comment;
    }
	
	public AddedByAdmin(String gpmName, Long groupId, Integer position, Date dateOfAddition, Date dateOfRemoval, String comment) {
		GPM findLink = GPM.findByIdGpm(gpmName);
		Group findGroup = Group.findById(groupId);
		if (findLink != null && findGroup != null) {
			this.group = findGroup;
			this.gpm = findLink;
	        this.position = position;
        	this.dateOfAddition = dateOfAddition;
        	this.dateOfRemoval = dateOfRemoval;
        	this.comment = comment;
		}
    }

	public String toString(){
		return this.gpm.toString();
	}

	private static Model.Finder<Long, AddedByAdmin> find = new Model.Finder<Long, AddedByAdmin>(Long.class, AddedByAdmin.class);

	public static List<AddedByAdmin> all() {
		return find.all();
	}

	public static AddedByAdmin findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}
	
	public static List<AddedByAdmin> findByGpm(Long Id) {
		return GPM.findById(Id).addedByAdmin;
	}
	
	public static List<AddedByAdmin> findByGroupId(Long Id) {
		return find.where().eq("group", Group.findById(Id)).orderBy("position").findList();
	}

	public static void add(AddedByAdmin element) {
		AddedByAdmin findGroupLink = find.where().eq("group", element.group).eq("gpm", element.gpm).findUnique();
		if (findGroupLink == null)
			element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
