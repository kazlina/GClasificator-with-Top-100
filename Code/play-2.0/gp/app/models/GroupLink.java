package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "GroupLink", uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"groupDescr", "link"
		})
	})
public class GroupLink extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Constraints.Required
    @JoinColumn(name = "groupDescr", nullable = false)
    @ManyToOne
    public Group group;

    @Constraints.Required
    @JoinColumn(name = "link", nullable = false)
    @ManyToOne
    public Link link;

	@Constraints.Required
	@Column(name = "postWeight", nullable = false)
	public float postWeight;

	@Constraints.Required
	@Column(name = "profileWeight", nullable = false)
	public float profileWeight;

	public GroupLink(Group group, Link link, float postWeight, float profileWeight) {
        this.group = group;
        this.link = link;
        this.postWeight = postWeight;
        this.profileWeight = profileWeight;
        }

	private static Model.Finder<Long, GroupLink> find = new Model.Finder<Long, GroupLink>(Long.class, GroupLink.class);

	public static List<GroupLink> findByGroup(Long id) {
		return Group.findById(id).links;
	}
	
	public static List<GroupLink> findByLink(Long id) {
		return Link.findById(id).groupLink;
	}

	public static GroupLink findById(Long Id) {
		return find.ref(Id);
	}

	public static void create(GroupLink element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
