package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
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

	@Constraints.Required
	@JoinColumn(name = "groupDescr", nullable = false)
	@ManyToOne
    public Group group;

	@Constraints.Required
	@Column(name = "position", nullable = false)
	public Integer position;

    @Constraints.Required
    @Column(name = "dateOfAddition", nullable = false)
	public Date dateOfAddition;

	@Column(name = "dateOfRemoval")
	public Date dateOfRemoval;

	@Column(name = "commentField")
	public String comment;
/*
	public AddedByAdmin(GPM gpm, Group group, int position, Date dateOfAddition, Date dateOfRemoval, String comment) {
        this.gpm = gpm;
        this.group = group;
        this.position = position;
        this.dateOfAddition = dateOfAddition;
        this.dateOfRemoval = dateOfRemoval;
        this.comment = comment;
        }
*/
	public static Model.Finder<Long, AddedByAdmin> find = new Model.Finder<Long, AddedByAdmin>(Long.class, AddedByAdmin.class);

	public static List<AddedByAdmin> all() {
		return find.all();
	}

	public static AddedByAdmin addedByAdminById(Long Id) {
		return find.ref(Id);
	}

	public static void create(AddedByAdmin man) {
		man.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
